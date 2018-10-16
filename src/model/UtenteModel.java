package model;

import dao.*;
import java.util.ArrayList;
import java.sql.*;

public class UtenteModel {
	
	private int userId;
	private String login;
    private String passw;
    private String nome;
    private String cognome;
    private boolean privilegio;
    private char ruolo;
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
            this.ruolo = rs.getString("ruolo.Nome").charAt(0);
            this.livello = rs.getInt("ruolo.Livello");
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
    
    public char getRuolo() {
    	return this.ruolo;
    }
    
    public void setRuolo(char ruolo) {
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
    
    public static UtenteModel CreateUtente(String login, String passw, String nome, String cognome) {
    	try {
    	    Utente.AddUtente(login, passw, nome, cognome);
    	}
    	catch(Exception e) {
        	e.printStackTrace();
        }
    	return new UtenteModel(login, passw);
    }
    
    public static void DelUser (int userId) {
    	try {
    	    Utente.DelUtente(userId);
    	}
    	catch(Exception e) {
        	e.printStackTrace();
    	}
    }
    
    public static ArrayList <String> SearchUserByLogin(String login) {
    	ArrayList<String> user = new ArrayList<String>();
    	try {
    	    ResultSet rs = Utente.SearchUserByLogin(login);
    	    int i = 0;
    	    while (rs.next() && i<10) {
    	    	i++;
    	    	user.add(rs.getString("Login"));
    	    }
    	}
    	catch(Exception e) {
        	e.printStackTrace();
        }
    	return user;
    }    
    
    public static ArrayList <String> SearchUserByRuolo(char ruolo) {
    	ArrayList<String> user = new ArrayList<String>();
    	try {
    	    ResultSet rs = Utente.SearchUserByRuolo(ruolo);
    	    int i = 0;
    	    while (rs.next() && i<10) {
    	    	i++;
    	    	user.add(rs.getString("utente.Login"));
    	    }
    	}
    	catch(Exception e) {
        	e.printStackTrace();
        }
    	return user;
    }
}
