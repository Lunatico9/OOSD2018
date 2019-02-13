package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.TranscriberController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class Transcriber implements Initializable {
	
	@FXML
	private Button btnApp;
	@FXML
	private Button btnChange;
	@FXML
	private HTMLEditor teiEditor;
	
	private Timestamp init;


	/**
	 * Inizializza il testo nell'editor e controlla quali bottoni visualizzare
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		teiEditor.setHtmlText(Memento.selectedPage.getTrascrizione());
		
		if (Memento.selectedPage.getUltModifica() == null) {
			Date data = new Date();
			init = new Timestamp(data.getTime());
			//init.setTime(data.getTime());
		}
		else {
			this.init = Memento.selectedPage.getUltModifica();
		}
		
		if (Memento.user.getRuolo() == 'r' || Memento.user.getRuolo() == 'a') {
			btnApp.setVisible(true);
		}
		else {
			btnChange.setVisible(true);
		}
	}
	
	/**
	 * Approva la trascrizione
	 * 
	 * @param event che provoca la chiamata del metodo
	 */
	public void appTranscription(ActionEvent event) {
		TranscriberController controller = new TranscriberController();
		if(controller.approve(Memento.selectedPage.getId())) {
			Memento.selectedPage.setApp(true);
			
			Main.toOperaInfo(event);
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore in connessione al Database");
		}
	}
	
	/**
	 * Controlla se ci sono state modifiche sulla trascrizione nel db, se non ci sono carica la nuova trascrizione
	 * 
	 * @param event che provoca la chiamata del metodo
	 */
	public void change(ActionEvent event) {
		TranscriberController controller = new TranscriberController();
		Timestamp mod = controller.getModifica(Memento.selectedPage.getId());

		if (mod == null) {
			Main.toErrorMsg("Errore in connessione al Database");
		}
		if (init.before(mod)) {
			synchMessage();
		}
		else {
			if(controller.addTranscription(Memento.selectedPage.getId(), teiEditor.getHtmlText().toString())) {
				Memento.selectedPage.setTrascrizione(teiEditor.getHtmlText());
				Main.toCompMsg();
			}
			else {
				Main.toErrorMsg("Errore in connessione al Database");
			}
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
					synch();
					((Node) (event.getSource())).getScene().getWindow().hide();
				}
			});
			
			Text txt = new Text("C'è una versione aggiornata del file");
			txt.setLayoutX(5.0);
			txt.setLayoutY(30.0);
			root.getChildren().addAll(btn, txt);
			
			Scene scene = new Scene(root, 250, 150);
			Stage stage = new Stage();
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
	}

	/**
	 * Effettua la sincronizzazione del testo nella trascrizione locale con quello presente sul Database
	 */
	private void synch() {
		String localTransc = teiEditor.getHtmlText();
		
		TranscriberController controller = new TranscriberController();
		String newText = controller.update(Memento.selectedPage.getId(), localTransc);
		
		if (newText == null) {
			Main.toErrorMsg("Errore in connessione al Database");
		}
		else {
			teiEditor.setHtmlText(newText);
		}
	}

}
