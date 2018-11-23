package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class RegistrationController {
	
	/**
	 * Implementa la procedura di registrazione
	 * @param 
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
				status = "success";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	

}
