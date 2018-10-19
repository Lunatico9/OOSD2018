package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Opera;

public class OperaDao implements OperaDaoInterface{

	public void AddOpera(String titolo, String autore, int anno) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO opera (ID, Titolo, Autore, Anno, Approvato) VALUES (NULL, ?, ?, ?, '0');");
        stmt.setString(1, titolo);
        stmt.setString(2, autore);
        stmt.setInt(3, anno);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public void AddCategoriaToOpera(int operaId, String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
        PreparedStatement stmt = op.pStatement("INSERT INTO organizzazione (Opera, Categoria) VALUES ( ?, ?;");
        stmt.setInt(1, operaId);
        stmt.setString(2, categoria);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	
	public ArrayList<String> GetCategorie(int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT categoria.Nome FROM categoria, organizzazione WHERE organizzazione.Opera = ? AND organizzazione.Categoria = categoria.ID;");
        stmt.setInt(1, operaId);
        ResultSet rs = stmt.executeQuery();
        ArrayList<String> categorie = new ArrayList<String>();
		while (rs.next())
        {
            categorie.add(rs.getString("categoria.Nome"));
        }
		op.close(rs, stmt);
		return categorie;
	}
	
	public ArrayList<Opera> SearchOperaByName (String titolo) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 1 AND Titolo LIKE %?;");
		stmt.setString(1, titolo);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno");
            Opera opera = new Opera(id, tit, aut, anno, true);
            listaOpere.add(opera);
            i++;
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	public ArrayList<Opera> SearchOperaByAuthor (String autore) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 1 Autore LIKE %?;");
		stmt.setString(1, autore);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno");
            Opera opera = new Opera(id, tit, aut, anno, true);
            listaOpere.add(opera);
            i++;
        }
		op.close(rs, stmt);
		return listaOpere;
	}

	public ArrayList<Opera> SearchOperaByCategory (String categoria) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT opera.ID opera.Titolo, opera.Autore, opera.Anno FROM opera, organizzazione WHERE opera.Approvato = 1 AND opera.ID = organizzazione.Opera AND Categoria = ?;");
		stmt.setString(1, categoria);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("opera.ID");
            String tit = rs.getString("opera.Titolo");
            String aut = rs.getString("opera.Autore");
            int anno = rs.getInt("opera.Anno");
            Opera opera = new Opera(id, tit, aut, anno, true);
            listaOpere.add(opera);
            i++;
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	public ArrayList<Opera> SearchOperaNotApproved () throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID, Titolo, Autore, Anno FROM opera WHERE Approvato = 0;");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Opera> listaOpere = new ArrayList<Opera>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("ID");
            String tit = rs.getString("Titolo");
            String aut = rs.getString("Autore");
            int anno = rs.getInt("Anno");
            Opera opera = new Opera(id, tit, aut, anno, false);
            listaOpere.add(opera);
            i++;
        }
		op.close(rs, stmt);
		return listaOpere;
	}
	
	public void AllocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO trascrittore (Utente, Opera, Data) VALUES ( ?, ?, CURRENT_TIMESTAMP);");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public void DeallocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM trascrittore WHERE trascrittore.Utente = ? AND trascrittore.Opera = ?;");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public void ApproveOpera (int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE opera SET Approvato = 1 WHERE opera.ID = ?;");
		stmt.setInt(1, operaId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public void DelOpera (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM opera WHERE opera.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
}
