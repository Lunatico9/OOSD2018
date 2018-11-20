package it.bibliotecadigitale.controller;

import javafx.event.ActionEvent;

public class UserProfileAdminController extends UserProfileController {
	
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente selezionato nella ricerca
	 * @throws Exception
	 */
	@Override
	public void defineView() {
		lblUser.setText(Cookie.selectedUser.getLogin());
		lblNome.setText(Cookie.selectedUser.getNome());
		lblCnome.setText(Cookie.selectedUser.getCognome());
		txtPass.setText(Cookie.selectedUser.getPassw());
		lblMail.setText(Cookie.selectedUser.getMail());
		lblTit.setText(Cookie.selectedUser.getTitolo());
		lblPro.setText(Cookie.selectedUser.getProfessione());

		
		if (Cookie.selectedUser.getPriv()) {
			lblPriv.setText("Può effettuare il download delle opere!");
		}
		else {
			lblPriv.setText("Non può effettuare il download delle opere!");
		}
		
		switch (Cookie.selectedUser.getRuolo()) {
		case 'u':
			lblRole.setText("Utente");
			break;
		case 't':
			lblRole.setText("Trascrittore");
			Integer i = (Integer)Cookie.selectedUser.getLiv();
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
