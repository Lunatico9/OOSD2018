package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.ContactController;
import it.bibliotecadigitale.controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Contact {
	
	@FXML
	private Label lblErr;
	@FXML
	private TextArea txtInfo;
	@FXML
	private TextField txtMail;
	
	/**
	 * Effettua controllo sui dati inseriti nel form di contatto e li inserisce nel Database
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
			lblErr.setText("Inserisci informazioni prima di inviare il messaggio");
		}
		else {
			ContactController cc = new ContactController();
			if(cc.sendForm(mail, info)) {
				Main.toSearchOpera(event);
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
		}
	}

}
