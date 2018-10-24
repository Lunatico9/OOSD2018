package dao;

import java.util.ArrayList;
import model.Pagina;

interface PaginaDaoInterface {
	
	void addPagina(int operaId, int numero, String img) throws Exception;
	
    void addTrascrizione(String trascrizione, int paginaId) throws Exception;
	
	ArrayList<String> getImageCollection(int operaId) throws Exception;
	
	ArrayList<Pagina> searchPaginaNotApproved() throws Exception;
	
	void approvePagina(int paginaId) throws Exception;
	
	void delPagina(int paginaId) throws Exception;
	
}
