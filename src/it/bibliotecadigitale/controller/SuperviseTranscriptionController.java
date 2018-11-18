package it.bibliotecadigitale.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.bibliotecadigitale.model.dao.PaginaDao;
import it.bibliotecadigitale.model.Pagina;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class SuperviseTranscriptionController implements Initializable {
	
	@FXML
	private TilePane imagePane;

	/**
	 * Inizializza le miniature delle pagine
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		PaginaDao db = new PaginaDao();
		
		try {
			pages = db.searchPaginaNotApproved();
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
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		
	}
}
