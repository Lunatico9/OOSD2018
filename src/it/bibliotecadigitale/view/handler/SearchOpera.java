package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.SearchOperaController;
import it.bibliotecadigitale.model.Opera;
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

public class SearchOpera implements Initializable {
	
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
		pane.getChildren().clear();
		
		if (Memento.user.getRuolo() == 'a' || Memento.user.getRuolo() == 's')
		{
			choiceFilter.setItems(FXCollections.observableArrayList(RTITOLO, RAUTORE, RAPPROVATE));
			choiceFilter.setValue(RAPPROVATE);
		}
		else {
			choiceFilter.setItems(FXCollections.observableArrayList(RTITOLO, RAUTORE));
		}
	}
	
	/**
	 * Implementa autocompletamento sulla barra di ricerca
	 */
	public void autoCompletion () {
		String fil = choiceFilter.getValue();
		String input = txtSearch.getText();
		
		ArrayList<String> auto = new ArrayList<String>();
		
		SearchOperaController controller = new SearchOperaController();
		auto = controller.searchFiltered(input, fil);
		
		if (!auto.isEmpty()) {
			TextFields.bindAutoCompletion(txtSearch, auto);
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
		
		pane.getChildren().clear();
		lblSearch.setText("");
		
		ArrayList<Opera> opere = new ArrayList<Opera>();
		
		SearchOperaController controller = new SearchOperaController();
		opere = controller.searchOpera(search, fil);
		
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
						Memento.selectedOpera = o;
						Main.toOperaInfo(event);
					}
				});
				
				Text author = new Text(o.getAutore());
				
				GridPane.setHalignment(link, HPos.CENTER);
				GridPane.setHalignment(author, HPos.CENTER);
				
				pane.addRow(i, link, author);
				
				i++;
			}
		}
	}

}
