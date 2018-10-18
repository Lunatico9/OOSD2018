package dao;

import java.sql.*;

public class UtenteDao {
	
	public static void AddUtente(String login, String passw, String nome, String cognome) throws Exception {
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
	
	public static void ModifyPassw(String passw, int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Passw = ? WHERE utente.ID = ?;");
		stmt.setString(1, passw);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static void ModifyRuolo(char ruolo, int userId) throws Exception {
		String s = Character.toString(ruolo);
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Nome = ? WHERE utente.ID = ?;");
		stmt.setString(1, s);
		stmt.setInt(2, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}

	public static void AddPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '1' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static void DelPriv(int userId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE utente SET Privilegio = '0' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		stmt.executeUpdate();
		op.close(stmt);
	}
	
	public static ResultSet Login(String login, String passw) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT ID FROM utente WHERE Login = ? AND Passw = ?;");
		stmt.setString(1, login);
		stmt.setString(2, passw);
		ResultSet rs = stmt.executeQuery();
		return rs;
	}
	
	public static ResultSet SearchUserByLogin (String login) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT Login FROM utente WHERE Login LIKE %?;");
		stmt.setString(1, login);
		return stmt.executeQuery();
	}
	
	public static ResultSet SearchUserByRuolo (char ruolo) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("SELECT utente.Login FROM utente, ruolo WHERE ruolo.Nome = ? AND utente.ID = ruolo.Utente;");
		String s = Character.toString(ruolo);
		stmt.setString(1, s);
		return stmt.executeQuery();
	}
	
	public static void DelUtente (int id) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM utente WHERE utente.ID = ?;");
		stmt.setInt(1, id);
		stmt.executeUpdate();
		op.close(stmt);
	}
}