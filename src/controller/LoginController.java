package controller;

import java.util.ArrayList;

import dao.UtenteDao;
import model.Utente;
import view.Login;

public class LoginController {
	public LoginController() throws Exception {
		Login view = new Login (this);
	}
	public boolean login(String login, String passw) throws Exception {
		UtenteDao db = new UtenteDao();
		boolean loginSuccessful = db.Login(login, passw);
		if (loginSuccessful) {
			Utente user = db.GetUserByLogin(login);
			UserDirector direct = new UserDirector(user);
		}
		return loginSuccessful;
	}
}
