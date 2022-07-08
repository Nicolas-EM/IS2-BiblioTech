package integration.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
	
	public static final String DB_URL = "jdbc:mysql://server.sa-pp.com:41131/is2f2122?useSSL=false";
	public static final String USERNAME = "is2f2122";
	public static final String PASSWORD = "vy6sMdXnjzzLpg16";
	// forum
	// FkKzQnFLDKAl7xeJjS
	
	private static Connection conn;
	
	public DatabaseConnection() {
		// Empty
	}

	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			return (conn != null) ? conn : null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static int dropRegisterWithID(String sql, int ID) {
		Connection conn = getConnection();
		int result = -1;
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setInt(1, ID);
			
			result = statement.executeUpdate();
			
			statement.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
				
		return result;
	}
	
	public static int dropRegisterWithString(String sql, String str) {
		Connection conn = getConnection();
		int result = -1;
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, str);
			
			result = statement.executeUpdate();
			
			statement.close();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
				
		return result;
	}
	
	public static void killConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}