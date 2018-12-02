package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModRoleController{
	
	/**
	 * Effettua il cambiamento del ruolo
	 * @param String role
	 * @return boolean
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
	 * @param String role
	 * @return boolean
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
