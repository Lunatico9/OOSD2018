package model;

import dao.*;
import java.util.ArrayList;
import java.sql.*;

public class PaginaModel {

	private int paginaId;
	private String immagine;
	private String trascrizione;
	private Timestamp ultModifica;
	private boolean approvato;
	
	public PaginaModel(int id, String imm, String trsc, Timestamp m, boolean app) {
		this.paginaId = id;
		this.immagine = imm;
		this.trascrizione = trsc;
		this.ultModifica = m;
		this.approvato = app;
	}
	
	public String getImm() {
        return this.immagine;
    }
    
    public String getTrascrizione() {
        return this.trascrizione;
    }
    
    public void setTrascrizione(String trsc) {
		this.trascrizione = trsc;
    	try {
    		Pagina.AddTrascrizione(trsc, this.paginaId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
	}
    
    public Timestamp getUltModifica() {
        return this.ultModifica;
    }
    
    public Boolean getApp() {
        return this.approvato;
    }
    
    public void setApp() {
    	this.approvato = true;
    	try {
    		Pagina.ApprovePagina(this.paginaId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public static ArrayList<PaginaModel> SearchPaginaNotApproved() {
    	ResultSet rs;
    	ArrayList<PaginaModel> pagine = new ArrayList<PaginaModel>();
        try {
        	int i = 0;
    		rs = Pagina.SearchPaginaNotApproved();
    		while (rs.next() && i<10) {
    			pagine.add(new PaginaModel(rs.getInt("ID"), rs.getString("Immagine"), rs.getString("trascrizione"), rs.getTimestamp("ult_modifica"), false));
    		}
    	}
        catch(Exception e) {
        	e.printStackTrace();
        }
        return pagine;
    }
}
