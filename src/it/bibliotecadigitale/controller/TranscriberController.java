package it.bibliotecadigitale.controller;

import java.sql.Timestamp;
import java.util.Date;

import it.bibliotecadigitale.model.dao.PaginaDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class TranscriberController {
	
	@FXML
	private Button btnApp;
	@FXML
	private Button btnChange;
	@FXML
	private HTMLEditor teiEditor;
	
	private Timestamp init;

	/**
	 * Carica la trascrizione e inizializza la variabile init
	 */
	public void loadTranscription() {
		teiEditor.setHtmlText(Cookie.selectedPage.getTrascrizione());
		
		this.init = Cookie.selectedPage.getUltModifica();
		
		if (Cookie.user.getRuolo() == 's' || Cookie.user.getRuolo() == 'a') {
			btnApp.setVisible(true);
		}
		else {
			btnChange.setVisible(true);
		}
	}
	
	/**
	 * Approva la trascrizione
	 */
	public void appTranscription(ActionEvent event) {
		PaginaDao db = new PaginaDao();
		
		Cookie.selectedPage.setApp(true);
		
		try {
			db.approvePagina(Cookie.selectedPage.getId());
		} 
		catch (Exception e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
		
		Main.toOperaInfo(event);
	}
	
	/**
	 * Controlla se ci sono state modifiche sulla trascrizione nel db, se non ci sono carica la nuova trascrizione
	 * @param ActionEvent
	 */
	public void change(ActionEvent event) {
		
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
	 * Compara il testo locale e il testo presente sul database, quindi inserisce in un nuovo testo tutte le righe presenti in locale e le righe modificate tra parentesi quadre []
	 */
	private void update() {
		
		PaginaDao db = new PaginaDao();
		
		String localTransc = teiEditor.getHtmlText();
		String dbTransc;
		try {
			
			dbTransc = db.getTrascrizione(Cookie.selectedPage.getId());
			
			String[] formattedLocal = localTransc.split("<br>");
			String[] formattedDb = dbTransc.split("<br>");
			
			final int localLength = formattedLocal.length;
			final int dbLength = formattedDb.length;
			
			StringBuffer newText = new StringBuffer("");
		    
			if (localLength <= dbLength) {
				//se il testo locale ha meno righe di quello scaricato dal db iteriamo sulla lunghezza di questo
				for (int i = 0; i<localLength; i++) {
			    	//se la riga in formatted local è uguale alla riga in formatted db inseriamo nel nuovo testo solamente formatted local
					if (formattedLocal[i].equals(formattedDb[i])) {
			    		newText.append(formattedLocal[i] + "<br>");
			    	}
			    	//altrimenti inseriamo la riga nel testo locale e tra parentesi quadre la stringa nel testo sul db
			    	else {
			    		newText.append(formattedLocal[i] + "<br>["  + formattedDb[i] + "]<br>");
			    	}
			    		 
			    }
				//e insieramo in coda al nuovo testo le righe del testo scaricato non preso in considerazione dal primo ciclo for
				for (int i = 0; i<(dbLength - localLength); i++) {
					newText.append("[" + formattedDb[i+localLength] + "]<br>");
				}
			}
			
			else {
				//nel caso opposto iteriamo sulla lunghezza del testo nel db
				for (int i = 0; i<dbLength; i++) {
			    	
					if (formattedLocal[i].equals(formattedDb[i])) {
			    		newText.append(formattedLocal[i] + "<br>");
			    	}
			    	
					else {
			    		newText.append(formattedLocal[i] + "<br>["  + formattedDb[i] + "]<br>");
			    	}
			    }
				// quindi andiamo ad aggiungere in coda al nuovo testo le righe del testo locale non considerate nel primo ciclo for
				for (int i = 0; i<(localLength - dbLength); i++) {
					newText.append(formattedLocal[i+dbLength] + "<br>");
				}
			}
			
			teiEditor.setHtmlText(newText.toString());
		} 
		catch (Exception e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
	}
	
}

