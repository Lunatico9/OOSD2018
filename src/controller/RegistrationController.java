package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController implements Initializable{

	@FXML
	private Label lblError;
	@FXML
	private Hyperlink linkLog;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword1;
	@FXML
	private TextField txtPassword2;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtSurname;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Procedura di registrazione
	 * @param ActionEvent event
	 */
	public void signUp(ActionEvent event) {
		String usr = txtUsername.getText();
		String psw1 = txtPassword1.getText();
		String psw2 = txtPassword2.getText();
		String nome = txtName.getText();
		String cnome = txtSurname.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (!db.isNotRegistered(usr)) {
				lblError.setText("Username già in uso");
			}
			else if(psw1 == psw2) {
				lblError.setText("Le password inserite non combaciano");
			}
			else {
				db.addUtente(usr, psw1, nome, cnome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Permette di tornare alla pagina di login
	 * @param ActionEvent event
	 */
	public void backToLogin(ActionEvent event) {
		Main.toLogin(event);
	}
}
