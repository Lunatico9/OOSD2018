package model;

import dao.*;
import java.sql.*;
import java.util.ArrayList;

public class OperaModel {
	
	private int operaId;
	private String titolo;
	private String autore;
	private int anno;
	private boolean approvato;
	private ArrayList <PaginaModel> pagine;
	private ArrayList <String> categorie;
	
	public OperaModel (int operaId) {
		ResultSet rs;
		try {
    		rs = Opera.GetOpera(operaId);
    	    this.operaId = rs.getInt("ID");
    	    this.titolo = rs.getString("Titolo");
		    this.autore = rs.getString("Autore");
		    this.anno = rs.getInt("Anno");
            this.approvato = rs.getBoolean("Approvato");
            rs = Opera.GetPagine(operaId);
            while (rs.next()) {
                  this.pagine.add(new PaginaModel(rs.getInt("pagina.ID"), rs.getString("pagina.Immagine"), rs.getString("pagina.trascrizione"), rs.getTimestamp("pagina.ult_modifica"), rs.getBoolean("pagina.approvato")));
            }
            rs = Opera.GetCategorie(operaId);
            while (rs.next()) {
            	this.categorie.add(rs.getString("categoria.Nome"));
            }
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
	}
	
	public int getId() {
        return this.operaId;
    }
    
    public String getTitolo() {
        return this.titolo;
    }
    
    public String getAutore() {
        return this.autore;
    }
    
    public int getAnno() {
        return this.anno;
    }
    
    public Boolean getApp() {
        return this.approvato;
    }
    
    public void setApp() {
    	this.approvato = true;
    	try {
    		Opera.ApproveOpera(this.operaId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static void AllocateOpera(int userId, int operaId) {
    	try {
    		Trascrittore.AllocateOpera(userId, operaId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static void DeallocateOpera(int userId, int operaId) {
    	try {
    		Trascrittore.DeallocateOpera(userId, operaId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static void CreateOpera(String titolo, String autore, int anno) {
    	try {
    	    Opera.AddOpera(titolo, autore, anno);
    	}
    	catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void DelOpera(int operaId) {
    	try {
    	    Opera.DelOpera(operaId);
    	}
    	catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static ArrayList<OperaModel> SearchOperaByTitolo(String titolo) {
    	ResultSet rs;
    	ArrayList<OperaModel> opere = new ArrayList<OperaModel>();
        try {
        	int i = 0;
    		rs = Opera.SearchOperaByName(titolo);
    		while (rs.next() && i<10) {
    			opere.add(new OperaModel(rs.getInt("ID")));
    		}
    	}
        catch(Exception e) {
        	e.printStackTrace();
        }
        return opere;	
    }
    
    public static ArrayList<OperaModel> SearchOperaByAutore(String autore) {
    	ResultSet rs;
    	ArrayList<OperaModel> opere = new ArrayList<OperaModel>();
        try {
        	int i = 0;
    		rs = Opera.SearchOperaByAuthor(autore);
    		while (rs.next() && i<10) {
    			opere.add(new OperaModel(rs.getInt("ID")));
    		}
    	}
        catch(Exception e) {
        	e.printStackTrace();
        }
        return opere;
    }
    
    public static ArrayList<OperaModel> SearchOperaByCategoria(String categoria) {
    	ResultSet rs;
    	ArrayList<OperaModel> opere = new ArrayList<OperaModel>();
        try {
        	int i = 0;
    		rs = Opera.SearchOperaByAuthor(categoria);
    		while (rs.next() && i<10) {
    			opere.add(new OperaModel(rs.getInt("opera.ID")));
    		}
    	}
        catch(Exception e) {
        	e.printStackTrace();
        }
        return opere;
    }
    
    public static ArrayList<OperaModel> SearchOperaNotApproved() {
    	ResultSet rs;
    	ArrayList<OperaModel> opere = new ArrayList<OperaModel>();
        try {
        	int i = 0;
    		rs = Opera.SearchOperaNotApproved();
    		while (rs.next() && i<10) {
    			opere.add(new OperaModel(rs.getInt("opera.ID")));
    		}
    	}
        catch(Exception e) {
        	e.printStackTrace();
        }
        return opere;
    }
}
