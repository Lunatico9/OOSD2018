package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;
import it.bibliotecadigitale.model.Utente;

public class AddUserController {

	/**
	 * Aggiunge utente al database
	 * @param ActionEvent event
	 */
	public boolean addProcedure(String usr, String role) {
		
		UtenteDao db = new UtenteDao();
		
		Utente ut = new Utente();
		try {
			ut = db.getUtente(usr);
			db.modifyRuolo(role.toLowerCase().charAt(0), ut.getId());
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
