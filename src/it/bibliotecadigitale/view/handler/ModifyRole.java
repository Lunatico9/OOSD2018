package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModRoleController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyRole implements Initializable{

	@FXML
	private Label lblErr;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private TextField txtLiv;
	
	/**
	 * Inizializza il choice box dei ruoli e il text field del livello
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Revisore trascrizioni", "Supervisore upload", "Amministratore"));

		switch (Memento.selectedUser.getRuolo()) {
		case 'u':
			choiceRole.setValue("Utente");
			break;
		case 't':
			choiceRole.setValue("Trascrittore");
			Integer i = (Integer)Memento.selectedUser.getLiv();
			txtLiv.setText(i.toString());
			break;
		case 'r':
			choiceRole.setValue("Revisore trascrizioni");
			break;
		case 's':
			choiceRole.setValue("Supervisore upload");
			break;
		case 'a':
			choiceRole.setValue("Amministratore");
			break;
		default:
			Main.toErrorMsg("L'utente non ha ruolo valido");
		}	
		
		txtLiv.setText(((Integer)Memento.selectedUser.getLiv()).toString());
	}
	
	/**
	 * Modifica il ruolo dell'utente selezionato
	 * @param event
	 */
	public void update(ActionEvent event) {
		String role = choiceRole.getValue();
		String liv = txtLiv.getText();
		Integer i = (Integer)Memento.selectedUser.getLiv();
		String livello = i.toString();
		
		lblErr.setText("");
		
		ModRoleController controller = new ModRoleController();
		
		if (!role.equals("Trascrittore") && !liv.equals("0")) {
			lblErr.setText("Solo i Trascrittori aumentano di livello");
		}
		else if(liv.equals(livello)) {
			if (controller.modifyRole(role)) {
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
		}
		else {
			if (controller.modifyRole(role, liv)) {
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
			}
			else {
				lblErr.setText("Input non valido");
			}
		}
		
	}
}
