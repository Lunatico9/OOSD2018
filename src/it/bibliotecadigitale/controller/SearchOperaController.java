package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.model.dao.OperaDao;
import it.bibliotecadigitale.model.Opera;

public class SearchOperaController {
	
	private final String RTITOLO = "Per titolo";
	private final String RAUTORE= "Per autore";
	
	/**
	 * Genera lista di titoli o autori per popolare autocompletamento
	 * @param String input
	 * @param String fil
	 * @return ArrayList<String>
	 */
	public ArrayList<String> searchFiltered (String input, String fil) {
		ArrayList<String> auto = new ArrayList<String>();
		ArrayList<Opera> opere = new ArrayList<Opera>();
		OperaDao db = new OperaDao();
		
		try {
			if (fil == null){
				opere = db.searchOperaByName(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getTitolo());
					}
				}
		    } 
			else if (fil.equals(RTITOLO)) {
				opere = db.searchOperaByName(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getTitolo());
					}
				}
			}
			else {
				opere = db.searchOperaByAuthor(input);
				if(!opere.isEmpty()) {
					for(Opera o : opere) {
						auto.add(o.getAutore());
					}
				}
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return auto;
	}
	
	/**
	 * Costruisce dinamicamente lista di opere cercata
	 * @param String input
	 * @param String fil
	 * @return ArrayList<Opera>
	 */
	
	public ArrayList<Opera> searchOpera (String input, String fil) {
		ArrayList<Opera> opere = new ArrayList<Opera>();
		OperaDao db = new OperaDao();

		try {
			if (fil == null) {
				opere = db.searchOperaByName(input);
			}
			else if (fil.equals(RTITOLO)) {
				opere = db.searchOperaByName(input);
			}
			else if (fil.equals(RAUTORE)) {
				opere = db.searchOperaByAuthor(input);
			}
			else {
				opere = db.searchOperaNotApproved();
			}
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		
		return opere;
	}
	
	/*
	 * public void buildChoiceCat() {
		OperaDao db = new OperaDao();
		try {
			ArrayList<String> cat = db.getCategorie();
			choiceCategory.setItems(FXCollections.observableArrayList(cat));
		} catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	
	}*/
}

