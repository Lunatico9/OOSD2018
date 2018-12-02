package it.bibliotecadigitale.controller;

import java.util.ArrayList;
import java.util.Scanner;

import it.bibliotecadigitale.model.dao.OperaDao;

public class ModOperaController {
	
	
	/**
	 * Recupera elenco di categorie presenti nel database
	 * @return ArrayList<String>
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
	 * @param int id
	 * @param String tit
	 * @param String aut
	 * @param String cat
	 * @param String d
	 * @return
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
