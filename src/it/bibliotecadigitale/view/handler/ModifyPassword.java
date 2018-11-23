package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.Cookie;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModPassController;
import it.bibliotecadigitale.helper.Password;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyPassword {
	
	@FXML
	protected TextField txtOldPass;
	@FXML
	protected TextField txtNewPass1;
	@FXML
	protected TextField txtNewPass2;
	@FXML
	protected Label lblStat;
	
	/**
	 * Modifica la password dell'utente
	 * @param ActionEvent event
	 */
	public void update(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		
		lblStat.setText("");
		
		if (!Password.checkPassword(op, Cookie.user.getPassw())) {
			lblStat.setText("Password Errata");
		}
		
		if (!np1.equals(np2) || np1.isEmpty()) {
			lblStat.setText("Le password non coincidono");
		}
		
		if (Password.checkPassword(op, Cookie.user.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
			ModPassController controller = new ModPassController();
			if (controller.modifyPassword(np1)) {
				Main.toUserProfile(event);
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
		}
	}
	
}
