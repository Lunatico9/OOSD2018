package it.bibliotecadigitale.model;

import it.bibliotecadigitale.vo.Datazione;
import it.bibliotecadigitale.vo.Identification;

public class Opera {
    
	private Identification operaId;
	private String titolo;
	private String autore;
	private String categoria;
	private Datazione periodo;
	private boolean approvato;
	
	public Opera () {}
	
	public Opera (String tit, String aut) {
		this.titolo = tit;
		this.autore = aut;
	}
			
	public Opera (int id, String tit, String aut, String cat, int anno, boolean app) {
		this.operaId = new Identification(id);
		this.titolo = tit;
		this.autore = aut;
		this.categoria = cat;
		this.periodo = new Datazione(anno);
		this.approvato = app;
	}
	
	public int getId() {
        return this.operaId.getId();
    }
    
    public void setId(int id) {
    	this.operaId.setId(id);
    }
	
    public String getTitolo() {
        return this.titolo;
    }
    
    public void setTitolo(String tit) {
    	this.titolo = tit;
    }
    
    public String getAutore() {
        return this.autore;
    }
    
    public void setAutore(String a) {
    	this.autore = a;
    }
    
    public String getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(String c) {
    	this.categoria = c;
    }
    
    public Datazione getDatazione() {
        return this.periodo;
    }
    
    public void setDatazione(int anno) {
    	periodo.setDatazione(anno);
    }
    
    public Boolean getApp() {
        return this.approvato;
    }
    
    public void setApp(boolean b) {
    	this.approvato = b;
    }
}
