package controller;

import model.Utente;

public class Cookie {
	
	public static Utente user = new Utente();
	
	public static void destroy() {
		user.setId(0);
		user.setLogin("");
		user.setPassw("");
		user.setNome("");
		user.setCognome("");
		user.setLiv(0);
		user.setPriv(false);
		user.setRuolo('n');
	}

}
