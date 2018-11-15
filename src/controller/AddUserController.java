package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Utente;

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
	private ChoiceBox<String> choiceRole;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
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
		String role = choiceRole.getValue();
		
		UtenteDao db = new UtenteDao();
		
		try {
			if (usr.isEmpty() || psw1.isEmpty() || psw2.isEmpty() || nome.isEmpty() || cnome.isEmpty() || choiceRole.getValue() == null) {
				lblError.setText("Compila tutti i campi prima di procedere");
			}
			else if (!db.isNotRegistered(usr)) {
				lblError.setText("Username già in uso");
			}
			else if(psw1.equals(psw2)) {
				lblError.setText("Le password inserite non combaciano");
			}
			else {
				db.addUtente(usr, psw1, nome, cnome);
				Utente ut = new Utente();
				ut = db.getUtente(usr);
				db.modifyRuolo(role.charAt(0), ut.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Costruisce choice box per la selezione del ruolo
	 * @param ActionEvent event
	 */
	public void buildChoiceBox() {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Supervisore trascrizioni", "Manager upload", "Amministratore"));
	}
	
}
