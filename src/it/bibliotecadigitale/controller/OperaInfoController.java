package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.dao.OperaDao;
import it.bibliotecadigitale.model.Pagina;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class OperaInfoController {

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
	private TilePane imagePane;
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente
	 * @throws Exception
	 */
	public void defineView() throws Exception {
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
		System.out.println(Cookie.selectedOpera.getApp());
		System.out.println((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 'm') && !Cookie.selectedOpera.getApp());
		if ((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 'm') && !Cookie.selectedOpera.getApp()) {
			System.out.println("bello");
			btnApp.setVisible(true);
		}
		
		if (Cookie.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
		}

		//generiamo le miniature delle pagine dell'opera su j colonne e k righe
		
		pages = db.getPagine(Cookie.selectedOpera.getId());
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
		//Non abbiamo definito la procedura di download
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
		} 
		catch (Exception e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Indirizza alla pagina di modifica opera
	 * @param ActionEvent event
	 */
	public void modOpera(ActionEvent event) {
		Main.toModOpera(event);
	}
	
}
