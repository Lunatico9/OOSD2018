package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.ViewerController;
import it.bibliotecadigitale.helper.Highlighter;
import it.bibliotecadigitale.model.Pagina;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Viewer implements Initializable {
	
	@FXML
	private ImageView image;
	@FXML
	private WebView transcription;
	@FXML
	private Button btnMod;
	@FXML
	private Button btnSearch;
	@FXML
	private Button btnSearchReset;
	@FXML
	private TextField txtSearch;
	
	private Integer index; //memorizza posizione della pagina cliccata nell'array di pagine salvato sul cookie
	
	private String text_transcription; //Trascrizione originale - Utile in caso di ricerca.
	
	
	/**
	 * Carica l'immagine e la trascrizione dell'immagine selezionata
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Inizializza il bottone con nome diverso in base al ruolo dell'utente
		if (Memento.user.getRuolo() == 'a' || Memento.user.getRuolo() == 'r') {
			btnMod.setVisible(true);
			btnMod.setText("Approva");
		}
		
		ViewerController controller = new ViewerController();
		boolean b = controller.isAssigned(Memento.user.getId(), Memento.selectedOpera.getId());
		
		if (Memento.user.getRuolo() == 't' && b) {
			btnMod.setVisible(true);
			btnMod.setText("Modifica");
		}
		
		//inizializziamo l'ImageView
		Image img = new Image(Memento.selectedPage.getImmagine());
		image.setImage(img);
				
		//inizializziamo la trascrizione
		WebEngine we = transcription.getEngine();
				
		String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
		String notAppTransc = "La transcrizione è in attesa di essere approvata";
				
		//andiamo ad aggiungere alla trascrizione una funzione javascript per cercare e mettere in evidenza parole
		String transc = Memento.selectedPage.getTrascrizione();
				
		if (transc == null) {
			we.loadContent(noTransc);
			text_transcription = noTransc;
		}
		else if (!Memento.selectedPage.getApp()) { 
			we.loadContent(notAppTransc);
			text_transcription = notAppTransc;
		}
		else {
			we.loadContent(transc);
			text_transcription = transc;
		}
				
		//Al caricamento di Viewer andiamo a inizializzare index
		//Scorriamo la lista di pagine, troviamo quella cliccata e inizializziamo index alla posizione della pagina nella lista salvata
		int i = 0;
		for (Pagina p : Memento.pageList) {
			if (p.getId() == Memento.selectedPage.getId()) {
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
		// ci torna utile index inizializzato in precedenza perch� ci basta recuperare la pagina in posizione index+1 sul nostro array di pagine
		if (!(index == Memento.pageList.size()-1)) {
			
			index += 1;
			
			Memento.selectedPage = Memento.pageList.get(index);

			Image img = new Image(Memento.pageList.get(index).getImmagine());
			image.setImage(img);
			
			WebEngine we = transcription.getEngine();
			
			String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
			String notAppTransc = "La transcrizione è in attesa di essere approvata";
			String transc = Memento.selectedPage.getTrascrizione();
			
			if (transc == null) {
				we.loadContent(noTransc);
				text_transcription = noTransc;
			}
			else if (!Memento.selectedPage.getApp()) { 
				we.loadContent(notAppTransc);
				text_transcription = notAppTransc;
			}
			else {
				we.loadContent(transc);
				text_transcription = transc;
			}
		}
	}
	
	/**
	 * Torna alla pagina precedente
	 * @param ActionEvent event
	 */
	public void backward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perch� ci basta recuperare la pagina in posizione index-1 sul nostro array di pagine
		if (!(index == 0)) {
			index -= 1;
			
			Memento.selectedPage = Memento.pageList.get(index);
			
			Image img = new Image(Memento.pageList.get(index).getImmagine());
			image.setImage(img);
			
			WebEngine we = transcription.getEngine();
			
			String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
			String notAppTransc = "La transcrizione è in attesa di essere approvata";
			String transc = Memento.selectedPage.getTrascrizione();
			
			if (transc == null) {
				we.loadContent(noTransc);
				text_transcription = noTransc;
			}
			else if (!Memento.selectedPage.getApp()) { 
				we.loadContent(notAppTransc);
				text_transcription = notAppTransc;
			}
			else {
				we.loadContent(transc);
				text_transcription = transc;
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
	
	/**
	 * Cerca la parola inserita nella trascrizione e la evidenzia
	 * @param ActionEvent event
	 */
	public void search(ActionEvent event) {
		String searched = txtSearch.getText();
		
		System.out.println(searched);
		
		WebEngine we = transcription.getEngine();
		
		// Cerco la parola e la evidenzio usando un tag html
		Highlighter hl = new Highlighter(searched, text_transcription);
		
		// Carico il codice html modificato
		we.loadContent(hl.getHighlightedHtml());
		
		// Abilito il bottone di reset della ricerca
		btnSearchReset.setVisible(true);
		
	}
	
	public void searchReset(ActionEvent event) {
		WebEngine we = transcription.getEngine();
		
		// Carico il contenuto originale della trascrizione
		we.loadContent(text_transcription);
		
		// Disabilito il bottone di reset della ricerca
		btnSearchReset.setVisible(false);
		
		// Reset testo campo di ricerca
		txtSearch.clear();
	}
}
