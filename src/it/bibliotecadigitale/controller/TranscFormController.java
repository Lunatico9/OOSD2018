package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class TranscFormController {

	private final String TIPO = "trascrittore";
	
	/**
	 * Inserisce nel database i dati forniti nel form
	 * 
	 * @param mail indirizzo email dell'utente
	 * @param info dati inseriti dall'utente
	 * @return true se i dati vengono inseriti nel database, false altrimenti
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