package it.bibliotecadigitale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TranscFormController {

	@FXML
	private Label lblErr1;
	@FXML
	private Label lblErr2;
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
		
		if (mail.isEmpty()) {
			lblErr1.setText("Inserisci contatto");
		}
		else if(info.isEmpty()) {
			lblErr2.setText("Inserisci informazioni prima di sottometere la candidatura");
		}
		else {
			//Invia mail ad un admin o salva su db, non è definito
			Main.toUserProfile(event);
			Main.toCompMsg();
		}
	}

}
