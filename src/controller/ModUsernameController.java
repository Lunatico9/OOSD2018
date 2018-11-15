package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import dao.UtenteDao;

public class ModUsernameController {
	
	@FXML
	protected TextField txtUsername;
	@FXML
	protected Label lblStat;
	
	/**
	 * Modifica lo username nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public void change(ActionEvent event) {
		String usr = txtUsername.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!(usr.equals(Cookie.user.getLogin())) && !(usr.isEmpty())) {
				if(db.isNotRegistered(usr)) {
					Cookie.user.setLogin(usr);
					db.modifyLogin(usr, Cookie.user.getId());
					Main.toUserProfile(event);
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
		txtUsername.setText(Cookie.user.getLogin());
	}

}
