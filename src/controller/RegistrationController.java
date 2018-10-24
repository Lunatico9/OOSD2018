package controller;

import java.util.ArrayList;

import dao.UtenteDao;
import model.Utente;
import view.Registration;

public class RegistrationController {
	public RegistrationController () throws Exception {
		Registration view = new Registration(this);
	}
	public boolean registerUser (String login, String password, String nome, String cognome) throws Exception {
		UtenteDao db = new UtenteDao();
		ArrayList<Utente> users = db.searchUserByLogin(login);
		for (Utente checkedUser: users) {
			if (checkedUser.getLogin().equals(login)) {
				return false;
			}
		}
		db.addUtente(login, password, nome, cognome);
		return true;
	}
}
