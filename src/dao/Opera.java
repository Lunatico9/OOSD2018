package dao;

import java.sql.*;

public class Opera {

	public static int AddOpera(String titolo, String autore, int anno) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("INSERT INTO opera (ID, Titolo, Autore, Anno, Approvato) VALUES (NULL, ?, ?, ?, '0');");
        stmt.setString(1, titolo);
        stmt.setString(2, autore);
        stmt.setInt(3, anno);
        return stmt.executeUpdate();
	}
	
	public static int AddCategoriaToOpera(int operaId, String categoria) throws Exception {
        PreparedStatement stmt = DatabaseOp.PrepareStatement("INSERT INTO organizzazione (Opera, Categoria) VALUES ( ?, (SELECT ID FROM categoria WHERE nome = ?));");
        stmt.setInt(1, operaId);
        stmt.setString(2, categoria);
        return stmt.executeUpdate();
	}
	
	public static ResultSet GetOpera(int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID, titolo, autore, anno, approvato FROM opera WHERE opera.ID = ?;");
        stmt.setInt(1,operaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet GetPagine(int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT pagina.ID, pagina.Immagine, pagina.trascrizione, pagina.ult_modifica, pagina.approvato FROM opera JOIN impaginazione JOIN Pagina ON opera.ID = impaginazione.Opera AND pagina.ID = impaginazione.Pagina AND opera.ID = ? ORDER BY impaginazione.Numero;");
        stmt.setInt(1, operaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet GetCategorie(int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT categoria.Nome FROM categoria, organizzazione WHERE organizzazione.Opera = ? AND organizzazione.Categoria = categoria.ID;");
        stmt.setInt(1, operaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaByName (String titolo) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID FROM opera WHERE Approvato = 1 AND Titolo LIKE %?;");
		stmt.setString(1, titolo);
		return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaByAuthor (String autore) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID FROM opera WHERE Approvato = 1 Autore LIKE %?;");
		stmt.setString(1, autore);
		return stmt.executeQuery();
	}

	public static ResultSet SearchOperaByCategory (String categoria) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT opera.ID, FROM opera,organizzazione WHERE opera.Approvato = 1 AND opera.ID = organizzazione.Opera AND Categoria = ?;");
		stmt.setString(1, categoria);
		return stmt.executeQuery();
	}
	
	public static ResultSet SearchOperaNotApproved () throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID FROM opera WHERE Approvato = 0;");
		return stmt.executeQuery();
	}
	
	public static int ApproveOpera (int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE opera SET Approvato = 1 WHERE opera.ID = ?;");
		stmt.setInt(1, operaId);
		return stmt.executeUpdate();
	}
	
	public static int DelOpera (int id) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("DELETE FROM opera WHERE opera.ID = ?;");
		stmt.setInt(1, id);
		return stmt.executeUpdate();
	}
	
}
