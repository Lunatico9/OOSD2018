package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.Pagina;
import it.bibliotecadigitale.model.dao.OperaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ViewerController implements Initializable {
	
final KeyCombination keyComb = new KeyCodeCombination(KeyCode.F,
            KeyCombination.CONTROL_DOWN);
	
	@FXML
	private ImageView image;
	@FXML
	private WebView transcription;
	@FXML
	private Button btnMod;
	@FXML
	private Button btnSearch;
	@FXML
	private TextField txtSearch;
	
	private Integer index; //memorizza posizione della pagina cliccata nell'array di pagine salvato sul cookie
	
	
	
	/**
	 * Carica l'immagine e la trascrizione dell'immagine selezionata
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		OperaDao db = new OperaDao();
		
		//Inizializza il bottone con nome diverso in base al ruolo dell'utente
		if (Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 'r') {
			btnMod.setVisible(true);
			btnMod.setText("Approva");
		}
		try {
			if (Cookie.user.getRuolo() == 't' && db.isAssigned(Cookie.user.getId(), Cookie.selectedOpera.getId())) {
				btnMod.setVisible(true);
				btnMod.setText("Modifica");
			}
		} catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		
		//inizializziamo l'ImageView
		Image img = new Image(Cookie.selectedPage.getImmagine());
		image.setImage(img);
		
		//inizializziamo la trascrizione
		WebEngine we = transcription.getEngine();
		
		String noTransc = "La trascrizione di questa pagina non é ancora disponibile";
		String notAppTransc = "La transcrizione é in attesa di essere approvata";
		String transc = Cookie.selectedPage.getTrascrizione();
		
		if (transc == null) {
			we.loadContent(noTransc);
		}
		else if (!Cookie.selectedPage.getApp()) { 
			we.loadContent(notAppTransc);
		}
		else {
			we.loadContent(transc);
		}
		
		//Al caricamento di Viewer andiamo a inizializzare index
		//Scorriamo la lista di pagine, troviamo quella cliccata e inizializziamo index alla posizione della pagina nella lista salvata
		int i = 0;
		for (Pagina p : Cookie.pageList) {
			if (p.getId() == Cookie.selectedPage.getId()) {
				this.index = new Integer(i);
				break;
			}
			i++;
		}
	}
	
	/**
	 * Passa alla pagina successiva
	 * @param AcionEvent event
	 */
	public void forward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perché ci basta recuperare la pagina in posizione index+1 sul nostro array di pagine
		if (!(index == Cookie.pageList.size()-1)) {
			
			index += 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);

			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			WebEngine we = transcription.getEngine();
			
			String noTransc = "La trascrizione di questa pagina non é ancora disponibile";
			String notAppTransc = "La transcrizione é in attesa di essere approvata";
			String transc = Cookie.selectedPage.getTrascrizione();
			
			if (transc == null) {
				we.loadContent(noTransc);
			}
			else if (!Cookie.selectedPage.getApp()) { 
				we.loadContent(notAppTransc);
			}
			else {
				we.loadContent(transc);
			}
		}
	}
	
	/**
	 * Torna alla pagina precedente
	 * @param ActionEvent event
	 */
	public void backward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perché ci basta recuperare la pagina in posizione index-1 sul nostro array di pagine
		if (!(index == 0)) {
			index -= 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);
			
			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			WebEngine we = transcription.getEngine();
			
			String noTransc = "La trascrizione di questa pagina non é ancora disponibile";
			String notAppTransc = "La transcrizione é in attesa di essere approvata";
			String transc = Cookie.selectedPage.getTrascrizione();
			
			if (transc == null) {
				we.loadContent(noTransc);
			}
			else if (!Cookie.selectedPage.getApp()) { 
				we.loadContent(notAppTransc);
			}
			else {
				we.loadContent(transc);
			}
		}
	}
	
	/**
	 * Indirizza al Transcriber
	 * @param ActionEvent event
	 */
	public void modify(ActionEvent event) {
		Main.toTranscriber(event);
	}
	
	/*
	 * Cerca la parola inserita nella trascrizione e la evidenzia
	 * @param ActionEvent event
	 * 
	public void search(ActionEvent event) {
		String searched = txtSearch.getText();
		
		System.out.println(searched);
		
		WebEngine we = transcription.getEngine();
		we.setJavaScriptEnabled(true);
		we.executeScript("highlight(text)");
	}
	 */

}
