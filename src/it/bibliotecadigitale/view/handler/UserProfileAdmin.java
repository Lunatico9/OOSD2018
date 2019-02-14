package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import impl.jfxtras.styles.jmetro8.PasswordFieldSkin;
import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.ModUserController;
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
		lblMail.setText(Memento.selectedUser.getMail());
		lblTit.setText(Memento.selectedUser.getTitolo());
		lblPro.setText(Memento.selectedUser.getProfessione());
		
		int n = Memento.selectedUser.getPassw().length();
        StringBuilder passwordBuilder = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            passwordBuilder.append(PasswordFieldSkin.BULLET);
        }
        
        txtPass.setText(passwordBuilder.toString());

		
		if (Memento.selectedUser.getPriv()) {
			lblPriv.setText("Pu� effettuare il download delle opere!");
		}
		else {
			lblPriv.setText("Non pu� effettuare il download delle opere!");
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
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	
	@Override
	public void modUsername(ActionEvent event) {
		Main.toModUsernameAdmin(event);
	}
	
	/**
	 * Indirizza alla pagina di modifica password per l'admin
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	@Override
	public void modPass(ActionEvent event) {
		Main.toModPassAdmin(event);
	}

	/**
	 * Indirizza alla pagina di modifica ruolo
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void modRole(ActionEvent event) {
		Main.toModRole(event);
	}
	
	/**
	 * Garantisce o rimuove la possibilit� per l'utente di scaricare le opere
	 * 
	 * @param evento che provoca la chiamata del metodo
	 */
	public void modPriv(ActionEvent event) {
		ModUserController controller = new ModUserController();
		controller.modifyPriv();
		Main.toUserProfileAdmin(event);
	}
}
