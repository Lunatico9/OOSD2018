package dao;

import java.sql.*;

public class Trascrittore {

	public static int AllocateOpera (int userId, int operaId) throws Exception {
	    PreparedStatement stmt = DatabaseOp.PrepareStatement("INSERT INTO trascrittore (Utente, Opera, Data) VALUES ( ?, ?, CURRENT_TIMESTAMP);");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        return stmt.executeUpdate();
	}
	
	public static int DeallocateOpera (int userId, int operaId) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("DELETE FROM trascrittore WHERE trascrittore.Utente = ? AND trascrittore.Opera = ?;");
        stmt.setInt(1, userId);
        stmt.setInt(2, operaId);
        return stmt.executeUpdate();
	}
	
	public static int ModifyLivello (int userId, int livello) throws Exception {
		PreparedStatement stmt = DatabaseOp.PrepareStatement("UPDATE ruolo SET Livello = ? WHERE ruolo.Utente = ?;");
        stmt.setInt(1, livello);
        stmt.setInt(2, userId);
        return stmt.executeUpdate();
	}
}
