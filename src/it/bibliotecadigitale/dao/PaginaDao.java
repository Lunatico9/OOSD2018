package it.bibliotecadigitale.dao;

import java.sql.*;
import java.util.ArrayList;
import it.bibliotecadigitale.model.Pagina;

public class PaginaDao implements PaginaDaoInterface{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPagina(int operaId, int numero, String img) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt1 = op.pStatement("INSERT INTO pagina (ID, Immagine, trascrizione, ult_modifica, approvato) VALUES (NULL, ?, NULL, NULL, '0');");
        stmt1.setString(1, img);
        stmt1.executeUpdate();
        op.close(stmt1);
        PreparedStatement stmt2 = op.pStatement("SELECT ID FROM pagina WHERE Immagine = ?");
        stmt2.setString(1, img);
        ResultSet rs = stmt2.executeQuery();
        rs.next();
        int paginaId = rs.getInt("ID");
        op.close(rs, stmt2);
        PreparedStatement stmt3 = op.pStatement("INSERT INTO impaginazione (Opera, Pagina, Numero) VALUES (?, ?, ?);");
        stmt3.setInt(1, operaId);
        stmt3.setInt(2, paginaId);
        stmt3.setInt(3, numero);
        stmt3.executeUpdate();
        op.close(stmt3);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTrascrizione(String trascrizione, int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		Timestamp t = new Timestamp(System.currentTimeMillis());
		PreparedStatement stmt = op.pStatement("UPDATE pagina SET trascrizione = ?, ult_modifica = ? WHERE pagina.ID = ?;");
        stmt.setString(1, trascrizione);
        stmt.setTimestamp(2, t);
        stmt.setInt(3, paginaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<String> getImageCollection(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT pagina.Immagine FROM pagina, impaginazione WHERE pagina.ID = impaginazione.Pagina AND impaginazione.Opera = ? ORDER BY impaginazione.Numero;");
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void approvePagina(int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE pagina SET Approvato = '1' WHERE pagina.ID = ?;");
		stmt.setInt(1, paginaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delPagina(int paginaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM pagina WHERE pagina.ID = ?;");
	    stmt.setInt(1, paginaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Pagina> searchPaginaNotApproved() throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Immagine, trascrizione, ult_modifica FROM pagina WHERE Approvato = 0;");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Pagina> trascrizioni = new ArrayList<Pagina>();
		while (rs.next())
        {
			int id = rs.getInt("ID");
			String img = rs.getString("Immagine");
			String trsc = rs.getString("trascrizione");
			Timestamp t = rs.getTimestamp("ult_modifica");
            trascrizioni.add(new Pagina (id, img, trsc, t, false));
        }
		op.close(rs, stmt);
		return trascrizioni;
	}
}
