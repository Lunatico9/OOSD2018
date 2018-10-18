package dao;

import java.sql.*;

public class OperaDao {

	public static void AddOpera(String titolo, String autore, int anno) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO opera (ID, Titolo, Autore, Anno, Approvato) VALUES (NULL, ?, ?, ?, '0');");
        stmt.setString(1, titolo);
        stmt.setString(2, autore);
        stmt.setInt(3, anno);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public static void AddCategoriaToOpera(int operaId, String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
        PreparedStatement stmt = op.pStatement("INSERT INTO organizzazione (Opera, Categoria) VALUES ( ?, (SELECT ID FROM categoria WHERE nome = ?));");
        stmt.setInt(1, operaId);
        stmt.setString(2, categoria);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	
	public static ResultSet GetCategorie(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT categoria.Nome FROM categoria, organizzazione WHERE organizzazione.Opera = ? AND organizzazione.Categoria = categoria.ID;");
        stmt.setInt(1, operaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaByName (String titolo) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID FROM opera WHERE Approvato = 1 AND Titolo LIKE %?;");
		stmt.setString(1, titolo);
		return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaByAuthor (String autore) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID FROM opera WHERE Approvato = 1 Autore LIKE %?;");
		stmt.setString(1, autore);
		return stmt.executeQuery();
	}

	public static ResultSet SearchOperaByCategory (String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT opera.ID, FROM opera,organizzazione WHERE opera.Approvato = 1 AND opera.ID = organizzazione.Opera AND Categoria = ?;");
		stmt.setString(1, categoria);
		return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaNotApproved () throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID FROM opera WHERE Approvato = 0;");
		return stmt.executeQuery();
	}
	
	public static void ApproveOpera (int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE opera SET Approvato = 1 WHERE opera.ID = ?;");
		stmt.setInt(1, operaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static void DelOpera (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM opera WHERE opera.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
}
