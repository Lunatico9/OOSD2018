package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.model.Pagina;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewerController {
	
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
	public void load() {
		
		if (Cookie.user.getRuolo() == 't' || Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 's') {
			btnMod.setVisible(true);
			btnMod.setDisable(false);
		}
		
		Image img = new Image(Cookie.selectedPage.getImmagine());
		image.setImage(img);
		
		String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
		String notAppTransc = "La transcrizione è in attesa di essere approvata";
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
	
	public void forward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perché ci basta recuperare la pagina in posizione index+1 sul nostro array di pagine
		if (!(index == Cookie.pageList.size()-1)) {
			
			index += 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);

			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
			String notAppTransc = "La transcrizione è in attesa di essere approvata";
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
	
	public void backward(ActionEvent event) {
		// ci torna utile index inizializzato in precedenza perché ci basta recuperare la pagina in posizione index-1 sul nostro array di pagine
		if (!(index == 0)) {
			index -= 1;
			
			Cookie.selectedPage = Cookie.pageList.get(index);
			
			Image img = new Image(Cookie.pageList.get(index).getImmagine());
			image.setImage(img);
			
			String noTransc = "La trascrizione di questa pagina non è ancora disponibile";
			String notAppTransc = "La transcrizione è in attesa di essere approvata";
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
	
	public void modify(ActionEvent event) {
		Main.toTranscriber(event);
	}
}
