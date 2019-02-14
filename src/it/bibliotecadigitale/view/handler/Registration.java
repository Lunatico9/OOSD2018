package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.AddUserController;
import it.bibliotecadigitale.controller.Main;
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
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public boolean add(ActionEvent event) {
	
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
			AddUserController rc = new AddUserController();
			status = rc.addProcedure(usr, psw1, psw2, nome, cnome, mail, tit, pro);
		}
		
		switch (status) {
		
		case "user":
			lblErr.setText("Username già in uso");
			return false;
			
		case "mail":
			lblErr.setText("Email già in uso");
			return false;
			
		case "success":
			Main.toCompMsg();
			Main.toSearchOpera(event);
			return true;
			
		case "error":
			Main.toErrorMsg("Errore in connessione al database");
			return false;
		
		default:
			return false;
		}		
	}
	
	/**
	 * Permette di tornare alla pagina di login
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void backToLogin(ActionEvent event) {
		Main.toLogin(event);
	}

}
