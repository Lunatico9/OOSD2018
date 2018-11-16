package it.bibliotecadigitale.controller;

import java.util.Scanner;

import it.bibliotecadigitale.dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

public class ModRoleController {

	@FXML
	private Label lblErr;
	@FXML
	private ChoiceBox<String> choiceRole;
	@FXML
	private TextField txtLiv;
	
	
	public void change(ActionEvent event) {
		String role = choiceRole.getValue();
		String liv = txtLiv.getText();
		Integer i = (Integer)Cookie.selectedUser.getLiv();
		String livello = i.toString();
		
		lblErr.setText("");
		
		System.out.println("ciao 2");

		UtenteDao db = new UtenteDao();
		
		System.out.println(liv);
		try {
		
			if (!role.equals("Trascrittore") && !liv.equals("0")) {
				lblErr.setText("Solo i Trascrittori aumentano di livello");
			}
			else if(liv.equals(livello)) {
				System.out.println("ciao 3");
				Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
				db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
				Main.toUserProfileAdmin(event);
			}
			else {
				switch (liv) {
				case "0":
					Cookie.selectedUser.setLiv(0);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
					db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
					break;
				case "1":
					Cookie.selectedUser.setLiv(1);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
					db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
					break;
				case "2":
					Cookie.selectedUser.setLiv(2);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
					db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
					break;
				case "3":
					Cookie.selectedUser.setLiv(3);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
					db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
					break;
				case "4":
					Cookie.selectedUser.setLiv(4);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					break;
				case "5":
					Cookie.selectedUser.setLiv(5);
					db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
					Cookie.selectedUser.setRuolo(role.toLowerCase().charAt(0));
					db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
					Main.toUserProfileAdmin(event);
					break;
				default: 
					lblErr.setText("Input non valido");
					break;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}
	
	
	/**
	 *Inizializza il valore dei TextField
	 */
	public void setValue() throws Exception{
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
			Exception e = new Exception();
			throw e;
		}	
		
		txtLiv.setText(((Integer)Cookie.selectedUser.getLiv()).toString());
		
		System.out.println("ciap");

	}
}
