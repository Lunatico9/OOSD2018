package model;

public class Opera {

	private String titolo;
	private String autore;
	private int anno;
	private boolean approvato;
	
	public Opera (String tit, String aut, int anno, boolean app) {
		this.titolo = tit;
		this.autore = aut;
		this.anno = anno;
		this.approvato = app;
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
    
    public int getAnno() {
        return this.anno;
    }
    
    public void setAnno(int anno) {
    	this.anno = anno;
    }
    
    public Boolean getApp() {
        return this.approvato;
    }
    
    public void setApp(boolean b) {
    	this.approvato = b;
    }
}
