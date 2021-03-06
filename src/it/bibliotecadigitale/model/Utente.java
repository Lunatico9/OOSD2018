package it.bibliotecadigitale.model;

import it.bibliotecadigitale.vo.Identification;

public class Utente {
	
	private Identification userId;
	private String login;
    private String passw;
    private String nome;
    private String cognome;
    private String mail;
    private String titolo;
    private String professione;
    private boolean privilegio;
    private char ruolo;
    private String strRuolo;
    private int livello;
    
    public Utente() {}
    
    public Utente(String log, String rol) {
    	this.login = log;
    	this.strRuolo = rol;
    }
    
    public Utente(int id, String log, String psw, String nom, String cog, String mail, String tit, String pro, boolean prv, char rol, int liv) {
    	this.userId = new Identification(id);
    	this.login = log;
    	this.passw = psw;
    	this.nome = nom;
    	this.cognome = cog;
    	this.mail = mail;
    	this.titolo = tit;
    	this.professione = pro;
    	this.privilegio = prv;
    	this.ruolo = rol;
    	this.livello = liv;
    }
    
    public int getId() {
        return this.userId.getId();
    }
    
    public void setId(int id) {
    	this.userId.setId(id);
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    
    public String getPassw() {
        return this.passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCognome() {
        return this.cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    public String getMail() {
        return this.mail;
    }
    
    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String getTitolo() {
        return this.titolo;
    }
    
    public void setTitolo(String tit) {
        this.titolo = tit;
    }
    
    public String getProfessione() {
        return this.professione;
    }
    
    public void setProfessione(String pro) {
        this.professione = pro;
    }
    
    public Boolean getPriv() {
        return this.privilegio;
    }
    
    public void setPriv(Boolean priv) {
    	this.privilegio = priv;
    }

    public char getRuolo() {
    	return this.ruolo;
    }
    
    public String getStrRuolo() {
    	return this.strRuolo;
    }
    
    public void setRuolo(char ruolo) {
    	this.ruolo = ruolo;
    }
    
    public void setStrRuolo(String strRuolo) {
    	this.strRuolo = strRuolo;
    }
    
    public int getLiv() {
    	return this.livello;
    }

    public void setLiv(int liv) {
    	this.livello = liv;
    }
}
   