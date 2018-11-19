package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.Pagina;
import it.bibliotecadigitale.model.dao.OperaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewerController implements Initializable {
	
	@FXML
	private ImageView image;
	@FXML
	private TextArea transcription;
	@FXML
	private Button btnMod;
	
	private Integer index;
	
	
	
	/**
	 * Carica l'immagine e la trascrizione dell'immagine selezionata
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		OperaDao db = new OperaDao();
		
		if (Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 's') {
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
		
		Image img = new Image(Cookie.selectedPage.getImmagine());
		image.setImage(img);
		
		String noTransc = "La trascrizione di questa pagina non Ë ancora disponibile";
		String notAppTransc = "La transcrizione Ë in attesa di essere approvata";
		String transc = Cookie.selectedPage.getTrascrizione();
		
		if (transc == null) {
			transcription.setText(noTransc);
		}
		else if (!Cookie.selectedPage.getApp()) { 
			transcription.setText(notAppTransc);
		}
		else {
			transcription.setText(transc);
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
	 * @param event
	 */
	public void forward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perch√© ci basta recuperare la pagina in posizione index+1 sul nostro array di pagine
		if (!(index == Cookie.pageList.size()-1)) {
			
			index += 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);

			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			String noTransc = "La trascrizione di questa pagina non Ë ancora disponibile";
			String notAppTransc = "La transcrizione Ë in attesa di essere approvata";
			String transc = Cookie.selectedPage.getTrascrizione();
			
			if (transc == null) {
				transcription.setText(noTransc);
			}
			else if (!Cookie.selectedPage.getApp()) { 
				transcription.setText(notAppTransc);
			}
			else {
				transcription.setText(transc);
			}
		}
	}
	
	/**
	 * Torna alla pagina precedente
	 * @param event
	 */
	public void backward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perch√© ci basta recuperare la pagina in posizione index-1 sul nostro array di pagine
		if (!(index == 0)) {
			index -= 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);
			
			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			String noTransc = "La trascrizione di questa pagina non Ë ancora disponibile";
			String notAppTransc = "La transcrizione Ë in attesa di essere approvata";
			String transc = Cookie.selectedPage.getTrascrizione();
			
			if (transc == null) {
				transcription.setText(noTransc);
			}
			else if (!Cookie.selectedPage.getApp()) { 
				transcription.setText(notAppTransc);
			}
			else {
				transcription.setText(transc);
			}
		}
	}
	
	/**
	 * Indirizza al Transcriber
	 * @param event
	 */
	public void modify(ActionEvent event) {
		Main.toTranscriber(event);
	}

}
