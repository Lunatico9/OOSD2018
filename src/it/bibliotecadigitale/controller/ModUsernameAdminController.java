package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.dao.UtenteDao;
import javafx.event.ActionEvent;

public class ModUsernameAdminController extends ModUsernameController{

	public void change(ActionEvent event) {
		String usr = txtUsername.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!(usr.equals(Cookie.selectedUser.getLogin())) && !(usr.isEmpty())) {
				if(db.isNotRegistered(usr)) {
					Cookie.selectedUser.setLogin(usr);
					db.modifyLogin(usr, Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
				}
				else lblStat.setVisible(true);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}
	
	/**
	 *Inizializza il valore del TextField
	 */
	public void setValue() {
		txtUsername.setText(Cookie.selectedUser.getLogin());
	}
}
