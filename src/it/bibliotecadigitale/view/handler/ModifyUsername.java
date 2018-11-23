package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Cookie;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModUsernameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyUsername implements Initializable {
	
	@FXML
	protected TextField txtUsername;
	@FXML
	protected Label lblStat;
	
	/**
	 * Inizializza il text field con lo username dell'utente
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText(Cookie.user.getLogin());
	}
	
	/**
	 * Modifica lo username dell'utente
	 * @param ActionEvent event
	 */
	public void update(ActionEvent event) {
		String usr = txtUsername.getText();
		
		ModUsernameController controller = new ModUsernameController();
		
		if (controller.modifyUsername(usr, Cookie.user.getId())) {
			Cookie.user.setLogin(usr);
			
			Main.toUserProfileAdmin(event);
			Main.toCompMsg();
		}
		else {
			lblStat.setVisible(true);
		}
	}

}
