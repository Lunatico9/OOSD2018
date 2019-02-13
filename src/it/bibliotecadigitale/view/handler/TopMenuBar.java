package it.bibliotecadigitale.view.handler;

import it.bibliotecadigitale.controller.Memento;
import it.bibliotecadigitale.controller.Main;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class TopMenuBar {
	
	private MenuBar topMenu = new MenuBar();
	
	/**
	 * Genera menu dinamicamente basato sul ruolo dell'utente
	 * 
	 * @throws Exception 
	 */
	public TopMenuBar () throws Exception {
		Menu userMenu = new Menu("Utente");
		Menu operaMenu = new Menu("Opera");
		Menu managerMenu = new Menu("Manager");
		Menu helpMenu = new Menu("Aiuto");
		
		MenuItem profileMenu = new MenuItem("Profilo");
		profileMenu.setOnAction(e -> Main.toUserProfile(e));
		MenuItem logoutMenu = new MenuItem("Logout");
		logoutMenu.setOnAction(e -> {Memento.logOut(); Main.toLogin(e);});
		MenuItem transcFormMenu = new MenuItem("Diventa Trascrittore");
		transcFormMenu.setOnAction(e -> Main.toTranscForm(e));
		MenuItem searchUserMenu = new MenuItem("Cerca utente");
		searchUserMenu.setOnAction(e -> Main.toSearchUser(e));
		MenuItem addUserMenu = new MenuItem("Aggiungi utente");
		addUserMenu.setOnAction(e -> Main.toAddUser(e));
		
		MenuItem searchOperaMenu = new MenuItem("Cerca opera");
		searchOperaMenu.setOnAction(e -> Main.toSearchOpera(e));
		MenuItem uploadOperaMenu = new MenuItem("Carica opera");
		uploadOperaMenu.setOnAction(e -> Main.toUploadOpera(e));
		
		MenuItem notAppOperaMenu = new MenuItem("Supervisiona upload");
		notAppOperaMenu.setOnAction(e -> Main.toSearchOpera(e));
		MenuItem notAppTranscMenu = new MenuItem("Supervisiona trascrizioni");
		notAppTranscMenu.setOnAction(e -> Main.toSuperviseTranscription(e));
		
		MenuItem contactMenu = new MenuItem("Contattaci");
		contactMenu.setOnAction(e -> Main.toContactUs(e));
		
		switch (Memento.user.getRuolo()) {
		case 'u':
			userMenu.getItems().addAll(profileMenu, logoutMenu, transcFormMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			helpMenu.getItems().addAll(contactMenu);
            break;
		case 't':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			helpMenu.getItems().addAll(contactMenu);
			break;
		case 'r':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			managerMenu.getItems().addAll(notAppTranscMenu);
			helpMenu.getItems().addAll(contactMenu);
			break;
		case 's':
			userMenu.getItems().addAll(profileMenu, logoutMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			managerMenu.getItems().addAll(notAppOperaMenu);
			helpMenu.getItems().addAll(contactMenu);
			break;
		case 'a':
			userMenu.getItems().addAll(profileMenu, logoutMenu, transcFormMenu, new SeparatorMenuItem(), addUserMenu, searchUserMenu);
			operaMenu.getItems().addAll(searchOperaMenu, uploadOperaMenu);
			managerMenu.getItems().addAll(notAppOperaMenu, notAppTranscMenu);
			helpMenu.getItems().addAll(contactMenu);
			break;
		default:
			Exception e = new Exception();
			throw e;
		}
		
		topMenu.setId("topMenu");
		topMenu.getMenus().addAll(userMenu, operaMenu, managerMenu, helpMenu);
		
		/* 
		 * Pare che in JavaFX non sia possibile risalire da Menu a MenuBar
		 * abbiamo necessità di farlo perché da MenuBar possiamo risalire a Scene e chiamare hide()
		 * 
		 * il ciclo foreach serve ad aggiungere come proprietà ad ogni Menu di topMenuBar un'associazione chiave-valore
		 * che in questo caso è null-topMenuBar così da poter chiamare in seguito getProperties().get(null)
		 * ed ottenere topMenuBar
		 * 
		 */
		
		for (Menu m: topMenu.getMenus()) {
            m.getProperties().put(null, topMenu);
        }
	}
	
	/**
	 * Restituisce il campo topMenu
	 * 
	 * @return il menu creato
	 */
	public MenuBar getMenu () {
		return this.topMenu;
	}

}
