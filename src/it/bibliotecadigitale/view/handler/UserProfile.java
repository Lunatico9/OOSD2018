package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import impl.jfxtras.styles.jmetro8.PasswordFieldSkin;
import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserProfile implements Initializable {

	@FXML
	protected Label lblUser;
	@FXML
	protected Label lblNome;
	@FXML
	protected Label lblCnome;
	@FXML
	protected Label lblMail;
	@FXML
	protected Label lblTit;
	@FXML
	protected Label lblPro;
	@FXML
	protected Label lblRole;
	@FXML
	protected Label lblLiv;
	@FXML
	protected Label lblPriv;
	@FXML
	protected TextField txtPass;
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente
	 * 
	 * @param arg0 viene usato per risolvere percorsi relativi all'oggetto root
	 * o restituisce null se la variabile non è conosciuta
     *
     * @param arg1 viene usato per localizzare l'oggetto root o restituisce
     * null se l'oggetto root non viene localizzato.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		lblUser.setText(Memento.user.getLogin());
		lblNome.setText(Memento.user.getNome());
		lblCnome.setText(Memento.user.getCognome());
		lblMail.setText(Memento.user.getMail());
		lblTit.setText(Memento.user.getTitolo());
		lblPro.setText(Memento.user.getProfessione());
		
		int n = Memento.user.getPassw().length();
        StringBuilder passwordBuilder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            passwordBuilder.append(PasswordFieldSkin.BULLET);
        }
        
		txtPass.setText(passwordBuilder.toString());

		
		if (Memento.user.getPriv()) {
			lblPriv.setText("Puoi effettuare il download delle opere!");
		}
		else {
			lblPriv.setText("Non puoi effettuare il download delle opere!");
		}
		
		switch (Memento.user.getRuolo()) {
		case 'u':
			lblRole.setText("Utente");
			break;
		case 't':
			lblRole.setText("Trascrittore");
			Integer i = (Integer)Memento.user.getLiv();
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
	 * Indirizza alla pagina di modifica username
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void modUsername(ActionEvent event) {
		Main.toModUsername(event);
	}
	
	/**
	 * Indirizza alla pagina di modifica password
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void modPass(ActionEvent event) {
		Main.toModPass(event);
	}
}
