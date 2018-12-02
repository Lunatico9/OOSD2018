package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import javafx.event.ActionEvent;

public class UserProfileAdmin extends UserProfile {
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente selezionato nella ricerca
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblUser.setText(Memento.selectedUser.getLogin());
		lblNome.setText(Memento.selectedUser.getNome());
		lblCnome.setText(Memento.selectedUser.getCognome());
		txtPass.setText(Memento.selectedUser.getPassw());
		lblMail.setText(Memento.selectedUser.getMail());
		lblTit.setText(Memento.selectedUser.getTitolo());
		lblPro.setText(Memento.selectedUser.getProfessione());

		
		if (Memento.selectedUser.getPriv()) {
			lblPriv.setText("Può effettuare il download delle opere!");
		}
		else {
			lblPriv.setText("Non può effettuare il download delle opere!");
		}
		
		switch (Memento.selectedUser.getRuolo()) {
		case 'u':
			lblRole.setText("Utente");
			break;
		case 't':
			lblRole.setText("Trascrittore");
			Integer i = (Integer)Memento.selectedUser.getLiv();
			lblLiv.setText(i.toString());
			break;
		case 'r':
			lblRole.setText("Revisore trascrizioni");
			break;
		case 's':
			lblRole.setText("Supervisore upload");
			break;
		case 'a':
			lblRole.setText("Amministratore");
			break;
		default:
			Main.toErrorMsg("L'utente non ha ruolo valido");
		}
	}
	
	/**
	 * Indirizza alla pagina di modifica username per l'admin
	 * @param ActionEvent event
	 */
	
	@Override
	public void modUsername(ActionEvent event) {
		Main.toModUsernameAdmin(event);
	}
	
	/**
	 * Indirizza alla pagina di modifica password per l'admin
	 * @param ActionEvent event
	 */
	@Override
	public void modPass(ActionEvent event) {
		Main.toModPassAdmin(event);
	}

	/**
	 * Indirizza alla pagina di modifica ruolo
	 * @param ActionEvent event
	 */
	public void modRole(ActionEvent event) {
		Main.toModRole(event);
	}

}
