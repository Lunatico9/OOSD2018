package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModPassController {
	
	@FXML
	protected TextField txtOldPass;
	@FXML
	protected TextField txtNewPass1;
	@FXML
	protected TextField txtNewPass2;
	@FXML
	protected Label lblStat;
	
	/**
	 * Modifica la password nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public void change(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!op.equals(Cookie.user.getPassw())) {
				lblStat.setText("Password Errata");
			}
			
			if (!np1.equals(np2) || np1.isEmpty()) {
				lblStat.setText("Le password non combaciano");
			}
			
			if (op.equals(Cookie.user.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
				Cookie.user.setPassw(np1);
				db.modifyPassw(np1, Cookie.user.getId());
				Main.toUserProfile(event);
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}

}
