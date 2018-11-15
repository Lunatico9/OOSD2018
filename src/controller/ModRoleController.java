package controller;

import java.util.Scanner;

import dao.UtenteDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (choiceRole.getValue() == null) {
				lblErr.setText("Seleziona un ruolo");
			}
			else if (!role.equals("Trascrittore") && !liv.equals("0")) {
				lblErr.setText("Solo i Trascrittori aumentano di livello");
			}
			else if(liv.equals(livello)) {
				Cookie.selectedUser.setRuolo(role.charAt(0));
				db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
			}
			else {
				Cookie.selectedUser.setRuolo(role.charAt(0));
				db.modifyRuolo(Cookie.selectedUser.getRuolo(), Cookie.selectedUser.getId());
				
				Scanner in = new Scanner(liv).useDelimiter("[^0-9]+");
				int integer = in.nextInt();
				in.close();
				Cookie.selectedUser.setLiv(integer);
				db.modifyLivello(Cookie.selectedUser.getLiv(), Cookie.selectedUser.getId());
			}
			
			Main.toUserProfileAdmin(event);
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
			choiceRole.setValue("Supervisore");
			break;
		case 'm':
			choiceRole.setValue("Manager");
			break;
		case 'a':
			choiceRole.setValue("Amministratore");
			break;
		default:
			Exception e = new Exception();
			throw e;
		}	
	}
}
