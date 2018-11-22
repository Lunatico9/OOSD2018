package it.bibliotecadigitale.model.dao;

import java.sql.*;
import java.util.ArrayList;

import it.bibliotecadigitale.helper.Password;
import it.bibliotecadigitale.model.Utente;

public class UtenteDao implements UtenteDaoInterface {
	
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addUtente(String login, String passw, String nome, String cognome, String mail, String titolo, String professione) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt1 = op.pStatement("INSERT INTO utente (ID, Login, Passw, Privilegio, Nome, Cognome, Mail, Titolo, Professione) VALUES (NULL, ?, ?, '0', ?, ?, ?, ?, ?);");
        stmt1.setString(1, login);
        stmt1.setString(2, Password.hashPassword(passw));
        stmt1.setString(3, nome);
        stmt1.setString(4, cognome);
        stmt1.setString(5, mail);
        stmt1.setString(6, titolo);
        stmt1.setString(7, professione);
		stmt1.executeUpdate();
		op.close(stmt1);
		PreparedStatement stmt2 = op.pStatement("INSERT INTO ruolo (Nome, Utente, Livello) VALUES ('u', (SELECT ID FROM utente WHERE Login = ?), '0');");
		stmt2.setString(1, login);
		stmt2.executeUpdate();
		op.close(stmt2);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMessage(String cont, String msg, String tipo, int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO messaggio (ID, Contatto, Testo, Tipo, Utente) VALUES (NULL, ?, ?, ?, ?);");
		stmt.setString(1, cont);
		stmt.setString(2, msg);
		stmt.setString(3, tipo);
		stmt.setInt(4, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyLogin(String log, int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Login = ? WHERE utente.ID = ?;");
		stmt.setString(1, log);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyPassw(String passw, int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Passw = ? WHERE utente.ID = ?;");
		stmt.setString(1, passw);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyRuolo(char ruolo, int userId) throws Exception {
		String s = Character.toString(ruolo);
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Nome = ? WHERE  ruolo.Utente = ?;");
		stmt.setString(1, s);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void modifyLivello (int userId, int livello) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Livello = ? WHERE ruolo.Utente = ?;");
        stmt.setInt(1, livello);
        stmt.setInt(2, userId);
        stmt.executeUpdate();
        op.close(stmt);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '1' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '0' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean login(String login, String passw) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT Nome, Passw FROM utente WHERE Login = ?");
		stmt.setString(1, login);
		ResultSet rs = stmt.executeQuery();
		if (rs.next() && Password.checkPassword(passw, rs.getString("Passw"))) {
			return true;
		}
		else return false;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Utente getUtente(String login) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Nome, utente.Cognome, utente.Mail, utente.Titolo, utente.Professione, utente.Privilegio, ruolo.Nome, ruolo.Livello FROM utente, ruolo WHERE utente.Login = ? AND utente.ID = ruolo.Utente;");
		stmt.setString(1, login);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int id = rs.getInt("utente.ID");
        String log = rs.getString("utente.Login");
        String psw = rs.getString("utente.Passw");
        String nom = rs.getString("utente.Nome");
        String cog = rs.getString("utente.Cognome");
        String mail = rs.getString("utente.Mail");
        String tit = rs.getString("utente.Titolo");
        String pro = rs.getString("utente.Professione");
        boolean pri = rs.getBoolean("utente.Privilegio");
        char rol = rs.getString("ruolo.Nome").charAt(0);
        int liv = rs.getInt("ruolo.Livello");
        Utente utente = new Utente(id, log, psw, nom, cog, mail, tit, pro, pri, rol, liv);
		op.close(rs, stmt);
		return utente;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isNotRegistered(String login) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.Nome FROM utente WHERE utente.Login = ?;");
		stmt.setString(1, login);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			return false;
		}
		else return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Utente> searchUserByLogin (String login) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Nome, utente.Cognome, utente.Mail, utente.Titolo, utente.Professione, utente.Privilegio, ruolo.Nome, ruolo.Livello FROM utente, ruolo WHERE utente.Login LIKE ? AND utente.ID = ruolo.Utente;");
		stmt.setString(1, "%"+login+"%");
		ResultSet rs = stmt.executeQuery();
		ArrayList<Utente> listaUtenti = new ArrayList<Utente>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("utente.ID");
            String log = rs.getString("utente.Login");
            String psw = rs.getString("utente.Passw");
            String nom = rs.getString("utente.Nome");
            String cog = rs.getString("utente.Cognome");
            String mail = rs.getString("utente.Mail");
            String tit = rs.getString("utente.Titolo");
            String pro = rs.getString("utente.Professione");
            boolean pri = rs.getBoolean("utente.Privilegio");
            char rol = rs.getString("ruolo.Nome").charAt(0);
            int liv = rs.getInt("ruolo.Livello");
            Utente utente = new Utente(id, log, psw, nom, cog, mail, tit, pro, pri, rol, liv);
            listaUtenti.add(utente);
            i++;
        }
		op.close(rs, stmt);
		return listaUtenti;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ArrayList<Utente> searchUserByLogin (String login, String role) throws Exception {
		char c = role.charAt(0);
		role = Character.toString(c);
	    DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Nome, utente.Cognome, utente.Mail, utente.Titolo, utente.Professione, utente.Privilegio, ruolo.Nome, ruolo.Livello FROM utente, ruolo WHERE utente.Login LIKE ? AND utente.ID = ruolo.Utente AND ruolo.Nome = ?;");
		stmt.setString(1, "%"+login+"%");
		stmt.setString(2, role);
		ResultSet rs = stmt.executeQuery();
		ArrayList<Utente> listaUtenti = new ArrayList<Utente>(); int i = 0;
		while (rs.next() && i<10)
        {
			int id = rs.getInt("utente.ID");
            String log = rs.getString("utente.Login");
            String psw = rs.getString("utente.Passw");
            String nom = rs.getString("utente.Nome");
            String cog = rs.getString("utente.Cognome");
            String mail = rs.getString("utente.Mail");
            String tit = rs.getString("utente.Titolo");
            String pro = rs.getString("utente.Professione");
            boolean pri = rs.getBoolean("utente.Privilegio");
            char rol = rs.getString("ruolo.Nome").charAt(0);
            int liv = rs.getInt("ruolo.Livello");
            Utente utente = new Utente(id, log, psw, nom, cog, mail, tit, pro, pri, rol, liv);
            listaUtenti.add(utente);
            i++;
        }
		op.close(rs, stmt);
		return listaUtenti;
	}
	
	/*
	 * {@inheritDoc}
	
	@Override
	public ArrayList<Utente> searchUserByRuolo (char ruolo) throws Exception {
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
	 */
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delUtente (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM utente WHERE utente.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
}