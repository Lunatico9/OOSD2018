package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModUserController;
import javafx.event.ActionEvent;

public class ModifyUsernameAdmin extends ModifyUsername{
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText(Memento.selectedUser.getLogin());
	}
	
	/**
	 * Modifica lo username dell'utente selezionato
	 * 
	 * @param event che provoca la chiamata del metodo
	 */
	@Override
	public void update(ActionEvent event) {
		String usr = txtUsername.getText();
		
		ModUserController controller = new ModUserController();
		
		if (controller.modifyUsername(usr, Memento.selectedUser.getId())) {
			Memento.selectedUser.setLogin(usr);
			
			Main.toUserProfileAdmin(event);
			Main.toCompMsg();
		}
		else {
			lblStat.setVisible(true);
		}
	}
}
