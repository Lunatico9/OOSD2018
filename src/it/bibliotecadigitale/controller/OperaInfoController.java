package it.bibliotecadigitale.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.dao.PaginaDao;
import it.bibliotecadigitale.model.dao.UtenteDao;
import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Pagina;
import it.bibliotecadigitale.model.Utente;

public class OperaInfoController {
	
	/**
	 * Recupera dal database lista di pagine dell'opera
	 * @param id
	 * @return ArrayList<Pagina>
	 */
	public ArrayList<Pagina> getThumbnails(int id) {
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		OperaDao db = new OperaDao();
	
		try {
			pages = db.getPagine(id);
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return pages;
	}
	
	/**
	 * Effettua il download dell'opera
	 * @param Opera selOpera
	 * @return boolean
	 */
	public boolean download(Opera selOpera) {
		//Creo la directory dove salvare l'opera
		File dir = new File("C:/Bibliotecadigitale/" + selOpera.getTitolo());
		if(dir.mkdirs()) {System.out.println("creato");}
		
		//Scrivo nella directory i metadati
		File opera = new File("C:/Bibliotecadigitale/" + selOpera.getTitolo() + "/Metadati.txt");
		try {
			opera.createNewFile();
			
			FileWriter fw = new FileWriter(opera);
			fw.write(selOpera.getTitolo() + "/n" + selOpera.getAutore() + "/n" + selOpera.getCategoria() + "/n" + selOpera.getDatazione().getAnno());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		OperaDao db= new OperaDao();
		//Estraggo le pagine dell'opera e scrivo path delle immagini e trascrizioni(se esistono)
		try {
			pages = db.getPagine(selOpera.getId());
			int i = 1;
			
			for(Pagina p : pages) {
				File pagina = new File("C:/Bibliotecadigitale/" + selOpera.getTitolo() + "/Pagina" + i + ".jpg");
				pagina.createNewFile();
				i++;
				
				FileChannel source = null;
				FileChannel destination = null;
				
				String path = p.getImmagine().substring(5, p.getImmagine().length());

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
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Approva l'opera
	 * @param int id
	 * @return boolean
	 */
	public boolean approve(int id) {
		OperaDao db = new OperaDao();
		try {
			db.approveOpera(id);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Elimina l'opera dal database
	 * @param int id
	 * @return boolean
	 */
	public boolean delete(int id) {
		OperaDao db = new OperaDao();
		PaginaDao pd = new PaginaDao();
		
		try {
			ArrayList<Pagina> pages = new ArrayList<Pagina>();
			pages.addAll(db.getPagine(id));
			if (pages != null) {
				for (Pagina p : pages) {
					pd.delPagina(p.getId());
				}
			}
			
			db.delOpera(id);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Assegna l'opera al trascrittore selezionato
	 * @param int id
	 * @String input
	 * @return boolean
	 */
	public boolean assign(int id, String input) {
		UtenteDao db = new UtenteDao();
		try {
			if (db.isNotRegisteredWithUsername(input)) {
				return false;
			}	
			else {
				Utente user = new Utente();
				user = db.getUtente(input);
				if (user.getRuolo() != 't') {
				}
				else {
					OperaDao od = new OperaDao();
					od.allocateOpera(user.getId(), Memento.selectedOpera.getId());
					return true;
				}
				
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
}
