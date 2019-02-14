package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.TranscFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TranscForm {
	
	@FXML
	private Label lblErr;
	@FXML
	private TextArea txtInfo;
	@FXML
	private TextField txtMail;
	
	/**
	 * Controlla i dati del form e li aggiunge al Database
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void submit(ActionEvent event) {
		
		String mail = txtMail.getText();
		String info = txtInfo.getText();
		lblErr.setText("");
		
		if (mail.isEmpty()) {
			lblErr.setText("Inserisci contatto");
		}
		else if(info.isEmpty()) {
			lblErr.setText("Inserisci informazioni prima di sottomettere la candidatura");
		}
		else {
			TranscFormController tc = new TranscFormController();
			if(tc.sendForm(mail, info)) {
				Main.toSearchOpera(event);
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
		}
	}
}
