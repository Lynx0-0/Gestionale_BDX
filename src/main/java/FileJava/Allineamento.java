package FileJava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Allineamento {


    public static void allineaTabelle(String nomeTabellaDaConfrontare, String nomeTavellaRiferimento) throws SQLException {
        try (Connection conn = Database.getConnection()) {
            sincronizzaAggiunte(conn,nomeTabellaDaConfrontare,nomeTavellaRiferimento);
            sincronizzaCancellazioni(conn,nomeTabellaDaConfrontare,nomeTavellaRiferimento);
            sincronizzaAggiornamenti(conn,nomeTabellaDaConfrontare,nomeTavellaRiferimento);
        }
    }

    private static void sincronizzaAggiunte(Connection conn,String nomeTabellaDaConfrontare, String nomeTavellaRiferimento) throws SQLException {
        String query = "SELECT * FROM "+nomeTavellaRiferimento+" WHERE PF_IDANF NOT IN (SELECT PF_IDANF FROM "+nomeTavellaRiferimento+")";
        try (PreparedStatement selectStmt = conn.prepareStatement(query);
             ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                inserisciRecord(conn, rs,nomeTabellaDaConfrontare);
            }
        }
    }

    private static void inserisciRecord(Connection conn, ResultSet rs,String nomeTabellaDaConfrontare) throws SQLException {
        String insertQuery = "INSERT INTO "+nomeTabellaDaConfrontare+" (PF_IDANF, PF_COGNOME, PF_NOME, PF_CDFISC, PF_CDCOMU, PF_DTNASC, PF_FSESSO, PF_DTINSE) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            insertStmt.setLong(1, rs.getLong("PF_IDANF"));
            insertStmt.setString(2, rs.getString("PF_COGNOME"));
            insertStmt.setString(3, rs.getString("PF_NOME"));
            insertStmt.setString(4, rs.getString("PF_CDFISC"));
            insertStmt.setString(5, rs.getString("PF_CDCOMU"));
            insertStmt.setDate(6, rs.getDate("PF_DTNASC"));
            insertStmt.setString(7, rs.getString("PF_FSESSO"));
            insertStmt.setDate(8, rs.getDate("PF_DTINSE"));

            insertStmt.executeUpdate();
        }
    }

    private static void sincronizzaCancellazioni(Connection conn,String nomeTabellaDaConfrontare, String nomeTavellaRiferimento) throws SQLException {
        
        String logDeleteQuery = "SELECT PF_IDANF, PF_NOME, PF_COGNOME, PF_CDFISC FROM "+nomeTabellaDaConfrontare+" WHERE PF_IDANF NOT IN (SELECT PF_IDANF FROM "+nomeTavellaRiferimento+")";

        
        try (PreparedStatement selectStmt = conn.prepareStatement(logDeleteQuery)) {
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("PF_IDANF"); 
                String nome = rs.getString("PF_NOME");
                String cognome = rs.getString("PF_COGNOME");
                String cFiscale = rs.getString("PF_CDFISC");
                CreateLogFile.writeFile(Main.getAzione(), id, nome, cognome, "Cancellazione eseguita", cFiscale);
            }
        }

      
        String deleteQuery = "DELETE FROM "+nomeTabellaDaConfrontare+" WHERE PF_IDANF NOT IN (SELECT PF_IDANF FROM "+nomeTavellaRiferimento+")";
        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
            int rowsAffected = deleteStmt.executeUpdate();
            System.out.println("Numero di record eliminati: " + rowsAffected);
            
            CreateLogFile.writeFile(Main.getAzione(), rowsAffected, " ",  " ", "Cancellazione eseguite",  " ");
            CreateLogFile.writeLine(Integer.toString(rowsAffected));
        }
    }



    private static void sincronizzaAggiornamenti(Connection conn,String nomeTabellaDaConfrontare, String nomeTavellaRiferimento) throws SQLException {
        String query = "SELECT a.* FROM "+nomeTavellaRiferimento+" a JOIN "+nomeTabellaDaConfrontare+" t ON a.PF_IDANF = t.PF_IDANF WHERE a.PF_COGNOME <> t.PF_COGNOME OR a.PF_NOME <> t.PF_NOME OR a.PF_CDFISC <> t.PF_CDFISC OR a.PF_CDCOMU <> t.PF_CDCOMU OR a.PF_DTNASC <> t.PF_DTNASC OR a.PF_FSESSO <> t.PF_FSESSO OR a.PF_DTINSE <> t.PF_DTINSE";

        try (PreparedStatement selectStmt = conn.prepareStatement(query);
             ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                aggiornaRecord(conn, rs,nomeTabellaDaConfrontare);
            }
        }
    }

    private static void aggiornaRecord(Connection conn, ResultSet rs,String nomeTabellaDaConfrontare) throws SQLException {
        String updateQuery = "UPDATE "+nomeTabellaDaConfrontare+" SET PF_COGNOME = ?, PF_NOME = ?, PF_CDFISC = ?, PF_CDCOMU = ?, PF_DTNASC = ?, PF_FSESSO = ?, PF_DTINSE = ? WHERE PF_IDANF = ?";

        try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
            updateStmt.setString(1, rs.getString("PF_COGNOME"));
            updateStmt.setString(2, rs.getString("PF_NOME"));
            updateStmt.setString(3, rs.getString("PF_CDFISC"));
            updateStmt.setString(4, rs.getString("PF_CDCOMU"));
            updateStmt.setDate(5, rs.getDate("PF_DTNASC"));
            updateStmt.setString(6, rs.getString("PF_FSESSO"));
            updateStmt.setDate(7, rs.getDate("PF_DTINSE"));
            updateStmt.setLong(8, rs.getLong("PF_IDANF"));

            updateStmt.executeUpdate();
        }
    }

}
