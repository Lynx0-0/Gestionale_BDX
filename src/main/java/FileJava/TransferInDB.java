package FileJava;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class TransferInDB {

    public static void insertListOfPeople(ArrayList<Persona> listOfPeople, String tableName) {
        String insertPersonSql = "INSERT INTO " + tableName +
                " (PF_IDANF, PF_COGNOME, PF_NOME, PF_CDFISC, PF_CDCOMU, PF_DTNASC, PF_FSESSO, PF_DTINSE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String checkExistenceSql = "SELECT COUNT(*) FROM " + tableName + " WHERE PF_IDANF = ?";

        try (Connection connection = Database.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insertPersonSql);
                PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceSql)) {

            connection.setAutoCommit(false);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
            SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
       

            for (Persona persona : listOfPeople) {
            	System.out.println(persona.getId());
                checkExistenceStatement.setLong(1, Long.parseLong(persona.getId()));
                ResultSet resultSet = checkExistenceStatement.executeQuery();

                 if (resultSet.next() && resultSet.getInt(1) == 0) {
                    prepareInsertStatement(preparedStatement, sdf, output, persona);
                    preparedStatement.addBatch();
                }
            }

            executeBatchUpdate(preparedStatement, listOfPeople);

            connection.commit();
            
            System.out.println("Inserimento completato.");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    private static void prepareInsertStatement(PreparedStatement preparedStatement, SimpleDateFormat sdf,
            SimpleDateFormat output, Persona persona) {
    	
        try {
            if (persona.getId() == null || persona.getId().isEmpty()) {
            	System.out.println("id vuoto");
                return;
            }
            
            preparedStatement.setLong(1, Long.parseLong(persona.getId()));
            preparedStatement.setString(2, persona.getCognome());
            preparedStatement.setString(3, persona.getNome());
            preparedStatement.setString(4, persona.getCodiceFiscale());
            preparedStatement.setString(5, persona.getComuneCodice());

            setDatesInPreparedStatement(preparedStatement, sdf, output, persona);
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    
    
    private static void setDatesInPreparedStatement(PreparedStatement preparedStatement, SimpleDateFormat sdf,
            SimpleDateFormat output, Persona persona) throws SQLException {
        try {
        	 String dataNascitaStr = persona.getDataNascita();
        	 String dataInserimentoStr = persona.getDataInserimento();
             java.sql.Date sqlDataNascita;
        	
             if (dataNascitaStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            	 //sqlDataNascita = java.sql.Date.valueOf(dataNascitaStr);
            	 preparedStatement.setDate(6,java.sql.Date.valueOf(dataNascitaStr));
            	 preparedStatement.setString(7, String.valueOf(persona.getSesso()));
            	 preparedStatement.setDate(8, java.sql.Date.valueOf(dataInserimentoStr));
             }else {
            String formattedDataNascita = output.format(sdf.parse(persona.getDataNascita()));
            String formattedDataInserimento = output.format(sdf.parse(persona.getDataInserimento()));

            preparedStatement.setDate(6, java.sql.Date.valueOf(formattedDataNascita));
            preparedStatement.setString(7, String.valueOf(persona.getSesso()));
            preparedStatement.setDate(8, java.sql.Date.valueOf(formattedDataInserimento));
             }
        } catch (ParseException e) {
            e.printStackTrace();

        }
    }



    private static void executeBatchUpdate(PreparedStatement preparedStatement, ArrayList<Persona> listOfPeople)
            throws SQLException {
        int[] updateCounts = preparedStatement.executeBatch();

        for (int i = 0; i < updateCounts.length; i++) {
            Persona persona = listOfPeople.get(i);
            String status = getStatusFromUpdateCount(updateCounts[i]);
            CreateLogFile.writeFile(Main.getAzione(), i + 1, persona.getNome(), persona.getCognome(), status,
                    persona.getCodiceFiscale());
        }
    }

    private static String getStatusFromUpdateCount(int updateCount) {
        if (updateCount >= 0 || updateCount == Statement.SUCCESS_NO_INFO) {
            return "Inserito";
        } else if (updateCount == Statement.EXECUTE_FAILED) {
            return "Non inserito";
        } else {
            return "Stato sconosciuto";
        }
    }
}
