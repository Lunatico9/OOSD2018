package it.bibliotecadigitale.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.dao.PaginaDao;

public class UploaderController {
		
	private final String PATHNAME = "./src/it/bibliotecadigitale/source/";
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
	 * @return boolean
	 */
	public boolean addOpera(String tit, String aut, String cat, String d, List<File> imageFileList) {
		OperaDao db = new OperaDao();
		PaginaDao pd = new PaginaDao();
		
		ArrayList<String> imagePath = new ArrayList<String>();
		
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
			
			//copia i file nella directory source del programma
			File dir = new File(PATHNAME + tit);
			if(dir.mkdirs()) {System.out.println("creato");}
			
			for(File file : imageFileList) {
				File pagina = new File(PATHNAME + tit + "/" + i + ".jpg");
				pagina.createNewFile();
				imagePath.add(pagina.toURI().toString());
				i++;
				
				FileChannel source = null;
				FileChannel destination = null;
				
				String pathFile = file.toURI().toString();
				String path = pathFile.substring(5, pathFile.length());

				FileInputStream fis = new FileInputStream(path);
				source = fis.getChannel();
				FileOutputStream fos = new FileOutputStream(pagina);
				destination = fos.getChannel();
				destination.transferFrom(source, 0, source.size());
				
				fis.close();
				source.close();
				fos.close();
				destination.close();
			}

			i = 0;
			for (String s : imagePath) {
				pd.addPagina(id, i, s);
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
