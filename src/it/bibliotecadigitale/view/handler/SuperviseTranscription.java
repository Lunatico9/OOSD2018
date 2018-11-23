package it.bibliotecadigitale.view.handler;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Cookie;
import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.SuperviseTranscriptionController;
import it.bibliotecadigitale.model.Pagina;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;

public class SuperviseTranscription implements Initializable {
	
	@FXML
	private TilePane imagePane;

	/**
	 * Inizializza le miniature delle pagine
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		
		SuperviseTranscriptionController controller = new SuperviseTranscriptionController();
		pages = controller.getPages();

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

}
