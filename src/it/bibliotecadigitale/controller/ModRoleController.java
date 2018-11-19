package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModRoleController implements Initializable{

	@FXML
	private Label lblErr;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private TextField txtLiv;
	
	/**
	 * Inizializza il choice box dei ruoli e il text field del livello
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceRole.setItems(FXCollections.observableArrayList("Utente", "Trascrittore", "Supervisore trascrizioni", "Manager upload", "Amministratore"));

		switch (Cookie.selectedUser.getRuolo()) {
		case 'u':
			choiceRole.setValue("Utente");
			break;
		case 't':
			choiceRole.setValue("Trascrittore");
			Integer i = (Integer)Cookie.selectedUser.getLiv();
			txtLiv.setText(i.toString());
			break;
		case 's':
			choiceRole.setValue("Supervisore trascrizioni");
			break;
		case 'm':
			choiceRole.setValue("Manager upload");
			break;
		case 'a':
			choiceRole.setValue("Amministratore");
			break;
		default:
			Main.toErrorMsg("L'utente non ha ruolo valido");
		}	
		
		txtLiv.setText(((Integer)Cookie.selectedUser.getLiv()).toString());
	}
	
	/**
	 * Effettua il cambiamento del ruolo
	 * @param event
	 */
	public void change(ActionEvent event) {
		String role = choiceRole.getValue();
		String liv = txtLiv.getText();
		Integer i = (Integer)Cookie.selectedUser.getLiv();
		String livello = i.toString();
		
		lblErr.setText("");

		UtenteDao db = new UtenteDao();
		
		System.out.println(liv);
		
		if (!role.equals("Trascrittore") && !liv.equals("0")) {
			lblErr.setText("Solo i Trascrittori aumentano di livello");
		}
		else if(liv.equals(livello)) {
			Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
			try {
				db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
			} catch (Exception e) {
				Main.toErrorMsg("Errore in connessione al Database");
				e.printStackTrace();
			}
			Main.toUserProfileAdmin(event);
			Main.toCompMsg();
		}
		else {
			switch (liv) {
			case "0":
				doubleChange(0);
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			case "1":
				doubleChange(1);
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			case "2":
				doubleChange(2);
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			case "3":
				doubleChange(3);
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			case "4":
				doubleChange(4);

				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			case "5":
				doubleChange(5);
				
				Main.toUserProfileAdmin(event);
				Main.toCompMsg();
				break;
			default: 
				lblErr.setText("Input non valido");
				break;
			}
		}
		
			
	}
	
	private void doubleChange(int liv) {
		UtenteDao db = new UtenteDao();
		
		try {
			Cookie.selectedUser.setLiv(liv);
			db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
		    Cookie.selectedUser.setRuolo(choiceRole.getValue().toLowerCase().charAt(0));
		    db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
}
