package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.ContactController;
import it.bibliotecadigitale.controller.Main;
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
	 * Invia dati tramite la form
	 * @param ActionEvent event
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
			ContactController cc = new ContactController();
			if(cc.sendForm(mail, info)) {
				Main.toHome(event);
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
		}
	}

}
