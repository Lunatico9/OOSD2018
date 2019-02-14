package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.LoginController;
import it.bibliotecadigitale.controller.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Login implements Initializable {
		
	@FXML
	private Label lblStatus;
	@FXML
	private Hyperlink linkReg;
	@FXML
	private TextField txtUsername;
	@FXML
	private TextField txtPassword;


	/**
	 * Procedura di inizializzazione
	 * 
	 * @param location viene usato per risolvere percorsi relativi all'oggetto root
	 * o restituisce null se la variabile non è conosciuta
     *
     * @param resources viene usato per localizzare l'oggetto root o restituisce
     * null se l'oggetto root non viene localizzato.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtUsername.requestFocus();
			}
		});		
	}
	
	/**
	 * Procedura di login
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void login(ActionEvent event) {
		String usr = txtUsername.getText();
		String psw = txtPassword.getText();
		
		LoginController lc = new LoginController();
		
		if (lc.loginProcedure(usr, psw)){
			Main.toSearchOpera(event);
		}
		else {
			lblStatus.setText("Login Failed");
		}
	}
	
	/**
	 * Indirizza alla pagina di registrazione
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void registration(ActionEvent event) {
		Main.toRegistration(event);
	}

}
