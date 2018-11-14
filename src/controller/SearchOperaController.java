package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import dao.OperaDao;
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
import model.Opera;

public class SearchOperaController implements Initializable{
	
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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
			else if (fil.equals("Per titolo")) {
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
				
				// C'è un bug sull autocompletamento che non sono ancora riuscito a risolvere
				TextFields.bindAutoCompletion(txtSearch, auto);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
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
			else if (fil.equals("Per titolo")) {
				opere = db.searchOperaByName(search);
			}
			else {
				opere = db.searchOperaByAuthor(search);
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
							//Main.toOperaInfo(event);
						}
					});
					
					Text author = new Text(o.getAutore());
					
					pane.addRow(i, link, author);
					
					i++;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}

	}
	
	/**
	 * Costruisce choice box per la selezione della ricerca
	 * @param ActionEvent event
	 */
	
	public void buildChoiceBox() {
		choiceFilter.setItems(FXCollections.observableArrayList("Per titolo", "Per autore"));
		pane.getChildren().clear();
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

