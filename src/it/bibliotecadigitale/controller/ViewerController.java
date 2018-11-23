package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.OperaDao;

public class ViewerController {
	
	/**
	 * Controlla se l'opera è assegnata all'utente
	 * @param int userId
	 * @param int operaId
	 * @return boolean
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
