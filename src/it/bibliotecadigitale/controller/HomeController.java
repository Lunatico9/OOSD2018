package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable{
	
	@FXML
	private Label lblUser;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	public void setName() {
		lblUser.setText("Welcome " + Cookie.user.getNome());
	}
	
	/**
	 * Indirizza alla pagina di riepilogo dell'utente
	 * @param ActionEvent event
	 */
	public void userPanel(ActionEvent event) {
		Main.toUserProfile(event);
	}

}
