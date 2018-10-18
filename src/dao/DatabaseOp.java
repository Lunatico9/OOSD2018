package dao;

import java.sql.*;

public class DatabaseOp {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/oosd";
	private static String userId = "root";
	private static String passw = "";
	private Connection con;
	
	private void setConnection() {
		try {
			Class.forName(driver);
			this.con = DriverManager.getConnection (url, userId, passw);
		}
		catch(SQLException e) {
			System.out.println("Connection Error");
	    }
		catch(ClassNotFoundException e) {
			System.out.println("Class not found");
		}
	}
	
		public PreparedStatement pStatement(String query) throws Exception {
			this.setConnection();
		    return con.prepareStatement(query);
	}
	
	public void close (PreparedStatement stmt) {
		if (stmt != null)
	    {
	        try
	        {
	            stmt.close();
	        } catch (SQLException e)
	        {
	            System.out.println("The statement cannot be closed.");
	        }
	    }
		
	    if (this.con != null)
	    {
	        try
	        {
	            this.con.close();
	        } catch (SQLException e)
	        {
	            System.out.println("The data source connection cannot be closed.");
	        }
	    }
		
	}
}

