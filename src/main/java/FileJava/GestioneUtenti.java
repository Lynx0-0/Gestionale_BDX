package FileJava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestioneUtenti {

    public List<Utente> getAllUsersWithTheirFunctionalities() {
        List<Utente> users = new ArrayList<>();

        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM UTENTI");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("GMAIL");
                List<Funzionalita> funzionalitaList = getFunzionalitaForUser(email);
                users.add(new Utente(email, funzionalitaList));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    private List<Funzionalita> getFunzionalitaForUser(String userEmail) {
        List<Funzionalita> funzionalitaList = new ArrayList<>();

        String query = "SELECT f.ID, f.NOME_FUNZIONALITA, f.DESCRIZIONE FROM FUNZIONALITA f " +
                       "JOIN UTENTE_FUNZIONALITA uf ON f.ID = uf.ID_FUNZIONALITA " +
                       "WHERE uf.GMAIL = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, userEmail);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("ID");
                    String nome = rs.getString("NOME_FUNZIONALITA");
                    String descrizione = rs.getString("DESCRIZIONE");
                    funzionalitaList.add(new Funzionalita(id, nome, descrizione));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funzionalitaList;
    }

    public static List<Funzionalita> getFunzionalitaDisponibili() {
        List<Funzionalita> funzionalitaList = new ArrayList<>();

        String query = "SELECT ID, NOME_FUNZIONALITA, DESCRIZIONE FROM FUNZIONALITA";
        try (Connection connection = Database.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nome = rs.getString("NOME_FUNZIONALITA");
                String descrizione = rs.getString("DESCRIZIONE");
                funzionalitaList.add(new Funzionalita(id, nome, descrizione));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return funzionalitaList;
    }
    
    
    public boolean rimuoviFunzionalitaDaUtente(String userEmail, String funzionalitaId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM UTENTE_FUNZIONALITA WHERE GMAIL = ? AND ID_FUNZIONALITA = ?")) {
            statement.setString(1, userEmail);
            statement.setString(2, funzionalitaId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    public boolean aggiungiFunzionalitaUtente(String userEmail, String nuovaFunzionalitaId) {
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO UTENTE_FUNZIONALITA (GMAIL, ID_FUNZIONALITA) VALUES (?, ?)")) {
            statement.setString(1, userEmail);
            statement.setString(2, nuovaFunzionalitaId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

