package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModPassController {
	
	/**
	 * Modifica la password nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public boolean modifyPassword(String psw) {
		UtenteDao db = new UtenteDao();
		
		try {
			Memento.user.setPassw(psw);
			db.modifyPassw(psw, Memento.user.getId());
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
