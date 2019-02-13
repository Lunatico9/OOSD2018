package it.bibliotecadigitale.model.dao;

import java.util.ArrayList;

import it.bibliotecadigitale.model.Utente;

interface UtenteDaoInterface {
	
	/**
	 * Aggiunge nuovo utente al database
	 * 
	 * @param login dell'utente
	 * @param passw dell'utente
	 * @param nome dell'utente
	 * @param cognome dell'utente
	 * @param mail dell'utente
	 * @param titolo di studio dell'utente
	 * @param professione dell'utente
	 * @throws Exception
	 */
	void addUtente(String login, String passw, String nome, String cognome, String mail, String titolo, String professione) throws Exception;
	
	/**
	 * Aggiungi messaggio al database
	 * 
	 * @param cont contatto inserito dell'utemte
	 * @param msg messaggio inserito
	 * @param tipo del messaggio
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void addMessage(String cont, String msg, String tipo, int userId) throws Exception;
	
	/**
	 * Modifica lo username di un utente
	 * 
	 * @param log username dell'utente
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void modifyLogin(String log, int userId) throws Exception;

	/**
	 * Modifica la password di un utente
	 * 
	 * @param passw dell'utente
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void modifyPassw(String passw, int userId) throws Exception;
	
	/**
	 * Modifica ruolo di un utente
	 * 
	 * @param ruolo dell'utente
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void modifyRuolo(char ruolo, int userId) throws Exception;
	
	/**
	 * Modifica il livello di un utente
	 * 
	 * @param userId identificativo dell'utente
	 * @param livello dell'utente
	 * @throws Exception
	 */
	void modifyLivello (int userId, int livello) throws Exception;
	
	/**
	 * Aggiunge il privilegio di effettuare il download di opere ad un utente
	 * 
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void addPriv(int userId) throws Exception;
	
	/**
	 * Cancella il privilegio di effettuare il download di opere ad un utente
	 * 
	 * @param userId identificativo dell'utente
	 * @throws Exception
	 */
	void delPriv(int userId) throws Exception;
	
	/**
	 * Controlla se la combinazione login password ш presente nel database
	 * 
	 * @param login dell'utente
	 * @param passw dell'utente
	 * @return true se la combinazione ш presente, false altrimenti
	 * @throws Exception
	 */
	boolean login(String login, String passw) throws Exception;
	
	/**
	 * Restituisce utente con login fornito
	 * 
	 * @param login dell'utente
	 * @return Utente
	 * @throws Exception
	 */
	Utente getUtente(String login) throws Exception;
	
	/**
	 * Controlla se il login fornito ш giра utilizzato con username
	 * 
	 * @param login dell'utente
	 * @return true se non ш presente un utente con lo stesso username, false altrimenti
	 * @throws Exception
	 */
	boolean isNotRegisteredWithUsername(String login) throws Exception;
	
	/**
	 * Controlla se il login fornito ш giра utilizzato con email
	 * 
	 * @param mail dell'utente
	 * @return true se non ш presente un utente con la stessa mail, false altrimenti
	 * @throws Exception
	 */
	boolean isNotRegisteredWithEmail(String mail) throws Exception;
	
	/**
	 * Cerca utenti per login
	 * 
	 * @param login dell'utente
	 * @return ArrayList di utenti
	 * @throws Exception
	 */
	ArrayList<Utente> searchUserByLogin (String login) throws Exception;
	
	/**
	 * Cerca utenti per login e ruolo
	 * 
	 * @param login dell'utente
	 * @param role dell'utente
	 * @return ArrayList di utenti
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
	 * @param id identificativo dell'utente
	 * @throws Exception
	 */
	void delUtente (int id) throws Exception;
}
