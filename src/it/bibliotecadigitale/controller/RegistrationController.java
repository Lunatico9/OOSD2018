package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController implements Initializable {

	@FXML
	private Label lblErr;
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
			if (usr.isEmpty() || psw1.isEmpty() || psw2.isEmpty() || nome.isEmpty() || cnome.isEmpty()) {
				lblErr.setText("Compila tutti i campi prima di proseguire");
			}
			else if (!db.isNotRegistered(usr)) {
				lblErr.setText("Username già in uso");
			}
			else if(!psw1.equals(psw2)) {
				lblErr.setText("Le password inserite non combaciano");
			}
			else {
				db.addUtente(usr, psw1, nome, cnome);
			}
		} 
		catch (Exception e) {
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
