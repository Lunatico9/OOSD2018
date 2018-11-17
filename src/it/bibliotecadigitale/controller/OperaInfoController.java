package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.dao.OperaDao;
import it.bibliotecadigitale.dao.PaginaDao;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class OperaInfoController {
	
	//numero di immagini che andiamo a visualizzare in una riga
	private final int COLUMNS = 6;

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
	private GridPane imagePane;
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente
	 * @throws Exception
	 */
	public void defineView() throws Exception {
		lblTit.setText(Cookie.selectedOpera.getTitolo());
		lblAut.setText(Cookie.selectedOpera.getAutore());
		lblCat.setText(Cookie.selectedOpera.getCategoria());
		
		ArrayList<String> images = new ArrayList<String>();
		PaginaDao db = new PaginaDao();
		
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
		
		if ((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 'm') && !Cookie.selectedOpera.getApp()) {
			btnApp.setVisible(true);
		}
		
		if (Cookie.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
		}

		//generiamo le miniature delle pagine dell'opera su j colonne e k righe
		
		images = db.getImageCollection(Cookie.selectedOpera.getId());
		
		int k = 0; int j = 0;
		if (!images.isEmpty()) {
			for(String i : images) {
				
			    Image image = new Image(i);
			    ImageView iv = new ImageView(image);
			    iv.setImage(image);
				iv.setFitWidth(100);
				iv.setFitHeight(200);
		        iv.setPreserveRatio(true);
		        iv.setSmooth(true);
		        iv.setCache(true);
				iv.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
	                public void handle(MouseEvent event) {
						System.out.println("clicked");
						//Main.toViewer(event);
					}
				});
				
				if (j < COLUMNS) {
					imagePane.add(iv, j++, k);
				}
				else {
					imagePane.add(iv, j++, k++);
				}
				
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
