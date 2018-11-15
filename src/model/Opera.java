package model;

import vo.Datazione;
import vo.Identification;

public class Opera {
    
	private Identification operaId;
	private String titolo;
	private String autore;
	private Datazione periodo;
	private boolean approvato;
	
	public Opera () {}
			
	public Opera (int id, String tit, String aut, int anno, boolean app) {
		this.operaId = new Identification(id);
		this.titolo = tit;
		this.autore = aut;
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
