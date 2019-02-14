package it.bibliotecadigitale.controller;

import java.io.IOException;

import com.sun.javafx.css.StyleManager;
import it.bibliotecadigitale.view.handler.Contact;
import it.bibliotecadigitale.view.handler.Login;
import it.bibliotecadigitale.view.handler.ModifyPasswordAdmin;
import it.bibliotecadigitale.view.handler.ModifyRole;
import it.bibliotecadigitale.view.handler.ModifyUsernameAdmin;
import it.bibliotecadigitale.view.handler.OperaInfo;
import it.bibliotecadigitale.view.handler.Registration;
import it.bibliotecadigitale.view.handler.TopMenuBar;
import it.bibliotecadigitale.view.handler.Transcriber;
import it.bibliotecadigitale.view.handler.Uploader;
import it.bibliotecadigitale.view.handler.UserProfile;
import it.bibliotecadigitale.view.handler.UserProfileAdmin;
import it.bibliotecadigitale.view.handler.Viewer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;


public class Main extends Application {
	
	private final static String ERRMSG = "L'utente non ha ruolo";
	private final static String IOERRMSG = "Errore nel caricamento della pagina";
	
	/**
	 * Esegue la pagina iniziale della view
	 * 
	 * @param stage su cui viene costruita la view di partenza
	 */
	@Override	
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/it/bibliotecadigitale/view/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
			StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("/it/bibliotecadigitale/resources/light.css").toString());
			stage.setScene(scene);
			stage.setTitle("Login");
			stage.setResizable(false);
			stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di login
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toLogin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		
		try {
			
			/* 
			 * Procedura per nascondere pagina di arrivo dell'evento
			 * 
			 * Sono costretto a effettuare controllo sul tipo dell'evento perché c'è una problematica su MenuItem
			 * infatti MenuItem non è nodo in JavaFX, quindi non possiamo effettuare il cast (Node) su event.getSource
			 *
			 * ho adottato una soluzione creativa che ci permette di risalire da MenuItem a MenuBar che è nodo
			 */
			
			if (event.getSource() instanceof MenuItem) {
				/* 
				 * Otteniamo MenuItem e poi Menu da getParentMenu()
				 * ho aggiunto a Menu proprietà  che associa a chiave null il valore della sua MenuBar
				 * otteniamo quindi MenuBar da getProperties().get(null)
				 * possiamo finalmente applicare getScene().getWindow().hide() poichè MenuBar è nodo
				 */
		    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
		    }
		    else ((Node) (event.getSource())).getScene().getWindow().hide();
			
			root = loader.load(Login.class.getResource("/it/bibliotecadigitale/view/fxml/Login.fxml").openStream());
	        
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.setTitle("Login");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di registrazione
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toRegistration(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		
	    ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(Registration.class.getResource("/it/bibliotecadigitale/view/fxml/Registration.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo dei dati dell'utente
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toUserProfile(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(UserProfile.class.getResource("/it/bibliotecadigitale/view/fxml/UserProfile.fxml").openStream());
		    
			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo utente");
		    stage.show();
		}
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica dello username
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModUsername(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModUserController.class.getResource("/it/bibliotecadigitale/view/fxml/ModUsername.fxml").openStream());
		    
			createMenu(root);
		    
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica della password
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModPass(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModPassController.class.getResource("/it/bibliotecadigitale/view/fxml/ModPass.fxml").openStream());
		   
			createMenu(root);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina per aggiungere utenti al database, solo per Amministratori
	 * 
	 *  @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toAddUser(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(AddUserController.class.getResource("/it/bibliotecadigitale/view/fxml/AddUser.fxml").openStream());
			
			createMenu(root);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Aggiungi Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica modulo per richiedere di diventare trascrittore
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toTranscForm(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(TranscFormController.class.getResource("/it/bibliotecadigitale/view/fxml/TranscForm.fxml").openStream());
			
			createMenu(root);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Diventa Trascrittore");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica pagina di ricerca utente
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toSearchUser(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(SearchUserController.class.getResource("/it/bibliotecadigitale/view/fxml/SearchUser.fxml").openStream());
			
			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica pagina di ricerca opere
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toSearchOpera(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(SearchOperaController.class.getResource("/it/bibliotecadigitale/view/fxml/SearchOpera.fxml").openStream());

			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo dei dati dell'utente per l'admin
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toUserProfileAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(UserProfileAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/UserProfileAdmin.fxml").openStream());
			
			createMenu(root);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo dei dati dell'utente per l'admin, overload nel caso in cui il 
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toUserProfileAdmin(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(UserProfileAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/UserProfileAdmin.fxml").openStream());
			
			createMenu(root);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica dello username per l'admin
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModUsernameAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModifyUsernameAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/ModUsernameAdmin.fxml").openStream());
		    
			createMenu(root);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica della password di un utente per l'admin
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModPassAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModifyPasswordAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/ModPassAdmin.fxml").openStream());
		    
			createMenu(root);
			
			Scene scene = new Scene(root);
			stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica del ruolo
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModRole(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModifyRole.class.getResource("/it/bibliotecadigitale/view/fxml/ModRole.fxml").openStream());
			
			createMenu(root);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Ruolo");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo informazioni di un'opera
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toOperaInfo(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(OperaInfo.class.getResource("/it/bibliotecadigitale/view/fxml/OperaInfo.fxml").openStream());

			createMenu(root);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Info Opera");
		    stage.setResizable(false);
		    stage.show();
		}
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo informazioni di un'opera, overload necessario a gestire chiamata con parametro MouseEvent
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toOperaInfo(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(OperaInfo.class.getResource("/it/bibliotecadigitale/view/fxml/OperaInfo.fxml").openStream());
			
			createMenu(root);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Info Opera");
		    stage.setResizable(false);
		    stage.show();
		}
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica informazioni  di un'opera
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toModOpera(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModOperaController.class.getResource("/it/bibliotecadigitale/view/fxml/ModOpera.fxml").openStream());

			createMenu(root);
	        
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica il viewer
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toViewer(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(Viewer.class.getResource("/it/bibliotecadigitale/view/fxml/Viewer.fxml").openStream());
	        
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Viewer");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica il Transcrber
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toTranscriber(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(Transcriber.class.getResource("/it/bibliotecadigitale/view/fxml/Transcriber.fxml").openStream());
	        
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Transcriber");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica il Transcrber, overload necessario per gestire MouseEvent
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toTranscriber(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(Transcriber.class.getResource("/it/bibliotecadigitale/view/fxml/Transcriber.fxml").openStream());
	        
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Transcriber");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di upload di un'opera
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toUploadOpera(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(Uploader.class.getResource("/it/bibliotecadigitale/view/fxml/Uploader.fxml").openStream());

			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Carica Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di supervisione alle trascrizioni non ancora approvate
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toSuperviseTranscription(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(SuperviseTranscriptionController.class.getResource("/it/bibliotecadigitale/view/fxml/SuperviseTranscription.fxml").openStream());

			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Trascrizioni non approvate");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina per contattare un amministratore
	 * 
	 * @param event evento che causa l'inidirizzamento alla pagina
	 */
	public static void toContactUs(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		if (event.getSource() instanceof MenuItem) {
	    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
	    }
	    else ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(Contact.class.getResource("/it/bibliotecadigitale/view/fxml/Contact.fxml").openStream());

			createMenu(root);
	        
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.setTitle("Contattaci");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg(IOERRMSG);
			e.printStackTrace();
		}
	}

	/**
	 * Genera finestra di dialogo per errori critici
	 * 
	 * @param string messaggio di errore da mostrare
	 */
	public static void toErrorMsg(String string) {
		AnchorPane root = new AnchorPane();
		
		Text txt1 = new Text("ERRORE");
		txt1.setLayoutX(90.0);
		txt1.setLayoutY(60.0);
		txt1.setFont(Font.font ("Verdana", 22));
		txt1.setFill(Color.RED);
		
		Text txt2 = new Text(string);
		txt2.setLayoutX(35.0);
		txt2.setLayoutY(90.0);
		txt2.setFont(Font.font (14));
	
		root.getChildren().addAll(txt1, txt2);
		
		Scene scene = new Scene(root, 300, 150);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Genera finestra di dialogo per operazioni riuscite
	 * 
	 * @param string messaggio di successo da mostrare
	 */
	public static void toCompMsg() {
		AnchorPane root = new AnchorPane();
		
		Text txt = new Text("OPERAZIONE COMPLETATA!");
		txt.setLayoutX(20.0);
		txt.setLayoutY(70.0);
		txt.setFont(Font.font (18));
		txt.setFill(Color.BLUE);
	
		root.getChildren().addAll(txt);
		
		Scene scene = new Scene(root, 300, 150);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Procedura di creazione e ancoraggio del top menu
	 * 
	 * @param root componente su cui mostrare il menu
	 */
	private static void createMenu(AnchorPane root) {
		TopMenuBar tmb;
		try {
			tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		} 
		catch (Exception e) {
			Main.toErrorMsg(ERRMSG);
			e.printStackTrace();
		}
	}
	
}
