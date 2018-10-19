package dao;

import java.sql.*;
import java.util.ArrayList;
import model.Utente;

public class UtenteDao implements UtenteDaoInterface {
	
	public void AddUtente(String login, String passw, String nome, String cognome) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt1 = op.pStatement("INSERT INTO utente (ID, Login, Passw, Privilegio, Nome, Cognome) VALUES (NULL, ?, ?, '0', ?, ?);");
        stmt1.setString(1, login);
        stmt1.setString(2, passw);
        stmt1.setString(3, nome);
        stmt1.setString(4, cognome);
		stmt1.executeUpdate();
		op.close(stmt1);
		PreparedStatement stmt2 = op.pStatement("INSERT INTO ruolo (Nome, Utente, Livello) VALUES ('u', (SELECT ID FROM utente WHERE Login = ?), '0');");
		stmt2.setString(1, login);
		stmt2.executeUpdate();
	}
	
	public void ModifyPassw(String passw, int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Passw = ? WHERE utente.ID = ?;");
		stmt.setString(1, passw);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public void ModifyRuolo(char ruolo, int userId) throws Exception {
		String s = Character.toString(ruolo);
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Nome = ? WHERE utente.ID = ?;");
		stmt.setString(1, s);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public void ModifyLivello (int userId, int livello) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Livello = ? WHERE ruolo.Utente = ?;");
        stmt.setInt(1, livello);
        stmt.setInt(2, userId);
        stmt.executeUpdate();
        op.close(stmt);
	}

	public void AddPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '1' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public void DelPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '0' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public boolean Login(String login, String passw) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID FROM utente WHERE Login = ? AND Passw = ?;");
		stmt.setString(1, login);
		stmt.setString(2, passw);
		ResultSet rs = stmt.executeQuery();
		if (rs.getInt("ID") == 0) {
			op.close(rs, stmt);
			return false;
		}
		else {
			op.close(rs, stmt);
			return true;
		}
	}
	
	public ArrayList<Utente> SearchUserByLogin (String login) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Nome, utente.Cognome, utente.Privilegio, ruolo.Nome, ruolo.Livello FROM utente, ruolo WHERE utente.Login LIKE %? AND utente.ID = ruolo.Utente;");
		stmt.setString(1, login);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Utente> listaUtenti = new ArrayList<Utente>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("utente.ID");
            String log = rs.getString("utente.Login");
            String psw = rs.getString("utente.Passw");
            String nom = rs.getString("utente.Nome");
            String cog = rs.getString("utente.Cognome");
            boolean pri = rs.getBoolean("utente.Privilegio");
            char rol = rs.getString("ruolo.Nome").charAt(0);
            int liv = rs.getInt("ruolo.Livello");
            Utente utente = new Utente(id, log, psw, nom, cog, pri, rol, liv);
            listaUtenti.add(utente);
            i++;
        }
		op.close(rs, stmt);
		return listaUtenti;
	}
	
	public ArrayList<Utente> SearchUserByRuolo (char ruolo) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Nome, utente.Cognome, utente.Privilegio, ruolo.Nome, ruolo.Livello FROM utente, ruolo WHERE ruolo.Nome = ? AND utente.ID = ruolo.Utente;");
		String s = Character.toString(ruolo);
		stmt.setString(1, s);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Utente> listaUtenti = new ArrayList<Utente>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("utente.ID");
			String log = rs.getString("utente.Login");
	        String psw = rs.getString("utente.Passw");
	        String nom = rs.getString("utente.Nome");
	        String cog = rs.getString("utente.Cognome");
	        boolean pri = rs.getBoolean("utente.Privilegio");
	        char rol = rs.getString("ruolo.Nome").charAt(0);
	        int liv = rs.getInt("ruolo.Livello");
            Utente utente = new Utente(id, log, psw, nom, cog, pri, rol, liv);
            listaUtenti.add(utente);
            i++;
        }
		op.close(rs, stmt);
		return listaUtenti;
	}
	
	public void DelUtente (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM utente WHERE utente.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
}