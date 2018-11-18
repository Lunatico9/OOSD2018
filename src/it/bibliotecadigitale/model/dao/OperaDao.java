package it.bibliotecadigitale.model.dao;

import java.sql.*;
import java.util.ArrayList;
import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Pagina;

public class OperaDao implements OperaDaoInterface{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOpera(String titolo, String autore, int anno) throws Exception{
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO opera (ID, Titolo, Autore, Anno, Approvato) VALUES (NULL, ?, ?, ?, '0');");
        stmt.setString(1, titolo);
        stmt.setString(2, autore);
        stmt.setInt(3, anno);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyOpera(int operaId, String titolo, String autore, String categoria, int anno) throws Exception{
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt1 = op.pStatement("UPDATE opera SET Titolo = ?, Autore = ?, Anno = ? WHERE opera.ID = ?;");
        stmt1.setString(1, titolo);
        stmt1.setString(2, autore);
        stmt1.setInt(3, anno);
        stmt1.setInt(4, operaId);
        stmt1.executeUpdate();
        op.close(stmt1);
		PreparedStatement stmt2 = op.pStatement("UPDATE organizzazione SET Categoria = ?, WHERE Opera = ?;");
		stmt2.setString(1, categoria);
	    stmt2.setInt(2, operaId);
	    op.close(stmt2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCategoriaToOpera(int operaId, String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
        PreparedStatement stmt = op.pStatement("INSERT INTO organizzazione (Opera, Categoria) VALUES ( ?, ?);");
        stmt.setInt(1, operaId);
        stmt.setString(2, categoria);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getOpera(String tit, String aut, int year) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT opera.ID FROM opera WHERE opera.Titolo = ? AND opera.Autore = ? AND opera.Anno = ?;");
        stmt.setString(1, tit);
        stmt.setString(2, aut);
        stmt.setInt(3, year);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int operaId = rs.getInt("ID");
		op.close(rs, stmt);
		return operaId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCategoria(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT categoria.Nome FROM categoria, organizzazione WHERE organizzazione.Opera = ? AND organizzazione.Categoria = categoria.Nome;");
        stmt.setInt(1, operaId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        String categoria = rs.getString("Nome");
		op.close(rs, stmt);
		return categoria;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ArrayList<String> getCategorie() throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT categoria.Nome FROM categoria;");
        ResultSet rs = stmt.executeQuery();
        ArrayList<String> categorie = new ArrayList<String>();
		while (rs.next())
        {
            categorie.add(rs.getString("categoria.Nome"));
        }
		op.close(rs, stmt);
		return categorie;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Pagina> getPagine(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT pagina.ID, pagina.Immagine, pagina.trascrizione, pagina.ult_modifica, pagina.approvato FROM pagina, impaginazione WHERE impaginazione.Pagina = pagina.ID AND impaginazione.Opera = ?;");
		stmt.setInt(1, operaId);
		ResultSet rs = stmt.executeQuery();
		
		ArrayList<Pagina> listaPagine = new ArrayList<Pagina>();
		while (rs.next()) {
			int id = rs.getInt("pagina.ID");
			String img = rs.getString("pagina.Immagine");
			String trasc = rs.getString("pagina.trascrizione");
			Timestamp t = rs.getTimestamp("pagina.ult_modifica");
			int app = rs.getInt("pagina.approvato");
			boolean b = false;
			if (app == 1) {
				b = true;
			}
			Pagina pagina = new Pagina(id, img, trasc, t, b);
			listaPagine.add(pagina);
		}
		op.close(rs, stmt);
		return listaPagine;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Opera> searchOperaByName (String titolo) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 1 AND Titolo LIKE ?;");
		stmt.setString(1, "%"+titolo+"%");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>();
		while (rs.next())
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno");
            String cat = getCategoria(id);
            Opera opera = new Opera(id, tit, aut, cat, anno, true);
            listaOpere.add(opera);
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Opera> searchOperaByAuthor (String autore) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 1 AND Autore LIKE ?;");
		stmt.setString(1, "%"+autore+"%");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>();
		while (rs.next())
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno"); String cat = getCategoria(id);
            Opera opera = new Opera(id, tit, aut, cat, anno, true);
            listaOpere.add(opera);
        }
		op.close(rs, stmt);
		return listaOpere;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Opera> searchOperaByCategory (String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT opera.ID, opera.Titolo, opera.Autore, opera.Anno FROM opera, organizzazione WHERE opera.Approvato = 1 AND opera.ID = organizzazione.Opera AND Categoria = ?;");
		stmt.setString(1, categoria);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>();
		while (rs.next())
        {
			int id = rs.getInt("opera.ID");
            String tit = rs.getString("opera.Titolo");
            String aut = rs.getString("opera.Autore");
            int anno = rs.getInt("opera.Anno");
            String cat = getCategoria(id);
            Opera opera = new Opera(id, tit, aut, cat, anno, true);
            listaOpere.add(opera);
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Opera> searchOperaNotApproved () throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 0;");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>();
		while (rs.next())
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno");
            String cat = getCategoria(id);
            Opera opera = new Opera(id, tit, aut, cat, anno, false);
            listaOpere.add(opera);
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void allocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO trascrittore (Utente, Opera, Data) VALUES ( ?, ?, CURRENT_TIMESTAMP);");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deallocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM trascrittore WHERE trascrittore.Utente = ? AND trascrittore.Opera = ?;");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void approveOpera (int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE opera SET Approvato = 1 WHERE opera.ID = ?;");
		stmt.setInt(1, operaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delOpera (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM opera WHERE opera.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
}
