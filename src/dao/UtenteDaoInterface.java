package dao;

import java.util.ArrayList;

import model.Utente;

interface UtenteDaoInterface {
	
	void addUtente(String login, String passw, String nome, String cognome) throws Exception;
	
	void modifyPassw(String passw, int userId) throws Exception;
	
	void modifyRuolo(char ruolo, int userId) throws Exception;
	
	void modifyLivello (int userId, int livello) throws Exception;
	
	void addPriv(int userId) throws Exception;
	
	void delPriv(int userId) throws Exception;
	
	boolean login(String login, String passw) throws Exception;
	
	Utente getUtente(String login) throws Exception;
	
	ArrayList<Utente> searchUserByLogin (String login) throws Exception;
	
	ArrayList<Utente> searchUserByRuolo (char ruolo) throws Exception;
	
	void delUtente (int id) throws Exception;
}
