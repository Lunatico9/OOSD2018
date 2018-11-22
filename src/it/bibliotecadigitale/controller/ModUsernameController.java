package it.bibliotecadigitale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.UtenteDao;

public class ModUsernameController implements Initializable {
	
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
	 * Modifica lo username nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public void change(ActionEvent event) {
		String usr = txtUsername.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!(usr.equals(Cookie.user.getLogin())) && !(usr.isEmpty())) {
				if(db.isNotRegisteredWithUsername(usr)) {
					Cookie.user.setLogin(usr);
					db.modifyLogin(usr, Cookie.user.getId());
					
					Main.toUserProfile(event);
					Main.toCompMsg();
				}
				else lblStat.setVisible(true);
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
}
