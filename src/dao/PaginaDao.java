package dao;

import java.sql.*;

public class PaginaDao {
	
	public static void AddPagina(int operaId, int numero, String img) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt1 = op.pStatement("INSERT INTO pagina (ID, Immagine, trascrizione, ult_modifica, approvato) VALUES (NULL, ?, NULL, NULL, '0');");
        stmt1.setString(1, img);
        stmt1.executeUpdate();
        op.close(stmt1);
        PreparedStatement stmt2 = op.pStatement("INSERT INTO impaginazione (Opera, Pagina, Numero) VALUES (?, (SELECT ID FROM pagina where Immagine = img), ?);");
        stmt2.setInt(1, operaId);
        stmt2.setInt(2, numero);
        stmt2.executeUpdate();
        op.close(stmt2);
	}
	
	public static void AddTrascrizione(String trascrizione, int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE pagina SET trascrizione = ? WHERE pagina.ID = ?;");
        stmt.setString(1, trascrizione);
        stmt.setInt(2, paginaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	
	public static ResultSet GetImageCollection(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT pagina.Immagine, impaginazione.Numero FROM pagina, impaginazione WHERE pagina.ID = impaginazione.Pagina AND impaginazione.Opera = ? ORDER BY impaginazione.Numero;");
        stmt.setInt(1, operaId);
        return stmt.executeQuery();
	}
	
	public static void ApprovePagina(int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE pagina SET Approvato = '1' WHERE pagina.ID = ?;");
		stmt.setInt(1, paginaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static void DelPagina(int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM pagina WHERE pagina.ID = ?;");
	    stmt.setInt(1, paginaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static ResultSet SearchPaginaNotApproved() throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Immagine, trascrizione, ult_modifica FROM pagina WHERE Approvato = 0;");
		return stmt.executeQuery();
	}
}
