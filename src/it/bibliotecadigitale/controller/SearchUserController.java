package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.model.dao.UtenteDao;
import it.bibliotecadigitale.model.Utente;

public class SearchUserController {
	
	/**
	 * Genera lista di nomi utente per popolare autocompletamento
	 * 
	 * @param input dati inseriti dall'utente
	 * @param role ruolo selezionato dall'utente
	 * @return array di stringhe contenente username
	 */
	public ArrayList<String> searchUsernames(String input, String role) {
		
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (role == null){
				users = db.searchUserByLogin(input);
		    } 
			else {
			    users = db.searchUserByLogin(input, role);
			}
			
			if(!users.isEmpty()) {
				for(Utente u : users) {
					usernames.add(u.getLogin());
				}
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al database");
			e.printStackTrace();
		}
		
		return usernames;
	}
	
	/**
	 * Genera lista di utenti in seguito ad una ricerca
	 * 
	 * @param input dati inseriti dall'utente
	 * @param role ruolo selezionato dall'utente
	 * @return array di utenti
	 */
	public ArrayList<Utente> search (String input, String role) {
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (role == null){
				users = db.searchUserByLogin(input);

		    } 
			else {
			    users = db.searchUserByLogin(input, role);
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return users;	
	}
	
}
