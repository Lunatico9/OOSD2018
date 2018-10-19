package dao;

import java.util.ArrayList;
import model.Opera;

interface OperaDaoInterface {
	
	void AddOpera(String titolo, String autore, int anno) throws Exception;
	
	void AddCategoriaToOpera(int operaId, String categoria) throws Exception;
	
	ArrayList<String> GetCategorie(int operaId) throws Exception;
	
	ArrayList<Opera> SearchOperaByName (String titolo) throws Exception;
	
	ArrayList<Opera> SearchOperaByAuthor (String autore) throws Exception;

	ArrayList<Opera> SearchOperaByCategory (String categoria) throws Exception;
	
	ArrayList<Opera> SearchOperaNotApproved () throws Exception;
	
	void AllocateOpera (int userId, int operaId) throws Exception;
	
	void DeallocateOpera (int userId, int operaId) throws Exception;
	
	void ApproveOpera (int operaId) throws Exception;
	
	void DelOpera (int operaId) throws Exception;
	
}
