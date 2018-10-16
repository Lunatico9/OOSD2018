package dao;

import java.sql.*;

public class Utente {
	
	public static void AddUser(String login, String passw, String nome, String cognome) throws Exception {
		PreparedStatement stmt1 = DatabaseOp.PrepareStatement("INSERT INTO utente (ID, Login, Passw, Privilegio, Nome, Cognome) VALUES (NULL, ?, ?, '0', ?, ?);");
        stmt1.setString(1, login);
        stmt1.setString(2, passw);
        stmt1.setString(3, nome);
        stmt1.setString(4, cognome);
		stmt1.executeUpdate();
		PreparedStatement stmt2 = DatabaseOp.PrepareStatement("INSERT INTO ruolo (Nome, Utente, Livello) VALUES ('u', (SELECT ID FROM utente WHERE Login = ?), '0');");
		stmt2.setString(1, login);
		stmt2.executeUpdate();
	}
	
	public static ResultSet GetUtente(int userId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT utente.ID, utente.Login, utente.Passw, utente.Privilegio, utente.Nome, utente.Cognome, ruolo.Nome, ruolo.Livello FROM pagina, ruolo WHERE utente.ID = ? AND utente.ID = ruolo.Utente;");
        stmt.setInt(1, userId);
        return stmt.executeQuery();
	}
	
	public static int ModifyPassw(String passw, int userId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE utente SET Passw = ? WHERE utente.ID = ?;");
		stmt.setString(1, passw);
		stmt.setInt(2, userId);
		return stmt.executeUpdate();
	}
	
	public static int ModifyRuolo(String ruolo, int userId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE ruolo SET Nome = ? WHERE utente.ID = ?;");
		stmt.setString(1, ruolo);
		stmt.setInt(2, userId);
		return stmt.executeUpdate();
	}

	public static int AddPriv(int userId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE utente SET Privilegio = '1' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		return stmt.executeUpdate();
	}
	
	public static int DelPriv(int userId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE utente SET Privilegio = '0' WHERE utente.ID = ?;");
		stmt.setInt(1, userId);
		return stmt.executeUpdate();
	}
	
	public static ResultSet Login(String login, String passw) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT ID FROM utente WHERE Login = ? AND Passw = ?;");
		stmt.setString(1, login);
		stmt.setString(2, passw);
		ResultSet rs = stmt.executeQuery();
		int userId = rs.getInt(0);
		return Utente.GetUtente(userId);
	}
	
	public static ResultSet SearchUserByLogin (String login) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("SELECT Login FROM utente WHERE Login LIKE %?;");
		stmt.setString(1, login);
		return stmt.executeQuery();
	}
	
	public static int DelUser (int id) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("DELETE FROM utente WHERE utente.ID = ?;");
		stmt.setInt(1, id);
		return stmt.executeUpdate();
	}
}