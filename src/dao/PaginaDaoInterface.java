package dao;

import java.util.ArrayList;
import model.Pagina;

interface PaginaDaoInterface {
	
	void AddPagina(int operaId, int numero, String img) throws Exception;
	
    void AddTrascrizione(String trascrizione, int paginaId) throws Exception;
	
	ArrayList<String> GetImageCollection(int operaId) throws Exception;
	
	ArrayList<Pagina> SearchPaginaNotApproved() throws Exception;
	
	void ApprovePagina(int paginaId) throws Exception;
	
	void DelPagina(int paginaId) throws Exception;
	
}
