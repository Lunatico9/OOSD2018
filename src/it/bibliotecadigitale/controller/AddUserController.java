package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import it.bibliotecadigitale.model.Utente;

public class AddUserController implements Initializable{

	@FXML
	private Label lblError;
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
	@FXML
	private ChoiceBox<String> choiceRole;
	
	/**
	 * Inizializza il choice box dei ruoli
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Revisore trascrizioni", "Supervisore upload", "Amministratore"));
		choiceRole.setValue("Utente");
	}
	
	/**
	 * Aggiunge utente al database
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
		String role = choiceRole.getValue();
		
		lblError.setText("");
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (usr.isEmpty() || psw1.isEmpty() || psw2.isEmpty() || nome.isEmpty() || cnome.isEmpty() || mail.isEmpty() || tit.isEmpty() || pro.isEmpty() || choiceRole.getValue() == null) {
				lblError.setText("Compila tutti i campi prima di procedere");
			}
			else if (!db.isNotRegisteredWithUsername(usr)) {
				lblError.setText("Username già in uso");
			}
			else if (!db.isNotRegisteredWithEmail(mail)) {
				lblError.setText("Email già in uso");
			}
			else if(!psw1.equals(psw2)) {
				lblError.setText("Le password inserite non combaciano");
			}
			else {
				db.addUtente(usr, psw1, nome, cnome, mail, tit, pro);
				Utente ut = new Utente();
				ut = db.getUtente(usr);
				db.modifyRuolo(role.toLowerCase().charAt(0), ut.getId());
				
				txtUsername.clear();
				txtPassword1.clear();
				txtPassword2.clear();
				txtName.clear();
				txtSurname.clear();
				txtMail.clear();
				txtTitle.clear();
				txtProfession.clear();
				choiceRole.setValue("Utente");
				
				Main.toCompMsg();
			}
		} catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
	
}
