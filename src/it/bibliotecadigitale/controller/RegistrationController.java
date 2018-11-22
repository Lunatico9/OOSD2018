package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegistrationController {

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
	@FXML
	private TextField txtMail;
	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtProfession;
	
	
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
		String mail = txtMail.getText();
		String tit = txtTitle.getText();
		String pro = txtProfession.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (usr.isEmpty() || psw1.isEmpty() || psw2.isEmpty() || nome.isEmpty() || cnome.isEmpty() || mail.isEmpty() || tit.isEmpty() || pro.isEmpty()) {
				lblErr.setText("Compila tutti i campi prima di proseguire");
			}
			else if (!db.isNotRegisteredWithUsername(usr)) {
				lblErr.setText("Username già in uso");
			}
			else if (!db.isNotRegisteredWithEmail(mail)) {
				lblErr.setText("Email già in uso");
			}
			else if(!psw1.equals(psw2)) {
				lblErr.setText("Le password inserite non coincidono");
			}
			else {
				db.addUtente(usr, psw1, nome, cnome, mail, tit, pro);
			}
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
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
