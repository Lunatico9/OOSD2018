package controller;

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
			Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
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
			 * Sono costretto a effettuare controllo sul tipo dell'evento perchè c'è problematica su MenuItem
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
			
			root = loader.load(LoginController.class.getResource("/view/Login.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.show();
		} catch (IOException e) {
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
			root = loader.load(RegistrationController.class.getResource("/view/Registration.fxml").openStream());
	        Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.setTitle("Registrazione");
		    stage.show();
		} catch (IOException e) {
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
		Menu trascMenu = new Menu("Trascrizioni");
		Menu helpMenu = new Menu("Aiuto");
		
		MenuItem profileMenu = new MenuItem("Profilo");
		profileMenu.setOnAction(e -> toUserProfile(e));
		MenuItem modDataMenu = new MenuItem("Modifica dati");
		MenuItem logoutMenu = new MenuItem("Logout");
		logoutMenu.setOnAction(e -> {Cookie.destroy(); toLogin(e);});
		MenuItem searchUserMenu = new MenuItem("Cerca utente");
		MenuItem addUserMenu = new MenuItem("Aggiungi utente");
		
		MenuItem searchOperaMenu = new MenuItem("Cerca opera");
		MenuItem uploadOperaMenu = new MenuItem("Carica opera");
		MenuItem notAppOperaMenu = new MenuItem("Non approvate");
		
		MenuItem beTrascMenu = new MenuItem("Diventa Trascrittore");
		MenuItem assignedOperaMenu = new MenuItem("Opere assegnate");
		MenuItem notAppTrascMenu = new MenuItem("Non approvate");
		MenuItem assignTrascMenu = new MenuItem("Carica opera");
		
		MenuItem contactMenu = new MenuItem("Contattaci");
		MenuItem infoMenu = new MenuItem("Informazioni");
		
		switch (Cookie.user.getRuolo()) {
		case 'u':
			userMenu.getItems().addAll(profileMenu, modDataMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			trascMenu.getItems().addAll(beTrascMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
            break;
		case 't':
			userMenu.getItems().addAll(profileMenu, modDataMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			trascMenu.getItems().addAll(assignedOperaMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 's':
			userMenu.getItems().addAll(profileMenu, modDataMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			trascMenu.getItems().addAll(assignedOperaMenu, new SeparatorMenuItem(), notAppTrascMenu, assignTrascMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 'm':
			userMenu.getItems().addAll(profileMenu, modDataMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu, new SeparatorMenuItem(), notAppOperaMenu);
			trascMenu.getItems().addAll(beTrascMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		case 'a':
			userMenu.getItems().addAll(profileMenu, modDataMenu, logoutMenu, new SeparatorMenuItem(), addUserMenu, searchUserMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu, new SeparatorMenuItem(), notAppOperaMenu);
			trascMenu.getItems().addAll(beTrascMenu, assignedOperaMenu, new SeparatorMenuItem(), notAppTrascMenu, assignTrascMenu);
			helpMenu.getItems().addAll(contactMenu, infoMenu);
			break;
		default:
			Exception e = new Exception();
			throw e;
		}
		
		MenuBar topMenuBar = new MenuBar();
		topMenuBar.setId("topMenu");
		topMenuBar.getMenus().addAll(userMenu, operaMenu, trascMenu, helpMenu);
		
		/* 
		 * Pare che in JavaFX non sia possibile risalire da Menu a MenuBar
		 * abbiamo necessità di farlo perchè da MenuBar possiamo risalire a Scene e chiamare hide()
		 * 
		 * il ciclo foreach serve ad aggiungere come proprietà ad ogni Menu di topMenuBar un'associazione chiave-valore
		 * che in questo caso è null-topMenuBar così da poter chiamare in seguito getProperties().get(null)
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
		try {
			((Node) (event.getSource())).getScene().getWindow().hide();
			root = loader.load(HomeController.class.getResource("/view/Home.fxml").openStream());
	        root.setTop(topMenu());
			Scene scene = new Scene(root);
	        HomeController hc = (HomeController)loader.getController();
	        hc.setName();
		    stage.setScene(scene);
		    stage.setTitle("Home");
		    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
			root = loader.load(UserProfileController.class.getResource("/view/UserProfile.fxml").openStream());
			MenuBar mb = topMenu();
			AnchorPane.setLeftAnchor(mb, 0.0);
			AnchorPane.setRightAnchor(mb, 0.0);
			root.getChildren().add(mb);
			Scene scene = new Scene(root);
	        UserProfileController upc = (UserProfileController)loader.getController();
		    upc.defineView();
		    stage.setScene(scene);
		    stage.setTitle("Profilo utente");
		    stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("L'utente non ha ruolo");
		}
	}
}
