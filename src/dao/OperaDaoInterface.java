package dao;

import java.util.ArrayList;
import model.Opera;

interface OperaDaoInterface {
	
	void addOpera(String titolo, String autore, int anno) throws Exception;
	
	void addCategoriaToOpera(int operaId, String categoria) throws Exception;
	
	ArrayList<String> getCategorie(int operaId) throws Exception;
	
	ArrayList<Opera> searchOperaByName (String titolo) throws Exception;
	
	ArrayList<Opera> searchOperaByAuthor (String autore) throws Exception;

	ArrayList<Opera> searchOperaByCategory (String categoria) throws Exception;
	
	ArrayList<Opera> searchOperaNotApproved () throws Exception;
	
	void allocateOpera (int userId, int operaId) throws Exception;
	
	void deallocateOpera (int userId, int operaId) throws Exception;
	
	void approveOpera (int operaId) throws Exception;
	
	void delOpera (int operaId) throws Exception;
	
}
