package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.OperaDao;

public class ViewerController {
	
	/**
	 * Controlla se l'opera � assegnata all'utente
	 * 
	 * @param userId identificativo dell'utente
	 * @param operaId identificativo dell'opera
	 * @return true se l'opera � assegnata all'utente, false altrimenti
	 */
	public boolean isAssigned(int userId, int operaId) {
		OperaDao db = new OperaDao();
		
		try {
			db.isAssigned(userId, operaId);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
