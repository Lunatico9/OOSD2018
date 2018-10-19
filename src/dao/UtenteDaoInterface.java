package dao;

import java.util.ArrayList;

import model.Utente;

interface UtenteDaoInterface {
	
	void AddUtente(String login, String passw, String nome, String cognome) throws Exception;
	
	void ModifyPassw(String passw, int userId) throws Exception;
	
	void ModifyRuolo(char ruolo, int userId) throws Exception;
	
	void ModifyLivello (int userId, int livello) throws Exception;
	
	void AddPriv(int userId) throws Exception;
	
	void DelPriv(int userId) throws Exception;
	
	boolean Login(String login, String passw) throws Exception;
	
	ArrayList<Utente> SearchUserByLogin (String login) throws Exception;
	
	ArrayList<Utente> SearchUserByRuolo (char ruolo) throws Exception;
	
	void DelUtente (int id) throws Exception;
}
