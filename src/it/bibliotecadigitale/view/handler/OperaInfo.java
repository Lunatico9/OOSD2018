package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.controller.Cookie;
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
		lblTit.setText(Cookie.selectedOpera.getTitolo());
		lblAut.setText(Cookie.selectedOpera.getAutore());
		lblCat.setText(Cookie.selectedOpera.getCategoria());
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		
		if (Cookie.selectedOpera.getDatazione().getAnno() > 1200) {
			Integer i = new Integer(Cookie.selectedOpera.getDatazione().getAnno());
			lblDate.setText(i.toString());
		}
		else {
			lblDate.setText(Cookie.selectedOpera.getDatazione().getDatazione());
		}
		//mostra download solo ad utenti che possono scaricare l'opera
		if (Cookie.user.getPriv()) {
			btnDownload.setVisible(true);
		}
		//mostra approva solo ad utenti Amministratore e Supervisore Upload 
		if ((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 's') && !Cookie.selectedOpera.getApp()) {
			btnApp.setVisible(true);
		}
		//mostra modifica, cancella e assegna solo a Amministratori
		if (Cookie.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
			btnDel.setVisible(true);
			btnAss.setVisible(true);
			txtSearch.setVisible(true);
		}
		
		//genera le miniature
		OperaInfoController controller = new OperaInfoController();
		pages = controller.getThumbnails(Cookie.selectedOpera.getId());
		
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
						Cookie.selectedPage = p;
						Main.toViewer(event);
					}
				});
				
			}
			Cookie.pageList = pages;
		}
	}
	
	/**
	 * Permette di effetturare download dell'opera
	 */
	public void download () {
		OperaInfoController controller = new OperaInfoController();
		if (controller.download(Cookie.selectedOpera)) {
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore nella creazione del file");
		}
	}
	
	/**
	 * Permette di prrovare l'opera
	 */
	public void appOpera() {
		OperaInfoController controller = new OperaInfoController();
		if (controller.approve(Cookie.selectedOpera.getId())) {
			Cookie.selectedOpera.setApp(true);
			btnApp.setVisible(false);
			Main.toCompMsg();
		}
		else {
			Main.toErrorMsg("Errore in connessione al Database");
		}	
	}
	
	/**
	 * Indirizza alla pagina di modifica dell'opera
	 * @param ActionEvent event
	 */
	public void modOpera(ActionEvent event) {
		Main.toModOpera(event);
	}

	/**
	 * Elimina l'opera dal database
	 * @param ActionEvent event
	 */
	public void delOpera(ActionEvent event) {
		OperaInfoController controller = new OperaInfoController();
		if (controller.delete(Cookie.selectedOpera.getId())) {
			Cookie.selectedOpera = null;

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
			if (controller.assign(Cookie.selectedOpera.getId(), input)) {
				txtSearch.clear();
				Main.toCompMsg();
			}
			else {
				lblErr.setText("L'utente cercato non � un trascrittore");
			}
		}
	}
	
}