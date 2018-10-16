package dao;

import java.sql.*;

public class Pagina {
	
	public static int AddPagina(int operaId, int numero, String img) throws Exception {
		PreparedStatement stmt1 = DatabaseOp.PrepareStatement("INSERT INTO pagina (ID, Immagine, trascrizione, ult_modifica, approvato) VALUES (NULL, ?, NULL, NULL, '0');");
        stmt1.setString(1, img);
        stmt1.executeUpdate();
        PreparedStatement stmt2 = DatabaseOp.PrepareStatement("INSERT INTO impaginazione (Opera, Pagina, Numero) VALUES (?, (SELECT ID FROM pagina where Immagine = img), ?);");
        stmt2.setInt(1, operaId);
        stmt2.setInt(2, numero);
        return stmt2.executeUpdate();
	}
	
	//String va modificato con il tipo che useremo per la trascrizione
	
	public static int AddTrascrizione(String trascrizione, int paginaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE pagina SET trascrizione = ? WHERE pagina.ID = ?;");
        stmt.setString(1, trascrizione);
        stmt.setInt(2, paginaId);
        return stmt.executeUpdate();
	}
	
	public static ResultSet GetPagina(int paginaId) throws Exception {
	    PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID, immagine, trascrizione FROM pagina WHERE pagina.ID = ?;");
        stmt.setInt(1, paginaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet GetTrascrzione(int paginaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT trascrizione FROM pagina WHERE pagina.ID = ?;");
        stmt.setInt(1, paginaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet GetImmagine(int paginaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT Immagine FROM pagina WHERE pagina.ID = ?;");
        stmt.setInt(1, paginaId);
        return stmt.executeQuery();
	}
	
	public static ResultSet GetImageCollection(int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT pagina.Immagine, impaginazione.Numero FROM pagina, impaginazione WHERE pagina.ID = impaginazione.Pagina AND impaginazione.Opera = ? ORDER BY impaginazione.Numero;");
        stmt.setInt(1, operaId);
        return stmt.executeQuery();
	}
	
	public static int ApprovePagina (int paginaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE pagina SET Approvato = '1' WHERE pagina.ID = ?;");
		stmt.setInt(1, paginaId);
		return stmt.executeUpdate();
	}
	
	public static int DelPagina (int paginaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("DELETE FROM pagina WHERE pagina.ID = ?;");
	    stmt.setInt(1, paginaId);
		return stmt.executeUpdate();
	}
}
