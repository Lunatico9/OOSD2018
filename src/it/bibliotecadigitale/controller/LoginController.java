package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController{
	
	@FXML
	private Label lblStatus;
	@FXML
	private Hyperlink linkReg;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	

	/**
	 * Procedura di login
	 * @param ActionEvent event
	 */
	public void login(ActionEvent event) {
		String usr = txtUsername.getText();
		String psw = txtPassword.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (db.login(usr, psw)) {
				lblStatus.setText("Login Successful");
				
				Cookie.user = db.getUtente(usr);
				Main.toHome(event);
			}
			else {
				lblStatus.setText("Login Failed");
			}
	    } catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Indirizza alla pagina di registrazione
	 * @param ActionEvent event
	 */
	public void registration(ActionEvent event) {
		Main.toRegistration(event);
	}
}
