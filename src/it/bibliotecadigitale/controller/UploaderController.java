package it.bibliotecadigitale.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.dao.PaginaDao;

public class UploaderController {
		
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
	 * Aggiunge opera al database
	 * @param String tit
	 * @param String aut
	 * @param String cat
	 * @param String d
	 * @param List<File> imageFileList
	 * @return
	 */
	public boolean addOpera(String tit, String aut, String cat, String d, List<File> imageFileList) {
		OperaDao db = new OperaDao();
		PaginaDao pd = new PaginaDao();
		
		int year = 0;
		
		//utilizziamo Scanner con espressione regolare per estrarre interi da String
		Scanner in = new Scanner(d);
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
			
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

}
