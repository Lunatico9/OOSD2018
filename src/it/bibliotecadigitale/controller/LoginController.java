package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class LoginController{
	
	/**
	 * Implementa la procedura di login
	 * @param String username
	 * @param String password
	 * @return boolean
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