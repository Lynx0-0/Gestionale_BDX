package FileJava;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReadDB {

    public ArrayList<Persona> readData(String nomeTabella) {

        final String QUERY = String.format(
                "SELECT PF_IDANF, PF_COGNOME, PF_NOME, PF_CDFISC, PF_CDCOMU, PF_DTNASC, PF_FSESSO, PF_DTINSE FROM %s",
                nomeTabella);

        ArrayList<Persona> persone = new ArrayList<>();

        try (Connection connection = Database.getConnection(); 
                PreparedStatement preparedStatement = connection.prepareStatement(QUERY);
                ResultSet rs = preparedStatement.executeQuery()) {

          
            while (rs.next()) {
              
                
                Persona persona = new Persona(
                        rs.getString("PF_IDANF"), 
                        rs.getString("PF_COGNOME"), 
                        rs.getString("PF_NOME"), 
                        rs.getString("PF_CDFISC"), 
                        rs.getString("PF_CDCOMU"),
                        rs.getDate("PF_DTNASC").toString(), 
                        rs.getString("PF_FSESSO").charAt(0),
                        rs.getDate("PF_DTINSE").toString() 
                );
                persone.add(persona);
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return persone;
    }
}
