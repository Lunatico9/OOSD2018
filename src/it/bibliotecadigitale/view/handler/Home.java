package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class Home implements Initializable{
		
	@FXML
	private Label lblUser;

	/**
	* Inizializza il valore del label con username dell'utente
	*/
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblUser.setText("Welcome " + Memento.user.getNome());
	}
	
	/**
	 * Indirizza alla pagina di riepilogo dell'utente
	 * @param ActionEvent event
	 */
	public void userPanel(ActionEvent event) {
		Main.toUserProfile(event);
	}
		
}
