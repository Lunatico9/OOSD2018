package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Utente;

public class Cookie {
	
	public static Utente user = new Utente();
	
	public static Utente selectedUser = new Utente();
	
	public static Opera selectedOpera = new Opera();
	
	public static void logOut() {
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
