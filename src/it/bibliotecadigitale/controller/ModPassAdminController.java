package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.helper.Password;
import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;

public class ModPassAdminController extends ModPassController {

	/**
	 * Modifica la password nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public void change(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		lblStat.setText("");
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!Password.checkPassword(op, Cookie.selectedUser.getPassw())) {
				lblStat.setText("Password Errata");
			}
			
			if (!np1.equals(np2) || np1.isEmpty()) {
				lblStat.setText("Le password non coincidono");
			}
			
			if (Password.checkPassword(op, Cookie.selectedUser.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
				Cookie.selectedUser.setPassw(np1);
				db.modifyPassw(np1, Cookie.selectedUser.getId());
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
}
