package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.RegistrationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Registration {
	
	@FXML
	protected Label lblErr;
	@FXML
	protected Hyperlink linkLog;
	@FXML
	protected TextField txtUsername;
	@FXML
	protected TextField txtPassword1;
	@FXML
	protected TextField txtPassword2;
	@FXML
	protected TextField txtName;
	@FXML
	protected TextField txtSurname;
	@FXML
	protected TextField txtMail;
	@FXML
	protected TextField txtTitle;
	@FXML
	protected TextField txtProfession;
	
	
	/**
	 * Procedura di registrazione
	 * @param ActionEvent event
	 */
	public void add(ActionEvent event) {
		String usr = txtUsername.getText();
		String psw1 = txtPassword1.getText();
		String psw2 = txtPassword2.getText();
		String nome = txtName.getText();
		String cnome = txtSurname.getText();
		String mail = txtMail.getText();
		String tit = txtTitle.getText();
		String pro = txtProfession.getText();
		
		String status = "";
		
		if (usr.isEmpty() || psw1.isEmpty() || psw2.isEmpty() || nome.isEmpty() || cnome.isEmpty() || mail.isEmpty() || tit.isEmpty() || pro.isEmpty()) {
			lblErr.setText("Compila tutti i campi prima di proseguire");
		}
		else if(!psw1.equals(psw2)) {
			lblErr.setText("Le password inserite non coincidono");
		}
		else {
			RegistrationController rc = new RegistrationController();
			status = rc.addProcedure(usr, psw1, psw2, nome, cnome, mail, tit, pro);
		}
		
		switch (status) {
		
		case "user":
			lblErr.setText("Username già in uso");
			break;
			
		case "mail":
			lblErr.setText("Email già in uso");
			break;
			
		case "success":
			Main.toCompMsg();
			Main.toHome(event);
			break;
			
		case "error":
			Main.toErrorMsg("Errore in connessione al database");
			break;
		
		default:
			break;
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
