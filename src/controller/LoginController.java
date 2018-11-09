package controller;

import dao.UtenteDao;
import model.Utente;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
	private Label lblStatus;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;
	
	public void login(ActionEvent event) {
		String usr = txtUsername.getText();
		String psw = txtPassword.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (db.login(usr, psw)) this.lblStatus.setText("Login Successful");
			else lblStatus.setText("Login Failed");
		} catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
}
