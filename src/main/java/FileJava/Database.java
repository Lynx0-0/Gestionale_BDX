package FileJava;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private static final String DATABASE_URL = "jdbc:oracle:thin:@";
	private static final String DATABASE_USER = "SYSTEST";
	private static final String DATABASE_PASSWORD = "SYSTEST";

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
	}

}
