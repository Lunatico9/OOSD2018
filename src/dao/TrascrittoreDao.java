package dao;

import java.sql.*;

public class TrascrittoreDao {

	public static void AllocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("INSERT INTO trascrittore (Utente, Opera, Data) VALUES ( ?, ?, CURRENT_TIMESTAMP);");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public static void DeallocateOpera (int userId, int operaId) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("DELETE FROM trascrittore WHERE trascrittore.Utente = ? AND trascrittore.Opera = ?;");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        stmt.executeUpdate();
        op.close(stmt);
	}
	
	public static void ModifyLivello (int userId, int livello) throws Exception {
		DatabaseOp op = new DatabaseOp();
		PreparedStatement stmt = op.pStatement("UPDATE ruolo SET Livello = ? WHERE ruolo.Utente = ?;");
        stmt.setInt(1, livello);
        stmt.setInt(2, userId);
        stmt.executeUpdate();
        op.close(stmt);
	}
}
