package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ContactUsController {
	
	private final String TIPO = "contatto";
	
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
			UtenteDao db = new UtenteDao();
			try {
				db.addMessage(mail, info, TIPO, Cookie.user.getId());
			} 
			catch (Exception e) {
				Main.toErrorMsg("Errore in connessione al Database");
				e.printStackTrace();
			}
			
			Main.toUserProfile(event);
			Main.toCompMsg();
		}
	}	
}
