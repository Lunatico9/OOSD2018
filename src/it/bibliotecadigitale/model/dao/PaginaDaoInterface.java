package it.bibliotecadigitale.model.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import it.bibliotecadigitale.model.Pagina;

interface PaginaDaoInterface {
	
	/**
	 * Aggiunge nuova pagina di un'opera al database
	 * 
	 * @param operaId identificativo dell'opera
	 * @param numero della pagina
	 * @param img immagine della pagina
	 * @throws Exception
	 */
	void addPagina(int operaId, int numero, String img) throws Exception;
	
	/**
	 * Aggiunge nuova trascrizione di una pagina al database
	 * 
	 * @param trascrizione della pagina
	 * @param paginaId identificativo della pagina
	 * @throws Exception
	 */
    void addTrascrizione(String trascrizione, int paginaId) throws Exception;
	
    /**
	 * Restituisce le immagini di un'opera
	 * 
	 * @param operaId identificativo dell'opera
	 * @return ArrayList di locazioni delle immagini delle pagine
	 * @throws Exception
	 */
	ArrayList<String> getImageCollection(int operaId) throws Exception;
	
	 /**
	 * Restituisce ultima modifica di una pagina
	 * 
	 * @param paginaId identificativo della pagina
	 * @return ultima modifica
	 * @throws Exception
	 */
	public Timestamp getModifica(int paginaId) throws Exception;
	
	/**
	 * Restituisce trascrizione di una pagina
	 * @param paginaId identificativo della pagina
	 * @return trascrizione della pagina
	 * @throws Exception
	 */
	String getTrascrizione(int paginaId) throws Exception;

	
	/**
	 * Restituisce trascrizioni non ancora apporvate
	 * 
	 * @return ArrayList di Pagine
	 * @throws Exception
	 */
	ArrayList<Pagina> searchPaginaNotApproved() throws Exception;
	
	/**
	 * Approva trascrizioni
	 * 
	 * @param paginaId identificatico della pagina
	 * @throws Exception
	 */
	void approvePagina(int paginaId) throws Exception;
	
	/**
	 * Cancella pagina dal database
	 * 
	 * @param paginaId identificativo della pagina
	 * @throws Exception
	 */
	void delPagina(int paginaId) throws Exception;
	
}
