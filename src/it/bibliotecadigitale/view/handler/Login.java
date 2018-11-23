package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.LoginController;
import it.bibliotecadigitale.controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Login {
		
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
		
		LoginController lc = new LoginController();
		
		if (lc.loginProcedure(usr, psw)){
			Main.toHome(event);
		}
		else {
			lblStatus.setText("Login Failed");
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
