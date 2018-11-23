package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Cookie;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModUsernameController;
import javafx.event.ActionEvent;

public class ModifyUsernameAdmin extends ModifyUsername{
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText(Cookie.selectedUser.getLogin());
	}
	
	/**
	 * Modifica lo username dell'utente
	 * @param ActionEvent event
	 */
	@Override
	public void update(ActionEvent event) {
		String usr = txtUsername.getText();
		
		ModUsernameController controller = new ModUsernameController();
		
		if (controller.modifyUsername(usr, Cookie.selectedUser.getId())) {
			Cookie.selectedUser.setLogin(usr);
			
			Main.toUserProfileAdmin(event);
			Main.toCompMsg();
		}
		else {
			lblStat.setVisible(true);
		}
	}
}
