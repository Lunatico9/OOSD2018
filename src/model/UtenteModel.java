package model;

import dao.*;
import java.sql.*;

public class UtenteModel {
	
	private int userId;
	private String login;
    private String passw;
    private String nome;
    private String cognome;
    private boolean privilegio;
    private String ruolo;
    private int livello;
    
    public UtenteModel(String login, String passw) {
    	ResultSet rs;
    	try {
    		rs = Utente.Login(login, passw);
    	    this.userId = rs.getInt("utente.ID");
    	    this.login = rs.getString("utente.Login");
		    this.passw = rs.getString("utente.Passw");
		    this.nome = rs.getString("utente.Nome");
            this.cognome = rs.getString("utente.Cognome");
            this.privilegio = rs.getBoolean("utente.Privilegio");
            this.ruolo = rs.getString("ruolo.Nome");
            this.livello = rs.getInt("ruolo.Livello");
    	}
    	catch (SQLException e) {
    		e.printStackTrace();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public int getId() {
        return this.userId;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    public String getPassw() {
        return this.passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
        try {
        	Utente.ModifyPassw(passw, this.userId);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public String geNome() {
        return this.nome;
    }
    
    public String getCognome() {
        return this.cognome;
    }
    
    public Boolean getPriv() {
        return this.privilegio;
    }
    
    public void setPriv(Boolean priv) {
    	try {
    		if (priv)
        		Utente.DelPriv(this.userId);
        	else Utente.AddPriv(this.userId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public String getRuolo() {
    	return this.ruolo;
    }
    
    public void setRuolo(String ruolo) {
    	this.ruolo = ruolo;
    	try {
    		Utente.ModifyRuolo(ruolo, this.userId);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public int getLiv() {
    	return this.livello;
    }

    public void setLiv(int liv) {
    	this.livello = liv;
        try {
        	Trascrittore.ModifyLivello(liv, this.userId);
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
}
