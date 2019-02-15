package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.SearchOperaController;
import it.bibliotecadigitale.model.Opera;
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
	private TableView<Opera> operaTable;
	@FXML
	private TableColumn<Opera, String> titleColumn;
	@FXML
	private TableColumn<Opera, String> authorColumn;

	/**
	 * Inizializza il choice box contenente i filtri di ricerca sulla base del ruolo dell'utente
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void search (ActionEvent event) {
		String search = txtSearch.getText();
		String fil = choiceFilter.getValue();
		//String cat = choiceCategory.getValue();
		
		lblSearch.setText("");
		
		ArrayList<Opera> opere = new ArrayList<Opera>();
		ObservableList<Opera> values = FXCollections.observableArrayList();
		
		//intercettiamo il click sulla riga della Table View
		operaTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (true) {
		            Node node = ((Node) event.getTarget()).getParent();
		            TableRow<Opera> row;
		            if (node instanceof TableRow) {
		                row = (TableRow) node;
		            } else {
		                // clicking on text part
		                row = (TableRow) node.getParent();
		            }
		            if ((Opera)row.getItem() == null) {
		            	return;
		            } else {
		            	Memento.selectedOpera = (Opera) row.getItem();
		            	Main.toOperaInfo(event);
		            }
		        }
		    }
		});
		
		//leghiamo le colonne della Table View al corrispondente campo della classe Utente
		titleColumn.setCellValueFactory(new PropertyValueFactory<Opera, String>("titolo"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Opera, String>("autore"));
		
		SearchOperaController controller = new SearchOperaController();
		opere = controller.searchOpera(search, fil);
		
		if(opere.isEmpty()) {
			lblSearch.setText("Nessun opera trovata");
		}
		else {
			for(Opera o : opere) {
				values.add(o);
			}
			
			operaTable.setItems(values);
		}
	}

}
