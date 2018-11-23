package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Cookie;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModOperaController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyOpera implements Initializable{
	
	@FXML
	private Label lblErr;
	@FXML
	private ChoiceBox<String> choiceCat;
	@FXML
	private TextField txtTit;
	@FXML
	private TextField txtAut;
	@FXML
	private TextField txtDate;
	
	/**
	 * Inizializza i text field con i valori dell'opera da modificare e il choice box con le categorie del sistema
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> categories = new ArrayList<String>();
		
		ModOperaController controller = new ModOperaController();
		categories = controller.getCategories();
		
		if (categories != null) {
			choiceCat.setItems(FXCollections.observableArrayList(categories));
			choiceCat.setValue(Cookie.selectedOpera.getCategoria());
			
			txtTit.setText(Cookie.selectedOpera.getTitolo());
			txtAut.setText(Cookie.selectedOpera.getAutore());
			
			txtDate.setText(((Integer)Cookie.selectedOpera.getDatazione().getAnno()).toString());
		}
		else {
			Main.toErrorMsg("Errore in connessione al Database");
		}
		
	}
	
	public void modify(ActionEvent event) {
		lblErr.setText("");
		
		String tit = txtTit.getText();
		String aut = txtAut.getText();
		String d = txtDate.getText();
		
		String cat = choiceCat.getValue();
		
		if (tit.isEmpty() || aut.isEmpty() || choiceCat.getValue() == null || d.isEmpty()) {
			lblErr.setText("Tutti i campi devono essere compilati");
		}
		else {
			//controlliamo se la stringa sia solo numerica con espressione regolare
	    	if (d.matches("^[0-9]+$")) {
	    		ModOperaController controller = new ModOperaController();
	    		if (controller.modify(Cookie.selectedOpera.getId(), tit, aut, cat, d)) {
	    			Cookie.selectedOpera.setTitolo(tit);
	    			Cookie.selectedOpera.setAutore(aut);
	    			Cookie.selectedOpera.setCategoria(cat);
	    			
	    			Main.toOperaInfo(event);
	    			Main.toCompMsg();
	    		}
	    		else {
	    			Main.toErrorMsg("Errore in connessione al Database");
	    		}
	    	}
	    	else {
	    		lblErr.setText("Solo numeri qui");
	    	}
		}
	}
}
