package it.bibliotecadigitale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactUsController {
	
	@FXML
	private Label lblErr;
	@FXML
	private TextArea txtInfo;
	@FXML
	private TextField txtMail;
	
	/**
	 * Invia dati tramite la form
	 * @param ActionEvent event
	 */
	public void sendForm(ActionEvent event) {
		String mail = txtMail.getText();
		String info = txtInfo.getText();
		lblErr.setText("");
		
		if (mail.isEmpty()) {
			lblErr.setText("Inserisci contatto");
		}
		else if(info.isEmpty()) {
			lblErr.setText("Inserisci informazioni prima di sottometere la candidatura");
		}
		else {
			//Invia mail ad un admin o salva su db, non è definito
			Main.toUserProfile(event);
			Main.toCompMsg();
		}
	}	
}
