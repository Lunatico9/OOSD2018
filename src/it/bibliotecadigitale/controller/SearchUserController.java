package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import it.bibliotecadigitale.model.Utente;

public class SearchUserController implements Initializable {

	@FXML
	private TextField txtSearch;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private Label lblSearch;
	@FXML
	private GridPane pane;
	


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Implementa autocompletamento sulla barra di ricerca
	 * @param ActionEvent event
	 */
	
	public void autoCompletion () {
		String role = choiceRole.getValue();
		String input = txtSearch.getText();
		
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		UtenteDao db = new UtenteDao();
		
		users.clear();
		usernames.clear();
		lblSearch.setText("");
		
		try {
			if (choiceRole.getValue() == null){
				users = db.searchUserByLogin(input);
		    } 
			else {
			    users = db.searchUserByLogin(input, role);
			}
			
			if(!users.isEmpty()) {
				for(Utente u : users) {
					usernames.add(u.getLogin());
				}
				// C'è un bug sull'autocompletamento che non sono ancora riuscito a risolvere
				TextFields.bindAutoCompletion(txtSearch, usernames);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Costruisce dinamicamente lista di utenti cercata
	 * @param ActionEvent event
	 */
	
	public void search (ActionEvent event) {
		String role = choiceRole.getValue();
		String input = txtSearch.getText();
		
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		UtenteDao db = new UtenteDao();
		
		pane.getChildren().clear();
		
		try {
			if (role == null){
				users = db.searchUserByLogin(input);

		    } 
			else {
			    users = db.searchUserByLogin(input, role);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
		
		if (!users.isEmpty()) {
			int i = 0;
			
			for(Utente u : users) {
				Hyperlink link = new Hyperlink();
				link.setText(u.getLogin());
				link.setOnAction(new EventHandler<ActionEvent>() {
					@Override
	                public void handle(ActionEvent event) {
						Cookie.selectedUser = u;
						Main.toUserProfileAdmin(event);
					}
				});
				
				Text r = role(u.getRuolo());
				
				pane.addRow(i, link, r);
				
				i++;
			}
		}
		else {
			lblSearch.setText("Nessun utente trovato");
		}
		
	}
	
	/**
	 * Costruisce choice box per la selezione del ruolo
	 * @param ActionEvent event
	 */
	
	public void buildChoiceBox() {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Supervisore trascrizioni", "Manager upload", "Amministratore"));
		pane.getChildren().clear();
	}
	
	
	public Text role(char c) {
		
		Text t = new Text();
		switch (c) {
		case 'u':
			t.setText("Utente");
			break;
		case 't':
			t.setText("Trascrittore");
			break;
		case 's':
			t.setText("Supervisore");
			break;
		case 'm':
			t.setText("Manager");
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
