package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModPassAdminController extends ModPassController{

	/**
	 * Modifica la password nel Cookie e nel database dell'utente selezionato
	 * @param String psw
	 */
	public boolean modifyPassword(String psw) {
		UtenteDao db = new UtenteDao();
		
		try {
			Memento.selectedUser.setPassw(psw);
			db.modifyPassw(psw, Memento.selectedUser.getId());
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
