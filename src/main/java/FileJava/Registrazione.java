package FileJava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Registrazione {

    private static final String SALT = "jdsekllkoiegroij"; 

    public static void registra(String azione, String email, String password) {
        String insertSql = "INSERT INTO UTENTI (GMAIL, PASSWORD, PERMISSIONS) VALUES (?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {

            
            String hashedPassword = hashPassword(password, SALT);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, azione);

           
            preparedStatement.executeUpdate();
            System.out.println("nonn va");
        } catch (SQLException | NoSuchAlgorithmException e) {
        	System.out.println("Strano");
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
