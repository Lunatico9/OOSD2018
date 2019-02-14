package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.OperaDao;

public class ViewerController {
	
	/**
	 * Controlla se l'opera è assegnata all'utente
	 * 
	 * @param userId identificativo dell'utente
	 * @param operaId identificativo dell'opera
	 * @return true se l'opera è assegnata all'utente, false altrimenti
	 */
	public boolean isAssigned(int userId, int operaId) {
		OperaDao db = new OperaDao();
		
		try {
			if(db.isAssigned(userId, operaId)) return true;
			else return false;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
