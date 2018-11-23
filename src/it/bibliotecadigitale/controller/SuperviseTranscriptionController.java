package it.bibliotecadigitale.controller;

import java.util.ArrayList;

import it.bibliotecadigitale.model.dao.PaginaDao;
import it.bibliotecadigitale.model.Pagina;

public class SuperviseTranscriptionController {
		
	/**
	 * Restituisce pagine non ancora approvate
	 * @return ArrayList<Pagina>
	 */
	public ArrayList<Pagina> getPages() {
		ArrayList<Pagina> pages = new ArrayList<Pagina>();
		PaginaDao db = new PaginaDao();
		
		try {
			pages = db.searchPaginaNotApproved();
		}
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return pages;
		
	}
}
