package it.bibliotecadigitale.controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.Opera;

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

public class SearchOperaController implements Initializable{
	
	private final String RTITOLO = "Per titolo";
	private final String RAUTORE= "Per autore";
	private final String RAPPROVATE = "Non approvate";
	
	//@FXML
	//private ChoiceBox<String> choiceCategory;
	@FXML
	private TextField txtSearch;
	@FXML
	private ChoiceBox<String> choiceFilter;
	@FXML
	private Label lblSearch;
	@FXML
	private GridPane pane;

	/**
	 * Inizializza il choice box contenente i filtri di ricerca sulla base del ruolo dell'utente
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 's')
		{
			choiceFilter.setItems(FXCollections.observableArrayList(RTITOLO, RAUTORE, RAPPROVATE));
			choiceFilter.setValue(RAPPROVATE);
			pane.getChildren().clear();
		}
		else {
			choiceFilter.setItems(FXCollections.observableArrayList(RTITOLO, RAUTORE));
			pane.getChildren().clear();
		}
	}
	
	public void autoCompletion () {
		String fil = choiceFilter.getValue();
		String input = txtSearch.getText();
		
		ArrayList<String> auto = new ArrayList<String>();
		ArrayList<Opera> opere = new ArrayList<Opera>();
		OperaDao db = new OperaDao();
		
		opere.clear();
		auto.clear();
		lblSearch.setText("");
		
		try {
			if (fil == null){
				opere = db.searchOperaByName(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getTitolo());
					}
				}
		    } 
			else if (fil.equals(RTITOLO)) {
				opere = db.searchOperaByName(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getTitolo());
					}
				}
			}
			else {
				opere = db.searchOperaByAuthor(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getAutore());
					}
				}
			}
			if(!auto.isEmpty()) {
				
				// C'è un bug sull'autocompletamento che non sono ancora riuscito a risolvere
				TextFields.bindAutoCompletion(txtSearch, auto);
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Costruisce dinamicamente lista di opere cercata
	 * @param ActionEvent event
	 */
	
	public void search (ActionEvent event) {
		
		String search = txtSearch.getText();
		String fil = choiceFilter.getValue();
		//String cat = choiceCategory.getValue();
	
		ArrayList<Opera> opere = new ArrayList<Opera>();
		OperaDao db = new OperaDao();
		
		pane.getChildren().clear();

		try {
			if (fil == null) {
				opere = db.searchOperaByName(search);
			}
			else if (fil.equals(RTITOLO)) {
				opere = db.searchOperaByName(search);
			}
			else if (fil.equals(RAUTORE)) {
				opere = db.searchOperaByAuthor(search);
			}
			else {
				opere = db.searchOperaNotApproved();
			}
			
			if(opere.isEmpty()) {
				lblSearch.setText("Nessun opera trovata");
			}
			else {
				int i = 0;
				
				for(Opera o : opere) {
					Hyperlink link = new Hyperlink();
					link.setText(o.getTitolo());
					link.setOnAction(new EventHandler<ActionEvent>() {
						@Override
		                public void handle(ActionEvent event) {
							Cookie.selectedOpera = o;
							Main.toOperaInfo(event);
						}
					});
					
					Text author = new Text(o.getAutore());
					
					pane.addRow(i, link, author);
					
					i++;
				}
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
		}

	}
	
	/*
	 * public void buildChoiceCat() {
		OperaDao db = new OperaDao();
		try {
			ArrayList<String> cat = db.getCategorie();
			choiceCategory.setItems(FXCollections.observableArrayList(cat));
		} catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	
	}*/
}

