package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserProfileController implements Initializable{
		
		@FXML
		private Label lblUser;
		@FXML
		private Label lblNome;
		@FXML
		private Label lblCnome;
		@FXML
		private Label lblRole;
		@FXML
		private Label lblLiv;
		@FXML
		private Label lblPriv;
		@FXML
		private TextField txtPass;
		
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
		}
		
		/**
		 * Realizza view personalizzata sulla base dei dati dell'utente
		 * @throws Exception
		 */
		public void defineView() throws Exception {
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
				Exception e = new Exception();
				throw e;
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
