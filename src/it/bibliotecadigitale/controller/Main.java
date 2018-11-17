package it.bibliotecadigitale.controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
			Parent root = FXMLLoader.load(getClass().getResource("/it/bibliotecadigitale/view/Login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} 
		catch (IOException e) {
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
			 * Sono costretto a effettuare controllo sul tipo dell'evento perch� c'� una problematica su MenuItem
			 * infatti MenuItem non � nodo in JavaFX, quindi non possiamo effettuare il cast (Node) su event.getSource
			 *
			 * ho adottato una soluzione creativa che ci permette di risalire da MenuItem a MenuBar che � nodo
			 */
			
			if (event.getSource() instanceof MenuItem) {
				/* 
				 * Otteniamo MenuItem e poi Menu da getParentMenu()
				 * ho aggiunto a Menu propriet� che associa a chiave null il valore della sua MenuBar
				 * otteniamo quindi MenuBar da getProperties().get(null)
				 * possiamo finalmente applicare getScene().getWindow().hide() poich� MenuBar � nodo
				 */
		    	((MenuBar) ((MenuItem) event.getSource()).getParentMenu().getProperties().get(null)).getScene().getWindow().hide();
		    }
		    else ((Node) (event.getSource())).getScene().getWindow().hide();
			
			root = loader.load(LoginController.class.getResource("/it/bibliotecadigitale/view/Login.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.show();
		} 
		catch (IOException e) {
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
			root = loader.load(RegistrationController.class.getResource("/it/bibliotecadigitale/view/Registration.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Genera menu dinamicamente basato sul ruolo dell'utente
	 * @return MenuBar
	 * @throws Exception 
	 */
	
	public static MenuBar topMenu() throws Exception {
		Menu userMenu = new Menu("Utente");
		Menu operaMenu = new Menu("Opera");
		Menu transcMenu = new Menu("Trascrizioni");
		Menu helpMenu = new Menu("Aiuto");
		
		MenuItem profileMenu = new MenuItem("Profilo");
		profileMenu.setOnAction(e -> toUserProfile(e));
		MenuItem logoutMenu = new MenuItem("Logout");
		logoutMenu.setOnAction(e -> {Cookie.logOut(); toLogin(e);});
		MenuItem searchUserMenu = new MenuItem("Cerca utente");
		searchUserMenu.setOnAction(e -> toSearchUser(e));
		MenuItem addUserMenu = new MenuItem("Aggiungi utente");
		addUserMenu.setOnAction(e -> toAddUser(e));
		
		MenuItem searchOperaMenu = new MenuItem("Cerca opera");
		searchOperaMenu.setOnAction(e -> toSearchOpera(e));
		MenuItem uploadOperaMenu = new MenuItem("Carica opera");
		MenuItem notAppOperaMenu = new MenuItem("Non approvate");
		
		MenuItem transcFormMenu = new MenuItem("Diventa Trascrittore");
		transcFormMenu.setOnAction(e -> toTranscForm(e));
		MenuItem assignedOperaMenu = new MenuItem("Opere assegnate");
		MenuItem notAppTranscMenu = new MenuItem("Non approvate");
		
		MenuItem contactMenu = new MenuItem("Contattaci");
		MenuItem infoMenu = new MenuItem("Informazioni");
		
		switch (Cookie.user.getRuolo()) {
		case 'u':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			transcMenu.getItems().addAll(transcFormMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
            break;
		case 't':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			transcMenu.getItems().addAll(assignedOperaMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 's':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			transcMenu.getItems().addAll(assignedOperaMenu, new SeparatorMenuItem(), notAppTranscMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 'm':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu, new SeparatorMenuItem(), notAppOperaMenu);
			transcMenu.getItems().addAll(transcFormMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 'a':
			userMenu.getItems().addAll(profileMenu, logoutMenu, new SeparatorMenuItem(), addUserMenu, searchUserMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu, new SeparatorMenuItem(), notAppOperaMenu);
			transcMenu.getItems().addAll(transcFormMenu, assignedOperaMenu, new SeparatorMenuItem(), notAppTranscMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		default:
			Exception e = new Exception();
			throw e;
		}
		
		MenuBar topMenuBar = new MenuBar();
		topMenuBar.setId("topMenu");
		topMenuBar.getMenus().addAll(userMenu, operaMenu, transcMenu, helpMenu);
		
		/* 
		 * Pare che in JavaFX non sia possibile risalire da Menu a MenuBar
		 * abbiamo necessit� di farlo perch� da MenuBar possiamo risalire a Scene e chiamare hide()
		 * 
		 * il ciclo foreach serve ad aggiungere come propriet� ad ogni Menu di topMenuBar un'associazione chiave-valore
		 * che in questo caso � null-topMenuBar cos� da poter chiamare in seguito getProperties().get(null)
		 * ed ottenere topMenuBar
		 * 
		 */
		
		for (Menu m: topMenuBar.getMenus()) {
            m.getProperties().put(null, topMenuBar);
        }
		
		return topMenuBar;
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
			root = loader.load(HomeController.class.getResource("/it/bibliotecadigitale/view/Home.fxml").openStream());
	        
			root.setTop(topMenu());
		
	        HomeController hc = loader.getController();
	        hc.setName();
	        
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Home");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(UserProfileController.class.getResource("/it/bibliotecadigitale/view/UserProfile.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
	        UserProfileController upc = loader.getController();
		    upc.defineView();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo utente");
		    stage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("L'utente non ha ruolo");
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
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModUsernameController.class.getResource("/it/bibliotecadigitale/view/ModUsername.fxml").openStream());
		    
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
		
			ModUsernameController muc = loader.getController();
		    muc.setValue();
		    
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(ModPassController.class.getResource("/it/bibliotecadigitale/view/ModPass.fxml").openStream());
		   
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(AddUserController.class.getResource("/it/bibliotecadigitale/view/AddUser.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			AddUserController auc = loader.getController();
		    auc.buildChoiceBox();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Aggiungi Utente");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(TranscFormController.class.getResource("/it/bibliotecadigitale/view/TranscForm.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			

			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Diventa Trascrittore");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(SearchUserController.class.getResource("/it/bibliotecadigitale/view/SearchUser.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			SearchUserController suc = loader.getController();
		    suc.buildChoiceBox();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Utente");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(SearchOperaController.class.getResource("/it/bibliotecadigitale/view/SearchOpera.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			SearchOperaController soc = (SearchOperaController)loader.getController();
		    soc.buildChoiceBox();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Cerca Opera");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
		
		((Node) (event.getSource())).getScene().getWindow().hide();

		try {
			root = loader.load(UserProfileAdminController.class.getResource("/it/bibliotecadigitale/view/UserProfileAdmin.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			UserProfileController upc = loader.getController();
		    upc.defineView(); //dynamic binding
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo Utente");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			root = loader.load(ModUsernameAdminController.class.getResource("/it/bibliotecadigitale/view/ModUsernameAdmin.fxml").openStream());
		    
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			ModUsernameController muc = loader.getController();
		    muc.setValue();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Username");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			root = loader.load(ModPassAdminController.class.getResource("/it/bibliotecadigitale/view/ModPassAdmin.fxml").openStream());
		    
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Password");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
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
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			root = loader.load(ModRoleController.class.getResource("/it/bibliotecadigitale/view/ModRole.fxml").openStream());
		    
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			ModRoleController mrc = loader.getController();
		    mrc.setValue();
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Ruolo");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
	
	public static void toOperaInfo(ActionEvent event) {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		AnchorPane root;
		
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			root = loader.load(OperaInfoController.class.getResource("/it/bibliotecadigitale/view/OperaInfo.fxml").openStream());
			
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
	        OperaInfoController oic = loader.getController();
		    oic.defineView();
		    
		    Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Profilo utente");
		    stage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("L'utente non ha ruolo");
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
			root = loader.load(ModOperaController.class.getResource("/it/bibliotecadigitale/view/ModOpera.fxml").openStream());
		    
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			
			ModOperaController moc = loader.getController();
		    moc.setValue();
			
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Modifica Opera");
		    stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			System.out.println("L'utente non ha ruolo");
			e.printStackTrace();
		}
	}
}