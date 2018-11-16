package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TranscFormController implements Initializable {

	@FXML
	private Label lblErr1;
	@FXML
	private Label lblErr2;
	@FXML
	private TextArea txtInfo;
	@FXML
	private TextField txtMail;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Procedura di registrazione
	 * @param ActionEvent event
	 */
	public void sendForm(ActionEvent event) {
		String mail = txtMail.getText();
		String info = txtInfo.getText();
		
		try {
			if (mail.isEmpty()) {
				lblErr1.setText("Inserisci contatto");
			}
			else if(info.isEmpty()) {
				lblErr2.setText("Inserisci informazioni prima di sottometere la candidatura");
			}
			else {
				//Invia mail ad un admin o salva su db, non � definito
				Main.toUserProfile(event);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
