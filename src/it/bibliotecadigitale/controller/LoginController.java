package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class LoginController{
	
	/**
	 * Implementa la procedura di login
	 * 
	 * @param username nome dell'utente
	 * @param password password dell'utente
	 * @return true se l'utente è presente nel Database, false altrimenti
	 */
	public boolean loginProcedure(String usr, String psw) {
		UtenteDao db = new UtenteDao();
		
		try {
			if (db.login(usr, psw)) {
				Memento.user = db.getUtente(usr);
				return true;
			}
			else {
				return false;
			}
	    } 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
			return false;
		}
		
	}
}