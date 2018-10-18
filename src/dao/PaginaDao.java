package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Pagina;

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
	
	public static ArrayList<String> GetImageCollection(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT pagina.Immagine, FROM pagina, impaginazione WHERE pagina.ID = impaginazione.Pagina AND impaginazione.Opera = ? ORDER BY impaginazione.Numero;");
        stmt.setInt(1, operaId);
        ResultSet rs = stmt.executeQuery();
		ArrayList<String> images = new ArrayList<String>();
		while (rs.next())
        {
            images.add(rs.getString("pagina.Immagine"));
        }
		op.close(rs, stmt);
		return images;
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
	
	public static ArrayList<Pagina> SearchPaginaNotApproved() throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT trascrizione, ult_modifica FROM pagina WHERE Approvato = 0;");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Pagina> trascrizioni = new ArrayList<Pagina>(); int i = 0;
		while (rs.next() && i<10)
        {
			String trsc = rs.getString("trascrizione");
			Timestamp t = rs.getTimestamp("ult_modifica");
            trascrizioni.add(new Pagina (trsc, t, false));
        }
		op.close(rs, stmt);
		return trascrizioni;
	}
}
