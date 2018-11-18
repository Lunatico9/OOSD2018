package it.bibliotecadigitale.controller;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.dao.PaginaDao;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class UploaderController implements Initializable {
	
	@FXML
	private Label lblErr;
	@FXML
	private ChoiceBox<String> choiceCat;
	@FXML
	private TextField txtTit;
	@FXML
	private TextField txtAut;
	@FXML
	private TextField txtDate;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnImages;
	@FXML
	private TilePane container;
	
	List<File> imageFileList;
	
	/**
	 * Inizializza il choice box delle categorie
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//carichiamo il choicebox per le categorie
		ArrayList<String> categories = new ArrayList<String>();
		
		OperaDao db = new OperaDao();
		
		try {
			categories = db.getCategorie();
		} 
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
		
		choiceCat.setItems(FXCollections.observableArrayList(categories));
	}
	
	/**
	 * Inizializza valori di choice box
	 */
	public void addOpera(ActionEvent event) {
		PaginaDao pd = new PaginaDao();
		OperaDao db = new OperaDao();
		
		lblErr.setText("");
		
		String tit = txtTit.getText();
		String aut = txtAut.getText();
		String d = txtDate.getText();
		int year;
		
		String cat = choiceCat.getValue();
		
		if (tit.isEmpty() || aut.isEmpty() || d.isEmpty() || choiceCat.getValue() == null) {
			lblErr.setText("Riempi tutti i campi");
		}
		else {
			if (d.matches("^[0-9]+$")) {
	    		//utilizziamo Scanner con espressione regolare per estrarre interi da String
	    		Scanner in = new Scanner(txtDate.getText());
		    	in.useDelimiter("[^0-9]+");
	    		year = in.nextInt();
	    		in.close();

				try {
					db.addOpera(tit, aut, year);
					int id = db.getOpera(tit, aut, year);
					db.addCategoriaToOpera(id, cat);
					
					int i = 0;
					for (File file : imageFileList) {
						pd.addPagina(id, i, file.toURI().toString());
						i++;
					}
				}
				catch (Exception e) {
					Main.toErrorMsg("Errore in connessione al Database");
					e.printStackTrace();
				}
			}
			
			else {
				lblErr.setText("Solo numeri");
			}
		}
	}
	
	public void setFileChooser(Stage stage) {
		final FileChooser fileChooser = new FileChooser();      
        fileChooser.setTitle("Aggiungi immagini");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
	    
        btnImages.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                    	container.getChildren().clear();
                    	
                        imageFileList = fileChooser.showOpenMultipleDialog(stage);
                        if (imageFileList != null) {
                        	
                            for (File file : imageFileList) {
                            	System.out.println(file.toURI().toString());
                            	Image image = new Image(file.toURI().toString());
                			    ImageView iv = new ImageView(image);
                				iv.setFitWidth(100);
                				iv.setFitHeight(200);
                		        iv.setPreserveRatio(true);
                		        iv.setSmooth(true);
                		        iv.setCache(true);
                		        
                		        container.getChildren().add(iv);
                            }
                        }
                    }
                 });
	}

}
