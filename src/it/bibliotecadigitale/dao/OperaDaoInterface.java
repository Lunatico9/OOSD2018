package it.bibliotecadigitale.dao;

import java.util.ArrayList;
import it.bibliotecadigitale.model.Opera;
import it.bibliotecadigitale.model.Pagina;

interface OperaDaoInterface {
	
	/**
	 * Aggiunge nuova opera al database
	 * @param String titolo
	 * @param String autore
	 * @param int anno
	 * @throws Exception
	 */
	void addOpera(String titolo, String autore, int anno) throws Exception;
	
	/**
	 * Modifica opera sul database
	 * @param int operaId
	 * @param String titolo
	 * @param String autore
	 * @param String categoria
	 * @param int anno
	 * @throws Exception
	 */
	void modifyOpera(int operaId, String titolo, String autore, String categoria, int anno) throws Exception;

	/**
	 * Aggiunge nuova categoria al database
	 * @param int operaId
	 * @param String categoria
	 * @throws Exception
	 */
	void addCategoriaToOpera(int operaId, String categoria) throws Exception;
	
	/**
	 * Restituisce ID di un'opera
	 * @param String titolo
	 * @param String autore
	 * @param int anno
	 * @return int
	 * @throws Exception
	 */
	int getOpera(String tit, String aut, int year) throws Exception;

	/**
	 * Restituisce categoria di un opera
	 * @param int operaId
	 * @return String
	 * @throws Exception
	 */
	String getCategoria(int operaId) throws Exception;
	
	/**
	 * Restituisce elenco completo delle categorie presenti nel database
	 * @param int operaId
	 * @return ArrayList<String>
	 * @throws Exception
	 */
	public ArrayList<String> getCategorie() throws Exception;
	
	/**
	 * Restituisce tutte le pagine di un'opera
	 * @param int operaId
	 * @return ArrayList<Pagina>
	 * @throws Exception
	 */
	public ArrayList<Pagina> getPagine(int operaId) throws Exception;
	
	/**
	 * Cerca opere per titolo
	 * @param String titolo
	 * @return ArrayList<Opera>
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByName (String titolo) throws Exception;
	
	/**
	 * Cerca opere per autore
	 * @param String autore
	 * @return ArrayList<Opera>
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByAuthor (String autore) throws Exception;
	
	/**
	 * Cerca opere per categoria
	 * @param String categoria
	 * @return ArrayList<Opera>
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaByCategory (String categoria) throws Exception;
	
	/**
	 * Cerca opere non ancora approvate
	 * @return ArrayList<Opera>
	 * @throws Exception
	 */
	ArrayList<Opera> searchOperaNotApproved () throws Exception;
	
	/**
	 * Assegna trascrizione di un'opera ad un trascrittore
	 * @param int userId
	 * @param int operaId
	 * @throws Exception
	 */
	void allocateOpera (int userId, int operaId) throws Exception;
	
	/**
	 * Revoca trascrizione di un'opera ad un trascrittore
	 * @param int userId
	 * @param int operaId
	 * @throws Exception
	 */
	void deallocateOpera (int userId, int operaId) throws Exception;
	
	/**
	 * Approva un'opera caricata
	 * @param int operaId
	 * @throws Exception
	 */
	void approveOpera (int operaId) throws Exception;
	
	/**
	 * Cancella un'opera dal database
	 * @param int operaId
	 * @throws Exception
	 */
	void delOpera (int operaId) throws Exception;

	
}
