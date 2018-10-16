package dao;

import java.sql.*;

public class DatabaseOp {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/oosd";
	private static String userId = "root";
	private static String passw = "";
	
	
	public static Connection Connection() throws Exception {
		Connection con  = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection (url, userId, passw);
		}
		catch(SQLException e) {
			System.out.println("Connection Error");
		}
		return con;
	}
	
	public static PreparedStatement PrepareStatement(String query) throws Exception {
		Connection con = DatabaseOp.Connection();
		return con.prepareStatement(query);
	}
}

