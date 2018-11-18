package it.bibliotecadigitale.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserProfileController {
		
		@FXML
		protected Label lblUser;
		@FXML
		protected Label lblNome;
		@FXML
		protected Label lblCnome;
		@FXML
		protected Label lblRole;
		@FXML
		protected Label lblLiv;
		@FXML
		protected Label lblPriv;
		@FXML
		protected TextField txtPass;
		
		
		/**
		 * Realizza view sulla base dei dati dell'utente
		 * @throws Exception
		 */
		public void defineView() {
			lblUser.setText(Cookie.user.getLogin());
			lblNome.setText(Cookie.user.getNome());
			lblCnome.setText(Cookie.user.getCognome());
			txtPass.setText(Cookie.user.getPassw());

			
			if (Cookie.user.getPriv()) {
				lblPriv.setText("Puoi effettuare il download delle opere!");
			}
			else {
				lblPriv.setText("Non puoi effettuare il download delle opere!");
			}
			
			switch (Cookie.user.getRuolo()) {
			case 'u':
				lblRole.setText("Utente");
				break;
			case 't':
				lblRole.setText("Trascrittore");
				Integer i = (Integer)Cookie.user.getLiv();
				lblLiv.setText(i.toString());
				break;
			case 's':
				lblRole.setText("Supervisore");
				break;
			case 'm':
				lblRole.setText("Manager");
				break;
			case 'a':
				lblRole.setText("Amministratore");
				break;
			default:
				Main.toErrorMsg("L'utente non ha ruolo valido");
			}
		}
		
		/**
		 * Indirizza alla pagina di modifica username
		 * @param ActionEvent event
		 */
		public void modUsername(ActionEvent event) {
			Main.toModUsername(event);
		}
		
		/**
		 * Indirizza alla pagina di modifica password
		 * @param ActionEvent event
		 */
		public void modPass(ActionEvent event) {
			Main.toModPass(event);
		}

}
