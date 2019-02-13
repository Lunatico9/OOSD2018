package it.bibliotecadigitale.controller;

import java.util.ArrayList;
import java.util.Scanner;

import it.bibliotecadigitale.model.dao.OperaDao;

public class ModOperaController {
	
	
	/**
	 * Recupera elenco di categorie presenti nel database
	 * 
	 * @return array di categorie presenti nel database
	 */
	public ArrayList<String> getCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		
		OperaDao db = new OperaDao();
		
		try {
			categories = db.getCategorie();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	/**
	 * Modifica metadati dell'opera
	 * 
	 * @param id identificativo dell'opera
	 * @param tit titolo dell'opera
	 * @param aut autore dell'opera
	 * @param cat categoria dell'opera
	 * @param d data dell'opera
	 * @return true se le informazioni vengono aggiornate, false altrimenti
	 */
	public boolean modify(int id, String tit, String aut, String cat, String d) {
		OperaDao db = new OperaDao();
		
		int year = 0;
		
		//utilizziamo Scanner con espressione regolare per estrarre interi da String
		Scanner in = new Scanner(d);
    	in.useDelimiter("[^0-9]+");
		year = in.nextInt();
		in.close();

		try {
			db.modifyOpera(id, tit, aut, cat, year);
			Memento.selectedOpera.setDatazione(year);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
}
