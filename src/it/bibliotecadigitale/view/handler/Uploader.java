package it.bibliotecadigitale.view.handler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.bibliotecadigitale.controller.Main;
import it.bibliotecadigitale.controller.UploaderController;
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

public class Uploader implements Initializable {
	
	List<File> imageFileList;
	
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
	private Button btnImg;
	@FXML
	private TilePane container;
	
	/**
	 * Inizializza il choice box delle categorie
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//carichiamo il choicebox per le categorie
		ArrayList<String> categories = new ArrayList<String>();
		System.out.println(btnImg.getText());
		
		UploaderController controller = new UploaderController();
		controller.getCategories();
		
		choiceCat.setItems(FXCollections.observableArrayList(categories));
		
		//aggiungiamo il File Chooser al bottone btnImg
		setFileChooser(new Stage());
	}
	
	/**
	 * Permette di aggiungere una nuova opera
	 * @param ActionEvent event
	 */
	public void add(ActionEvent event) {
		lblErr.setText("");
		
		String tit = txtTit.getText();
		String aut = txtAut.getText();
		String d = txtDate.getText();
		
		String cat = choiceCat.getValue();
		
		if (tit.isEmpty() || aut.isEmpty() || d.isEmpty() || choiceCat.getValue() == null) {
			lblErr.setText("Riempi tutti i campi");
		}
		else {
			if (d.matches("^[0-9]+$")) {
				UploaderController controller = new UploaderController();
				if(controller.addOpera(tit, aut, cat, d, imageFileList)) {
					txtTit.clear();
					txtAut.clear();
					txtDate.clear();
					choiceCat.setValue("");
					
					Main.toCompMsg();
				}
				else {
					Main.toErrorMsg("Errore in connessione al Database");
				}
			}
			else {
				lblErr.setText("Solo numeri");
			}
		}
	}
	
	/**
	 * Crea il file chooser per la selezione delle immagini
	 * @param Stage stage
	 */
	public void setFileChooser(Stage stage) {
		final FileChooser fileChooser = new FileChooser();      
        fileChooser.setTitle("Aggiungi immagini");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));                 
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"), new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
	   
        btnImg.setOnAction(
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
