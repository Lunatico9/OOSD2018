package it.bibliotecadigitale.controller;

import java.sql.Timestamp;
import java.util.Date;

import it.bibliotecadigitale.dao.PaginaDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TranscriberController {
	
	@FXML
	Label lblStatus;
	
	@FXML
	HTMLEditor teiEditor;
	
	Timestamp init;

	/**
	 * Carica la trascrizione e inizializza la variabile init
	 */
	public void loadTranscription() {
		teiEditor.setHtmlText(Cookie.selectedPage.getTrascrizione());
		
		this.init = Cookie.selectedPage.getUltModifica();
	}
	
	/**
	 * Controlla se ci sono state modifiche sulla trascrizione nel db, se non ci sono carica la nuova trascrizione
	 * @param ActionEvent
	 */
	public void change(ActionEvent event) {
		
		lblStatus.setVisible(false);
		
		PaginaDao db = new PaginaDao();
		Timestamp mod;
		
		try {
			mod = db.getModifica(Cookie.selectedPage.getId());
			
			if (init.before(mod)) {
				synchMessage();
			}
			else {
				db.addTrascrizione(teiEditor.getHtmlText(), Cookie.selectedPage.getId());
				Date data = new Date();
				init.setTime(data.getTime());
				lblStatus.setVisible(true);
			}
		}
		
		catch (Exception e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}	
	}

	/**
	 * Genera una finestra di dialogo che avvisa l'utente che c'è file aggiornato sul db e lo 'invita' ad aggiornare
	 */
	private void synchMessage() {
			AnchorPane root = new AnchorPane();
			
			Button btn = new Button();
			btn.setText("Aggiorna");
			btn.setLayoutX(90);
			btn.setLayoutY(100);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
                public void handle(ActionEvent event) {
					update();
				}
			});
			
			Text txt = new Text("C'è una versione aggiornata del file");
			txt.setLayoutX(5.0);
			txt.setLayoutY(30.0);
			root.getChildren().addAll(btn, txt);
			
			Scene scene = new Scene(root, 250, 150);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
	}
	
	/**
	 * Compara i due testi e integra le parti aggiornate
	 */
	private void update() {
		System.out.println("Sincronizzo");
	}

}
