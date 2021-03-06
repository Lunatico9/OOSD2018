package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ModUserController;
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
		txtUsername.setText(Memento.user.getLogin());
	}
	
	/**
	 * Modifica lo username dell'utente
	 * 
	 * @param event che provoca la chiamata del metodo
	 */
	public void update(ActionEvent event) {
		String usr = txtUsername.getText();
		
		ModUserController controller = new ModUserController();
		
		if (controller.modifyUsername(usr, Memento.user.getId())) {
			Memento.user.setLogin(usr);
			
			Main.toUserProfile(event);
			Main.toCompMsg();
		}
		else {
			lblStat.setVisible(true);
		}
	}

}
