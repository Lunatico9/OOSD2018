package it.bibliotecadigitale.model.dao;

import java.util.ArrayList;

import it.bibliotecadigitale.model.Utente;

interface UtenteDaoInterface {
	
	/**
	 * Aggiunge nuovo utente al database
	 * @param String login
	 * @param String passw
	 * @param String nome
	 * @param String cognome
	 * @throws Exception
	 */
	void addUtente(String login, String passw, String nome, String cognome) throws Exception;
	
	/**
	 * Modifica lo username di un utente
	 * @param String log
	 * @param int userId
	 * @throws Exception
	 */
	void modifyLogin(String log, int userId) throws Exception;

	/**
	 * Modifica la password di un utente
	 * @param String passw
	 * @param int userId
	 * @throws Exception
	 */
	void modifyPassw(String passw, int userId) throws Exception;
	
	/**
	 * Modifica ruolo di un utente
	 * @param char ruolo
	 * @param int userId
	 * @throws Exception
	 */
	void modifyRuolo(char ruolo, int userId) throws Exception;
	
	/**
	 * Modifica il livello di un utente
	 * @param int userId
	 * @param int livello
	 * @throws Exception
	 */
	void modifyLivello (int userId, int livello) throws Exception;
	
	/**
	 * Aggiunge il privilegio di effettuare il download di opere ad un utente
	 * @param int userId
	 * @throws Exception
	 */
	void addPriv(int userId) throws Exception;
	
	/**
	 * Cancella il privilegio di effettuare il download di opere ad un utente
	 * @param int userId
	 * @throws Exception
	 */
	void delPriv(int userId) throws Exception;
	
	/**
	 * Controlla se la combinazione login password � presente nel database
	 * @param String login
	 * @param String passw
	 * @return boolean
	 * @throws Exception
	 */
	boolean login(String login, String passw) throws Exception;
	
	/**
	 * Restituisce utente con login fornito
	 * @param String login
	 * @return Utente
	 * @throws Exception
	 */
	Utente getUtente(String login) throws Exception;
	
	/**
	 * Controlla se il login fornito è già utilizzato
	 * @param String login
	 * @return boolean
	 * @throws Exception
	 */
	boolean isNotRegistered(String login) throws Exception;
	
	/**
	 * Cerca utenti per login
	 * @param String login
	 * @return ArrayList<Utente>
	 * @throws Exception
	 */
	ArrayList<Utente> searchUserByLogin (String login) throws Exception;
	
	/**
	 * Cerca utenti per login e ruolo
	 * @param String login
	 * @param String role
	 * @return ArrayList<Utente>
	 * @throws Exception
	 */
	ArrayList<Utente> searchUserByLogin (String login, String role) throws Exception;
	
	/*
	 * Cerca utenti per ruolo
	 * @param char ruolo
	 * @return ArrayList<Utente>
	 * @throws Exception
	ArrayList<Utente> searchUserByRuolo (char ruolo) throws Exception;
	*/
	
	/**
	 * Cancella utente dal database
	 * @param int id
	 * @throws Exception
	 */
	void delUtente (int id) throws Exception;
}
