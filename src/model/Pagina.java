package model;

import java.sql.Timestamp;

public class Pagina {

	private String trascrizione;
	private Timestamp ultModifica;
	private boolean approvato;
	
	public Pagina(String trsc, Timestamp t, boolean app) {
		this.trascrizione = trsc;
		this.ultModifica = t;
		this.approvato = app;
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
