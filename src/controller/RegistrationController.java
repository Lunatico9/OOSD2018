package controller;

import dao.UtenteDao;
import model.Utente;
import view.Registration;

public class RegistrationController {
	public RegistrationController() throws Exception {
		Registration view = new Registration(this);
	}

	public boolean registerUser(String login, String password, String nome, String cognome) throws Exception {
		UtenteDao db = new UtenteDao();
		boolean isNotRegistered = db.isNotRegistered (login);
		if (isNotRegistered) {
			db.addUtente(login, password, nome, cognome);
			return true;
		} else {
			return false;
		}
	}
}
