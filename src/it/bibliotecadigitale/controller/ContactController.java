package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ContactController {
	
	private final String TIPO = "contatto";
	
	/**
	 * Aggiunge al Database dati inseriti tramite il form di contatto
	 * 
	 * @param mail indirizzo email dell'utente
	 * @param info testo inserito dall'utente
	 * @return true se il messaggio viene inserito nel database, false altrimenti
	 */
	public boolean sendForm(String mail, String info) {
		
		UtenteDao db = new UtenteDao();
		try {
			db.addMessage(mail, info, TIPO, Memento.user.getId());
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}	
}
