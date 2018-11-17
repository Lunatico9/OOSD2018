package it.bibliotecadigitale.controller;

import java.util.ArrayList;
import java.util.Scanner;

import it.bibliotecadigitale.dao.OperaDao;

import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModOperaController {
	
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
	 * Modifica metadata dell'opera
	 * @param ActionEvent event
	 */
	public void modify(ActionEvent event) {
		OperaDao db = new OperaDao();
		lblErr.setText("");
		
		String tit = txtTit.getText();
		String aut = txtAut.getText();
		String d = txtDate.getText();
		int year = 0;
		
		String cat = choiceCat.getValue();
		
		if (tit.isEmpty() || aut.isEmpty() || choiceCat.getValue() == null || d.isEmpty()) {
			lblErr.setText("Tutti i campi devono essere compilati");
		}
		else {
			
			//controlliamo se la stringa sia solo numerica con espressione regolare
	    	if (d.matches("^[0-9]+$")) {
	    		//utilizziamo Scanner con espressione regolare per estrarre interi da String
	    		Scanner in = new Scanner(txtDate.getText());
		    	in.useDelimiter("[^0-9]+");
	    		year = in.nextInt();
	    		in.close();
		    }
	    	else {
	    		System.out.println("Solo numeri qui");
	    		//Main.toError(event);
	    	}
	    	
			Cookie.selectedOpera.setTitolo(tit);
			Cookie.selectedOpera.setAutore(aut);
			Cookie.selectedOpera.setCategoria(cat);
			Cookie.selectedOpera.setDatazione(year);
			try {
				db.modifyOpera(Cookie.selectedOpera.getId(), tit, aut, cat, year);
			} 
			catch (Exception e) {
				System.out.println("Database Error");
				e.printStackTrace();
			}
			Main.toOperaInfo(event);
		}
		
	}
	
	/**
	 *Inizializza il valore dei TextField e della ChoiceBox
	 */
	public void setValue() {
		ArrayList<String> categories = new ArrayList<String>();
		
		OperaDao db = new OperaDao();
		
		try {
			categories = db.getCategorie();
		} 
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
		
		choiceCat.setItems(FXCollections.observableArrayList(categories));
		choiceCat.setValue(Cookie.selectedOpera.getCategoria());
		
		txtTit.setText(Cookie.selectedOpera.getTitolo());
		txtAut.setText(Cookie.selectedOpera.getAutore());
		
		txtDate.setText(((Integer)Cookie.selectedOpera.getDatazione().getAnno()).toString());
	}
}
