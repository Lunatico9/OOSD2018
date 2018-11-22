package it.bibliotecadigitale.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.dao.PaginaDao;
import it.bibliotecadigitale.model.dao.UtenteDao;
import it.bibliotecadigitale.model.Pagina;
import it.bibliotecadigitale.model.Utente;
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

public class OperaInfoController implements Initializable{

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
		OperaDao db = new OperaDao();
		
		if (Cookie.selectedOpera.getDatazione().getAnno() > 1200) {
			Integer i = new Integer(Cookie.selectedOpera.getDatazione().getAnno());
			lblDate.setText(i.toString());
		}
		else {
			lblDate.setText(Cookie.selectedOpera.getDatazione().getDatazione());
		}
		
	
		if (Cookie.user.getPriv()) {
			btnDownload.setVisible(true);
		}
		
		if ((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 's') && !Cookie.selectedOpera.getApp()) {
			btnApp.setVisible(true);
		}
		
		if (Cookie.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
			btnDel.setVisible(true);
			btnAss.setVisible(true);
			txtSearch.setVisible(true);
		}

		//generiamo le miniature delle pagine dell'opera
		
		try {
			pages = db.getPagine(Cookie.selectedOpera.getId());
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
		}
		Cookie.pageList = pages;
		
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
		}
	}
	
	/**
	 * Permette il download dell'opera
	 * @param ActionEvent event
	 */
	public void download() {
		//Creo la directory dove salvare l'opera
		File dir = new File("C:/Bibliotecadigitale/" + Cookie.selectedOpera.getTitolo());
		if(dir.mkdirs()) {System.out.println("creato");}
		
		//Scrivo nella directory i metadati
		File opera = new File("C:/Bibliotecadigitale/" + Cookie.selectedOpera.getTitolo() + "/Metadati.txt");
		try {
			opera.createNewFile();
			
			FileWriter fw = new FileWriter(opera);
			fw.write(Cookie.selectedOpera.getTitolo() + "/n" + Cookie.selectedOpera.getAutore() + "/n" + Cookie.selectedOpera.getCategoria() + "/n" + Cookie.selectedOpera.getDatazione().getAnno());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			Main.toErrorMsg("Errore nella creazione del file");
			e.printStackTrace();
		}
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		OperaDao db= new OperaDao();
		//Estraggo le pagine dell'opera e scrivo path delle immagini e trascrizioni(se esistono)
		try {
			pages = db.getPagine(Cookie.selectedOpera.getId());
			int i = 1;
			
			for(Pagina p : pages) {
				File pagina = new File("C:/Bibliotecadigitale/" + Cookie.selectedOpera.getTitolo() + "/Pagina" + i + ".jpg");
				pagina.createNewFile();
				i++;
				
				FileChannel source = null;
				FileChannel destination = null;
				
				String path = p.getImmagine().substring(5, p.getImmagine().length());

				source = new FileInputStream(path).getChannel();
				destination = new FileOutputStream(pagina).getChannel();
				destination.transferFrom(source, 0, source.size());
				
				source.close();
				destination.close();
			}
			Main.toCompMsg();
		} catch (Exception e) {
			Main.toErrorMsg("Errore nella creazione del file");
			e.printStackTrace();
		}
	}
	
	/**
	 * Permette di approvare l'opera
	 * @param ActionEvent event
	 */
	public void appOpera(ActionEvent event) {
		Cookie.selectedOpera.setApp(true);
		
		OperaDao db = new OperaDao();
		try {
			db.approveOpera(Cookie.selectedOpera.getId());
			btnApp.setVisible(false);
			Main.toCompMsg();
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
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
		OperaDao db = new OperaDao();
		PaginaDao pd = new PaginaDao();
		
		try {
			ArrayList<Pagina> pages = new ArrayList<Pagina>();
			pages.addAll(db.getPagine(Cookie.selectedOpera.getId()));
			if (pages != null) {
				for (Pagina p : pages) {
					pd.delPagina(p.getId());
				}
			}
			
			db.delOpera(Cookie.selectedOpera.getId());
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		Cookie.selectedOpera = null;
		
		Main.toSearchOpera(event);
		Main.toCompMsg();
	}
	
	/**
	 * Implementa autocompletamento sulla barra di ricerca
	 */
	public void autoCompletion () {
		String input = txtSearch.getText();
		
		ArrayList<String> usernames = new ArrayList<String>();
		ArrayList<Utente> users = new ArrayList<Utente>();
		
		UtenteDao db = new UtenteDao();
		
		users.clear();
		usernames.clear();
		
		try {
			users = db.searchUserByLogin(input, "Trascrittore");
			
			if(!users.isEmpty()) {
				for(Utente u : users) {
					usernames.add(u.getLogin());
				}
				TextFields.bindAutoCompletion(txtSearch, usernames);
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
	}
	
	/**
	 * Assegna l'opera al trascrittore selezionato
	 */
	public void assign() {
		UtenteDao db = new UtenteDao();
		
		String input = txtSearch.getText();
		lblErr.setText("");
		
		try {
			if (input == null) {
				lblErr.setText("Inserisci il nome utente di un trascrittore");
			}
			
			else if (db.isNotRegisteredWithUsername(input)) {
				lblErr.setText("L'utente cercato non esiste");
			}
			
			else {
				Utente user = new Utente();
				user = db.getUtente(input);
				if (user.getRuolo() != 't') {
					lblErr.setText("L'utente cercato non è un trascrittore");
				}
				else {
					OperaDao od = new OperaDao();
					od.allocateOpera(user.getId(), Cookie.selectedOpera.getId());
					
					txtSearch.clear();
					
					Main.toCompMsg();
				}
				
			}
		
		}
		catch (Exception e){
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		
			
	}
	
}
