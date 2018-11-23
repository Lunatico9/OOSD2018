package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ContactController {
	
	private final String TIPO = "contatto";
	
	/**
	 * Invia dati tramite la form
	 * @param String mail
	 * @param String info
	 * @return boolean
	 */
	public boolean sendForm(String mail, String info) {
		
		UtenteDao db = new UtenteDao();
		try {
			db.addMessage(mail, info, TIPO, Cookie.user.getId());
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
