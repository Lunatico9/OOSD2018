package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModPassAdminController extends ModPassController{

	/**
	 * Modifica la password dell'utente selezionato
	 * 
	 * @param psw nuova password da inserire
	 */
	@Override
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
