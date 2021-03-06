package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModUserController {
	
	/**
	 * Modifica lo username dell'utente
	 * 
	 * @param String usr
	 * @param int id
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
	
	/**
	 * Modifica il privilegio dell'utente selezionato
	 * 
	 * @param usr username
	 * @param id identificativo dell'utente
	 * @return true se l'operazione riesce, false altrimenti
	 */
	public boolean modifyPriv() {
		
		UtenteDao db = new UtenteDao();
		Memento.selectedUser.setPriv(!Memento.selectedUser.getPriv());
		
		try {
			if (Memento.selectedUser.getPriv()) {
				db.addPriv(Memento.selectedUser.getId());
				return true;
			}
			else {
				db.delPriv(Memento.selectedUser.getId());
				return true;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * Effettua il cambiamento del ruolo
	 * 
	 * @param role ruolo dell'utente selezionato
	 * @return true se il ruolo viene modificato, false altrimenti
	 */
	public boolean modifyRole(String role) {

		UtenteDao db = new UtenteDao();
		Memento.selectedUser.setRuolo(role.toLowerCase().charAt(0));
		
		try {
			db.modifyRuolo(Memento.selectedUser.getRuolo(), Memento.selectedUser.getId());
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	/**
	 * Effettua il cambiamento di ruolo e livello
	 * 
	 * @param role ruolo dell'utente
	 * @param liv livello dell'utente
	 * @return true se ruolo e livello vengono modificati, false altrimenti
	 */
	public boolean modifyRole(String role, String liv) {
		switch (liv) {
		case "0":
			return doubleChange(role, 0);
		case "1":
			return doubleChange(role, 1);
		case "2":
			return doubleChange(role, 2);
		case "3":
			return doubleChange(role, 3);
		case "4":
			return doubleChange(role, 4);
		case "5":
			return doubleChange(role, 5);
		default: 
			return false;
		}
	}
	
	/**
	 * Effettua il cambiamento di ruolo e livello nel Database
	 * 
	 * @param role ruolo dell'utente
	 * @param liv livello dell'utente
	 * @return true se ruolo e livello vengono modificati, false altrimenti
	 */
	private boolean doubleChange(String role, int liv) {
		UtenteDao db = new UtenteDao();
		
		try {
			Memento.selectedUser.setLiv(liv);
			db.modifyLivello(Memento.selectedUser.getLiv(), Memento.selectedUser.getId());
		    Memento.selectedUser.setRuolo(role.toLowerCase().charAt(0));
		    db.modifyRuolo(Memento.selectedUser.getRuolo(), Memento.selectedUser.getId());
		    return true;
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
			return false;
		}
	}
}
