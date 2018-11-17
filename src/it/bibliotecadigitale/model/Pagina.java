package it.bibliotecadigitale.model;

import java.sql.Timestamp;

import it.bibliotecadigitale.vo.Identification;

public class Pagina {

	private Identification paginaId;
	private String immagine;
	private String trascrizione;
	private Timestamp ultModifica;
	private boolean approvato;
	
	public Pagina() {}
	
	public Pagina(int id, String img, String trsc, Timestamp t, boolean app) {
		this.paginaId = new Identification(id);
		this.immagine = img;
		this.trascrizione = trsc;
		this.ultModifica = t;
		this.approvato = app;
	}

	public int getId() {
        return this.paginaId.getId();
    }
    
    public void setId(int id) {
    	this.paginaId.setId(id);
    }
    
    public String getImmagine() {
        return this.immagine;
    }
    
    public void setImmagine(String img) {
		this.immagine = img;
    }
		
    public String getTrascrizione() {
        return this.trascrizione;
    }
    
    public void setTrascrizione(String trsc) {
		this.trascrizione = trsc;
	}
    
    public Timestamp getUltModifica() {
        return this.ultModifica;
    }
    
    public void setUltModifica() {
    	this.ultModifica = new Timestamp(System.currentTimeMillis());
    }
    
    public Boolean getApp() {
        return this.approvato;
    }
    
    public void setApp(boolean b) {
    	this.approvato = b;
    }
}
