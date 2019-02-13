package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.AddUserController;
import it.bibliotecadigitale.controller.Main;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class AddUser extends Registration implements Initializable {

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
	 * Aggiunge utente al database utilizzando anche il metodo della classe genitore
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 * @return true se l'utente viene inserito nel database, false altrimenti
	 */
	@Override
	public boolean add(ActionEvent event) {
		if(super.add(event)) {
			String role = choiceRole.getValue();
			String usr = txtUsername.getText();
			
			if (choiceRole.getValue() != null && !usr.isEmpty()) {
				AddUserController auc = new AddUserController();
				if (auc.addProcedure(usr, role)) {
					txtUsername.clear();
				    txtPassword1.clear();
					txtPassword2.clear();
					txtName.clear();
					txtSurname.clear();
					txtMail.clear();
					txtTitle.clear();
					txtProfession.clear();
					choiceRole.setValue("Utente");
					return true;
				}
				else {
					Main.toErrorMsg("Errore in connessione al database");
				}
			}
		}
		return false;
	}		
}
