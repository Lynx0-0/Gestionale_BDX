package FileJava;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class Login {
    private static final String SALT = "jdsekllkoiegroij"; 
    private static final String LOGIN_QUERY = "SELECT PASSWORD, PERMISSIONS FROM UTENTI WHERE GMAIL = ?";

    public static String getPermissions(String email, String password) throws NoSuchAlgorithmException, SQLException {
        String hashedPassword = hashPassword(password, SALT);
        String permissions = null;

        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY)) {
            
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString("PASSWORD").equals(hashedPassword)) {
                permissions = resultSet.getString("PERMISSIONS");
            }
        }
        return permissions;
    }

    private static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
