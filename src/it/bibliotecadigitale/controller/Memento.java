package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Pagina;
import it.bibliotecadigitale.model.Utente;

public class Memento {
	
	public static Utente user = new Utente(0, "", "", "", "", "", "", "", false, 'n', 0);
	
	public static Utente selectedUser = new Utente();
	
	public static Opera selectedOpera = new Opera();
	
	public static Pagina selectedPage = new Pagina();
	
	public static ArrayList<Pagina> pageList;
	
	/**
	 * Pulisce i campi dell'utente corrente
	 */
	public static void logOut() {
		user.setId(0);
		user.setLogin("");
		user.setPassw("");
		user.setNome("");
		user.setCognome("");
		user.setMail("");
		user.setTitolo("");
		user.setProfessione("");
		user.setLiv(0);
		user.setPriv(false);
		user.setRuolo('n');
	}
}
