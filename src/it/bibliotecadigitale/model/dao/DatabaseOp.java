package it.bibliotecadigitale.model.dao;

import java.sql.*;

public class DatabaseOp {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/OOSD?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String userId = "root";
	private static String passw = "";
	private Connection con;
	
	/**
	 * Stabilisce la connessione con il database
	 */
	
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
	
	/**
	 * Effettua query con prepared statement
	 * @param String query
	 * @return a PreparedStatement
	 */
	
	public PreparedStatement pStatement(String query) throws Exception {
			this.setConnection();
		    return con.prepareStatement(query);
	}
	
	/**
	 * Chiude la connessione e l'oggetto PreparedStatement
	 * @param PreparedStatement stmt
	 */
	
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
	
	/**
	 * Chiude la connessione, l'oggetto PreparedStatement e il ResultSet
	 * @param PreparedStatement stmt
	 * @param ResultSet rs
	 */

	public void close (ResultSet rs, PreparedStatement stmt) {
		if (rs != null)
	    {
	        try
	        {
	            rs.close();
	        } catch (SQLException e)
	        {
	            System.out.println("The resultset cannot be closed.");
	        }
	    }
		
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

