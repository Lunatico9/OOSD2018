package model;

import dao.*;
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
	
}
