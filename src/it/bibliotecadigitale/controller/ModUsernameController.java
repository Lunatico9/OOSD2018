package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModUsernameController {
	
	/**
	 * Modifica lo username nel Cookie e nel database
	 * @param String usr
	 * @return boolean
	 */
	public boolean modifyUsername(String usr, int id) {
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!(usr.isEmpty())) {
				if(db.isNotRegisteredWithUsername(usr)) {
					db.modifyLogin(usr, id);
					
					return true;
				}
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return false;
	}
}
