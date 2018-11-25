package it.bibliotecadigitale.controller;

import java.io.IOException;

import it.bibliotecadigitale.view.handler.Home;
import it.bibliotecadigitale.view.handler.ModifyPasswordAdmin;
import it.bibliotecadigitale.view.handler.ModifyUsernameAdmin;
import it.bibliotecadigitale.view.handler.TopMenuBar;
import it.bibliotecadigitale.view.handler.Uploader;
import it.bibliotecadigitale.view.handler.UserProfile;
import it.bibliotecadigitale.view.handler.UserProfileAdmin;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;


public class Main extends Application {
	
	/**
	 * Esegue la pagina iniziale della view
	 * @param Stage stage
	 */
	@Override	
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/it/bibliotecadigitale/view/fxml/Login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Login");
			stage.setResizable(false);
			stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di login
	 * @param ActionEvent event
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
				 * ho aggiunto a Menu proprietà che associa a chiave null il valore della sua MenuBar
				 * otteniamo quindi MenuBar da getProperties().get(null)
				 * possiamo finalmente applicare getScene().getWindow().hide() poichè MenuBar è nodo
				 */
		    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
		    }
		    else ((Node) (event.getSource())).getScene().getWindow().hide();
			
			root = loader.load(LoginController.class.getResource("/it/bibliotecadigitale/view/fxml/Login.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.setTitle("Login");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di registrazione
	 * @param ActionEvent event
	 */
	public static void toRegistration(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Parent root;
		
	    ((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(RegistrationController.class.getResource("/it/bibliotecadigitale/view/fxml/Registration.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Carica la home
	 * @param ActionEvent event
	 */
	public static void toHome(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		BorderPane root;
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(Home.class.getResource("/it/bibliotecadigitale/view/fxml/Home.fxml").openStream());
	        
			TopMenuBar tmb = new TopMenuBar();
			root.setTop(tmb.getMenu());
	        
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Home");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo dei dati dell'utente
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo utente");
		    stage.show();
		}
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica dello username
	 * @param ActionEvent event
	 */
	
	public static void toModUsername(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModUsernameController.class.getResource("/it/bibliotecadigitale/view/fxml/ModUsername.fxml").openStream());
		    
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica della password
	 * @param ActionEvent event
	 */
	
	public static void toModPass(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModPassController.class.getResource("/it/bibliotecadigitale/view/fxml/ModPass.fxml").openStream());
		   
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina per aggiungere utenti al database (solo Amministratori)
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Aggiungi Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica modulo per richiedere di diventare trascrittore
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);

			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Diventa Trascrittore");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica pagina di ricerca utente
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica pagina di ricerca opere
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di riepilogo dei dati dell'utente per l'admin
	 * @param ActionEvent event
	 */
	
	public static void toUserProfileAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;

		try {
			root = loader.load(UserProfileAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/UserProfileAdmin.fxml").openStream());
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo Utente");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica dello username per l'admin
	 * @param ActionEvent event
	 */
	
	public static void toModUsernameAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModifyUsernameAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/ModUsernameAdmin.fxml").openStream());
		    
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica della password per l'admin
	 * @param ActionEvent event
	 */
	
	public static void toModPassAdmin(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModifyPasswordAdmin.class.getResource("/it/bibliotecadigitale/view/fxml/ModPassAdmin.fxml").openStream());
		    
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica della password per l'admin
	 * @param ActionEvent event
	 */
	
	public static void toModRole(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ModRoleController.class.getResource("/it/bibliotecadigitale/view/fxml/ModRole.fxml").openStream());
		    
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Ruolo");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	public static void toOperaInfo(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(OperaInfoController.class.getResource("/it/bibliotecadigitale/view/fxml/OperaInfo.fxml").openStream());
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Info Opera");
		    stage.setResizable(false);
		    stage.show();
		}
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di modifica informazioni  di un'opera
	 * @param ActionEvent event
	 */
	
	public static void toModOpera(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModOperaController.class.getResource("/it/bibliotecadigitale/view/fxml/ModOpera.fxml").openStream());
		    
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica il viewer
	 * @param MouseEvent event
	 */
	public static void toViewer(MouseEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		try {
			root = loader.load(ViewerController.class.getResource("/it/bibliotecadigitale/view/fxml/Viewer.fxml").openStream());
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Viewer");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica il Transcrber
	 * @param ActionEvent event
	 */
	public static void toTranscriber(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(TranscriberController.class.getResource("/it/bibliotecadigitale/view/fxml/Transcriber.fxml").openStream());
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Transcriber");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di upload di un'opera
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Carica Opera");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina di supervisione alle trascrizioni non ancora approvate
	 * @param ActionEvent event
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
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);

		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Trascrizioni non approvate");
		    stage.setResizable(false);
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	/**
	 * Carica la pagina per contattare un amministratore
	 * @param ActionEvent event
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
			root = loader.load(ContactController.class.getResource("/it/bibliotecadigitale/view/fxml/Contact.fxml").openStream());
			
			TopMenuBar tmb = new TopMenuBar();
			MenuBar topMenu = tmb.getMenu();
			AnchorPane.setLeftAnchor(topMenu, 0.0);
			AnchorPane.setRightAnchor(topMenu, 0.0);
			root.getChildren().add(topMenu);
			
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setResizable(false);
		    stage.setTitle("Contattaci");
		    stage.show();
		} 
		catch (IOException e) {
			Main.toErrorMsg("Errore nel caricamento della pagina");
			e.printStackTrace();
		}
		catch (Exception e) {
			Main.toErrorMsg("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}

	/**
	 * Genera finestra di dialogo per errori critici
	 * @param String string
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
		
		Scene scene = new Scene(root, 250, 150);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * Genera finestra di dialogo per operazioni riuscite
	 * @param String string
	 */
	public static void toCompMsg() {
		AnchorPane root = new AnchorPane();
		
		Text txt = new Text("OPERAZIONE COMPLETATA!");
		txt.setLayoutX(20.0);
		txt.setLayoutY(70.0);
		txt.setFont(Font.font (18));
		txt.setFill(Color.BLUE);
	
		root.getChildren().addAll(txt);
		
		Scene scene = new Scene(root, 250, 150);
		Stage stage = new Stage();
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
}
