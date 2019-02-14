package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.OperaInfoController;
import it.bibliotecadigitale.controller.SearchUserController;
import it.bibliotecadigitale.model.Pagina;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class OperaInfo implements Initializable {

	@FXML
	private Label lblErr;
	@FXML
	private Label lblTit;
	@FXML
	private Label lblAut;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblCat;
	@FXML
	private Button btnDownload;
	@FXML
	private Button btnApp;
	@FXML
	private Button btnMod;
	@FXML
	private Button btnDel;
	@FXML
	private Button btnAss;
	@FXML
	private TextField txtSearch;
	@FXML
	private TilePane imagePane;
	
	/**
	 * Inizializza i valori dei Text Field contenenti metadati dell'opera e genera le miniature
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lblTit.setText(Memento.selectedOpera.getTitolo());
		lblAut.setText(Memento.selectedOpera.getAutore());
		lblCat.setText(Memento.selectedOpera.getCategoria());
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		
		if (Memento.selectedOpera.getDatazione().getAnno() > 1200) {
			Integer i = new Integer(Memento.selectedOpera.getDatazione().getAnno());
			lblDate.setText(i.toString());
		}
		else {
			lblDate.setText(Memento.selectedOpera.getDatazione().getDatazione());
		}
		//mostra download solo ad utenti che possono scaricare l'opera
		if (Memento.user.getPriv()) {
			btnDownload.setVisible(true);
		}
		//mostra approva solo ad utenti Amministratore e Supervisore Upload 
		if ((Memento.user.getRuolo() == 'a' || Memento.user.getRuolo() == 's') && !Memento.selectedOpera.getApp()) {
			btnApp.setVisible(true);
		}
		//mostra modifica, cancella e assegna solo a Amministratori
		if (Memento.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
			btnDel.setVisible(true);
			btnAss.setVisible(true);
			txtSearch.setVisible(true);
		}
		
		//genera le miniature
		OperaInfoController controller = new OperaInfoController();
		pages = controller.getThumbnails(Memento.selectedOpera.getId());
		
		if (!pages.isEmpty()) {
			for(Pagina p : pages) {
			    Image image = new Image(p.getImmagine());
			    ImageView iv = new ImageView(image);
				iv.setFitWidth(100);
				iv.setFitHeight(200);
		        iv.setPreserveRatio(true);
		        iv.setSmooth(true);
		        iv.setCache(true);
		        imagePane.getChildren().add(iv);
				iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
	                public void handle(MouseEvent event) {
						Memento.selectedPage = p;
						Main.toOperaInfo(event);
						Main.toViewer(event);
					}
				});
				
			}
			Memento.pageList = pages;
		}
	}
	
	/**
	 * Permette di effetturare download dell'opera
	 */
	public void download () {
		OperaInfoController controller = new OperaInfoController();
		if (controller.download(Memento.selectedOpera)) {
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore nella creazione del file");
		}
	}
	
	/**
	 * Permette di aprrovare l'opera
	 */
	public void appOpera() {
		OperaInfoController controller = new OperaInfoController();
		if (controller.approve(Memento.selectedOpera.getId())) {
			Memento.selectedOpera.setApp(true);
			btnApp.setVisible(false);
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore in connessione al Database");
		}	
	}
	
	/**
	 * Indirizza alla pagina di modifica dell'opera
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void modOpera(ActionEvent event) {
		Main.toModOpera(event);
	}

	/**
	 * Elimina l'opera dal database
	 * 
	 * @param event evento che provoca la chiamata del metodo
	 */
	public void delOpera(ActionEvent event) {
		OperaInfoController controller = new OperaInfoController();
		if (controller.delete(Memento.selectedOpera.getId())) {
			Memento.selectedOpera = null;

			Main.toSearchOpera(event);
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore in connessione al Database");
		}
	}
	
	/**
	 * Implementa autocompletamento sulla barra di ricerca
	 */
	public void autoCompletion () {
		String input = txtSearch.getText();
		
		ArrayList<String> usernames = new ArrayList<String>();
		
		SearchUserController controller = new SearchUserController();
		usernames = controller.searchUsernames(input, null);
		
		if (!usernames.isEmpty()) {
			TextFields.bindAutoCompletion(txtSearch, usernames);
		}
	}
	
	/**
	 * Permette di assegnare l'opera al trascrittore selezionato
	 */
	public void assignOpera() {
		String input = txtSearch.getText();
		lblErr.setText("");
		
		if (input == null) {
			lblErr.setText("Inserisci il nome utente di un trascrittore");
		}
		else {
			OperaInfoController controller = new OperaInfoController();
			if (controller.assign(Memento.selectedOpera.getId(), input)) {
				txtSearch.clear();
				Main.toCompMsg();
			}
			else {
				lblErr.setText("L'utente cercato non è un trascrittore");
			}
		}
	}
	
}
