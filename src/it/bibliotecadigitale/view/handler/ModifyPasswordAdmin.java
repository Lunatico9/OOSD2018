package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModPassAdminController;
import it.bibliotecadigitale.controller.ModPassController;
import it.bibliotecadigitale.helper.Password;
import javafx.event.ActionEvent;

public class ModifyPasswordAdmin extends ModifyPassword{
	
	/**
	 * Effettua controlli sui dati inseriti e modifica la password dell'utente selezionato
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	@Override
	public void update(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		
		lblStat.setText("");
		
		if (!Password.checkPassword(op, Memento.selectedUser.getPassw())) {
			lblStat.setText("Password Errata");
		}
		
		if (!np1.equals(np2) || np1.isEmpty()) {
			lblStat.setText("Le password non coincidono");
		}
		
		if (Password.checkPassword(op, Memento.selectedUser.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
			ModPassController controller = new ModPassAdminController();
			//dynamic binding
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
