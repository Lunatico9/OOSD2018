package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.SearchUserController;
import it.bibliotecadigitale.model.Utente;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SearchUser  implements Initializable {

	@FXML
	private TextField txtSearch;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private Label lblSearch;
	@FXML
	private GridPane pane;
	
	/**
	 * Inizializza il choice box dei ruoli e ripulisce il Grid Pane
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Revisore trascrizioni", "Supervisore upload", "Amministratore"));
		pane.getChildren().clear();
	}
	
	/**
	 * Implementa autocompletamento sulla barra di ricerca
	 */
	public void autoCompletion () {
		String input = txtSearch.getText();
		String role = choiceRole.getValue();
		
		ArrayList<String> usernames = new ArrayList<String>();
		
		SearchUserController controller = new SearchUserController();
		usernames = controller.searchUsernames(input, role);
		
		if (!usernames.isEmpty()) {
			TextFields.bindAutoCompletion(txtSearch, usernames);
		}
	}
	
	/**
	 * Costruisce dinamicamente lista di utenti cercata
	 * @param ActionEvent event
	 */
	public void search (ActionEvent event) {
		String role = choiceRole.getValue();
		String input = txtSearch.getText();
		lblSearch.setText("");
		
		pane.getChildren().clear();
		
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		SearchUserController controller = new SearchUserController();
		users = controller.search(input, role);
		
		if (!users.isEmpty()) {
			int i = 0;
			
			for(Utente u : users) {
				Hyperlink link = new Hyperlink();
				link.setText(u.getLogin());
				link.setOnAction(new EventHandler<ActionEvent>() {
					@Override
	                public void handle(ActionEvent event) {
						Memento.selectedUser = u;
						Main.toUserProfileAdmin(event);
					}
				});
				
				Text r = role(u.getRuolo());
				
				GridPane.setHalignment(link, HPos.CENTER);
				GridPane.setHalignment(r, HPos.CENTER);
				
				pane.addRow(i, link, r);
				
				i++;
			}
		}
		else {
			lblSearch.setText("Nessun utente trovato");
		}
	}
	
	private Text role(char c) {
		
		Text t = new Text();
		switch (c) {
		case 'u':
			t.setText("Utente");
			break;
		case 't':
			t.setText("Trascrittore");
			break;
		case 'r':
			t.setText("Revisore");
			break;
		case 's':
			t.setText("Supervisore");
			break;
		case 'a':
			t.setText("Amministratore");
			break;
		default:
			t.setText("Errore");
		}
		return t;
	}
		
}
