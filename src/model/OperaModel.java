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
    	catch (SQLException e) {
    		e.printStackTrace();
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

}
