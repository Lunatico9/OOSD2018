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
			ArrayList<Utente> userList = db.SearchUserByLogin(login);
			Utente user = null;
			for (Utente next : userList) {
				if (next.getLogin().equals(login)) {
					user = next;
					break;
				}
			}
			if (user == null) {
				Exception e = new Exception();
				throw e;
			}
			UserController control = new UserController(user);
		}
		return loginSuccessful;
	}
}
