package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.SearchUserController;
import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Utente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SearchUser  implements Initializable {

	@FXML
	private TextField txtSearch;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private Label lblSearch;
	@FXML
	private TableView<Utente> userTable;
	@FXML
	private TableColumn<Utente, String> nameColumn;
	@FXML
	private TableColumn<Utente, String> roleColumn;
	
	/**
	 * Inizializza il choice box dei ruoli
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Revisore trascrizioni", "Supervisore upload", "Amministratore"));
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
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void search (ActionEvent event) {
		String role = choiceRole.getValue();
		String input = txtSearch.getText();
		lblSearch.setText("");
		
		ArrayList<Utente> users = new ArrayList<Utente>();
		ObservableList<Utente> values = FXCollections.observableArrayList();
		
		//intercettiamo il click sulla riga della Table View
				userTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
				    @Override 
				    public void handle(MouseEvent event) {
				        if (true) {
				            Node node = ((Node) event.getTarget()).getParent();
				            TableRow<Utente> row;
				            if (node instanceof TableRow) {
				                row = (TableRow) node;
				            } else {
				                // clicking on text part
				                row = (TableRow) node.getParent();
				            }
				            if ((Utente) row.getItem() == null) {
				            	return;
				            } else {
				            	Memento.selectedUser = (Utente) row.getItem();
					            Main.toUserProfileAdmin(event);
				            }
				        }
				    }
				});
		
		//leghiamo le colonne della Table View al corrispondente campo della classe Utente
		nameColumn.setCellValueFactory(new PropertyValueFactory<Utente, String>("login"));
		roleColumn.setCellValueFactory(new PropertyValueFactory<Utente, String>("strRuolo"));

		SearchUserController controller = new SearchUserController();
		users = controller.search(input, role);
		
		if (!users.isEmpty()) {
			
			for(Utente u : users) {
				u.setStrRuolo(role(u.getRuolo()));
				values.add(u);
			}
			
			userTable.setItems(values);
		}
		else {
			lblSearch.setText("Nessun utente trovato");
		}
	}
	
	/**
	 * Prende in input il ruolo dell'utente in formato carattere e lo restituisce in formato stringa
	 * 
	 * @param c ruolo in formato Char
	 * @return ruolo in formato String
	 */
	private String role(char c) {
		switch (c) {
		case 'u':
			return "Utente";
		case 't':
			return "Trascrittore";
		case 'r':
			return "Revisore";
		case 's':
			return "Supervisore";
		case 'a':
			return "Amministratore";
		default:
			return "Errore";
		}
	}
}

