package dao;

import java.util.ArrayList;
import model.Pagina;

interface PaginaDaoInterface {
	
	/**
	 * Aggiunge nuova pagina di un'opera al database
	 * @param int operaId
	 * @param int numero
	 * @param String img
	 * @throws Exception
	 */
	void addPagina(int operaId, int numero, String img) throws Exception;
	
	/**
	 * Aggiunge nuova trascrizione di una pagina al database
	 * @param String trascrizione
	 * @param int pagina id
	 * @throws Exception
	 */
    void addTrascrizione(String trascrizione, int paginaId) throws Exception;
	
    /**
	 * Restituisce le immagini di un'opera
	 * @param int operaId
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	ArrayList<String> getImageCollection(int operaId) throws Exception;
	
	/**
	 * Restituisce trascrizioni non ancora apporvate
	 * @param int operaId
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	ArrayList<Pagina> searchPaginaNotApproved() throws Exception;
	
	/**
	 * Approva trascrizioni
	 * @param int paginaId
	 * @throws Exception
	 */
	void approvePagina(int paginaId) throws Exception;
	
	/**
	 * Cancella pagina dal database
	 * @param int paginaId
	 * @throws Exception
	 */
	void delPagina(int paginaId) throws Exception;
	
}
