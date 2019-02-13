package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.Utente;
import it.bibliotecadigitale.model.dao.UtenteDao;

public class AddUserController {
	
	/**
	 * Aggiunge l'utente al Database
	 * 
	 * @param usr nome utente
	 * @param psw1 password inserita dall'utente
	 * @param psw2 password inserita nel campo di conferma
	 * @param nome nome dell'utente
	 * @param cnome cognome dell'utente
	 * @param mail indirizzo email dell'utente
	 * @param tit titolo di studio dell'utente
	 * @param pro professione dell'utente
	 * @return stringa di stato dell'operazione
	 */
	public String addProcedure(String usr, String psw1, String psw2, String nome, String cnome, String mail, String tit, String pro) {
		
		UtenteDao db = new UtenteDao();
		
		String status = "error";
		
		try {
		    if (!db.isNotRegisteredWithUsername(usr)) {
		    	status = "user";
			}
			else if (!db.isNotRegisteredWithEmail(mail)) {
				status = "mail";
			}
			else {
				db.addUtente(usr, psw1, nome, cnome, mail, tit, pro);
				Memento.user = db.getUtente(usr);
				status = "success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	/**
	 * Supporto alla procedura di aggiunta dell'utente, recupera i dati dell'utente dal database e ne modifica il ruolo
	 * 
	 * @param usr nome utente
	 * @param role ruolo dell'utente
	 * @return true se l'utente viene inserito nel database, false altrimenti
	 */
	public boolean addProcedure(String usr, String role) {
		
		UtenteDao db = new UtenteDao();
		
		Utente ut = new Utente();
		try {
			ut = db.getUtente(usr);
			db.modifyRuolo(role.toLowerCase().charAt(0), ut.getId());
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
