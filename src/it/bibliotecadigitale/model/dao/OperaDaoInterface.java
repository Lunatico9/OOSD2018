package it.bibliotecadigitale.model.dao;

import java.util.ArrayList;
import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Pagina;

interface OperaDaoInterface {
	
	/**
	 * Aggiunge nuova opera al database
	 * 
	 * @param titolo dell'opera
	 * @param autore dell'opera
	 * @param anno di pubblicazione dell'opera
	 * @throws Exception
	 */
	void addOpera(String titolo, String autore, int anno) throws Exception;
	
	/**
	 * Modifica opera sul database
	 * 
	 * @param operaId identificativo dell'opera
	 * @param titolo dell'opera
	 * @param autore dell'opera
	 * @param categoria dell'opera
	 * @param anno di pubblicazione dell'opera
	 * @throws Exception
	 */
	void modifyOpera(int operaId, String titolo, String autore, String categoria, int anno) throws Exception;

	/**
	 * Aggiunge nuova categoria al database
	 * 
	 * @param operaId identificativo dell'opera
	 * @param categoria dell'opera
	 * @throws Exception
	 */
	void addCategoriaToOpera(int operaId, String categoria) throws Exception;
	
	/**
	 * Restituisce ID di un'opera
	 * 
	 * @param titolo dell'opera
	 * @param autore dell'opera
	 * @param anno di pubblicazione dell'opera
	 * @return identificativo dell'opera
	 * @throws Exception
	 */
	int getOpera(String tit, String aut, int year) throws Exception;

	/**
	 * Restituisce categoria di un opera
	 * 
	 * @param operaId identificativo dell'opera
	 * @return categoria dell'opera
	 * @throws Exception
	 */
	String getCategoria(int operaId) throws Exception;
	
	/**
	 * Restituisce elenco completo delle categorie presenti nel database
	 * 
	 * @return ArrayList di categorie nel database
	 * @throws Exception
	 */
	ArrayList<String> getCategorie() throws Exception;
	
	/**
	 * Restituisce tutte le pagine di un'opera
	 * 
	 * @param operaId identificativo dell'opera
	 * @return ArrayList di pagine dell'opera
	 * @throws Exception
	 */
	ArrayList<Pagina> getPagine(int operaId) throws Exception;
	
	/**
	 * Controlla se un'opera è assegnata ad un utente
	 * 
	 * @param utenteId identificativo dell'utente
	 * @param operaId identificativo dell'opera
	 * @return true se è assegnata, false altrimenti
	 * @throws Exception
	 */
	boolean isAssigned(int utenteId, int operaId) throws Exception;
	
	/**
	 * Cerca opere per titolo
	 * 
	 * @param titolo dell'opera
	 * @return ArrayList di opere
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByName (String titolo) throws Exception;
	
	/**
	 * Cerca opere per autore
	 * 
	 * @param autore dell'opera
	 * @return ArrayList di opere
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByAuthor (String autore) throws Exception;
	
	/**
	 * Cerca opere per categoria
	 * 
	 * @param categoria dell'opera
	 * @return ArrayList di opere
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByCategory (String categoria) throws Exception;
	
	/**
	 * Cerca opere non ancora approvate
	 * 
	 * @return ArrayList di opere
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaNotApproved () throws Exception;
	
	/**
	 * Assegna trascrizione di un'opera ad un trascrittore
	 * 
	 * @param userId identificativo dell'utente
	 * @param operaId identificativo dell'opera
	 * @throws Exception
	 */
	void allocateOpera (int userId, int operaId) throws Exception;
	
	/**
	 * Revoca trascrizione di un'opera ad un trascrittore
	 * 
	 * @param userId identificativo dell'utente
	 * @param operaId identificativo dell'opera
	 * @throws Exception
	 */
	void deallocateOpera (int userId, int operaId) throws Exception;
	
	/**
	 * Approva un'opera caricata
	 * 
	 * @param operaId identificativo dell'opera
	 * @throws Exception
	 */
	void approveOpera (int operaId) throws Exception;
	
	/**
	 * Cancella un'opera dal database
	 * 
	 * @param operaId identificativo dell'opera
	 * @throws Exception
	 */
	void delOpera (int operaId) throws Exception;

	
}
