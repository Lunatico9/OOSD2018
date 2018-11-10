package controller;

import dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController{
	
	@FXML
	private Label lblStatus;
	@FXML
	private Label lblReg;
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
			e.printStackTrace();
		}
	}
	
	public void registration(MouseEvent event) {
		Main.toRegistration(event);
	}
}
