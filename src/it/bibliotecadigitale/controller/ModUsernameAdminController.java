package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

public class ModUsernameAdminController extends ModUsernameController implements Initializable {

	
	/**
	 * Inizializza il Text Field con lo username dell'utente selezionato
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtUsername.setText(Cookie.selectedUser.getLogin());
	}
	
	/**
	 * Modifica lo username nel Cookie e nel database
	 * @param ActionEvent event
	 */
	@Override
	public void change(ActionEvent event) {
		String usr = txtUsername.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!(usr.equals(Cookie.selectedUser.getLogin())) && !(usr.isEmpty())) {
				if(db.isNotRegisteredWithUsername(usr)) {
					Cookie.selectedUser.setLogin(usr);
					db.modifyLogin(usr, Cookie.selectedUser.getId());
					
					Main.toUserProfileAdmin(event);
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
