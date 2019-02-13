package it.bibliotecadigitale.controller;

import java.sql.Timestamp;

import it.bibliotecadigitale.model.dao.PaginaDao;

public class TranscriberController {
	
	/**
	 * Approva la trascrizione
	 * 
	 * @param id identificativo della pagina
	 * @return true se l'operazione riesce, false altrimenti
	 */
	public boolean approve(int id) {
		PaginaDao db = new PaginaDao();
		
		try {
			db.approvePagina(id);
			return true;
		} 
		catch (Exception e) {
			Main.toErrorMsg("Errore in connessione al Database");
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Recupera ultima modifica di una pagina dal database
	 * 
	 * @param id identificativo della pagina
	 * @return data di ultima modifica della pagina
	 */
	public Timestamp getModifica(int id) {
		
		PaginaDao db = new PaginaDao();
		Timestamp mod;
		
		try {
			mod = db.getModifica(id);
			return mod;
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * Aggiunge trascrizione ad una pagina nel database
	 * 
	 * @param id identificativo della pagina
	 * @param transcription trascrizione da aggiungere
	 * @return true se la trascrizione viene aggiunta, false altrimenti
	 */
	public boolean addTranscription(int id, String transcription) {
		PaginaDao db = new PaginaDao();
		
		try {
			db.addTrascrizione(transcription, id);
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 * Compara il testo locale e il testo presente sul database, quindi inserisce in un nuovo testo tutte le righe presenti in locale e le righe modificate tra parentesi quadre []
	 * 
	 * @param id identificativo della pagina
	 * @param localTransc trascrizione da comparare
	 * @return testo contenente entrambe le versioni delle trascrizioni
	 */
	public String update(int id, String localTransc) {
		
		PaginaDao db = new PaginaDao();
		
		String dbTransc;
		try {
			
			dbTransc = db.getTrascrizione(id);
			
			String[] formattedLocal = localTransc.split("<br>");
			String[] formattedDb = dbTransc.split("<br>");
			
			final int localLength = formattedLocal.length;
			final int dbLength = formattedDb.length;
			
			StringBuffer newText = new StringBuffer("");
		    
			if (localLength <= dbLength) {
				//se il testo locale ha meno righe di quello scaricato dal db iteriamo sulla lunghezza di questo
				for (int i = 0; i<localLength; i++) {
			    	//se la riga in formatted local � uguale alla riga in formatted db inseriamo nel nuovo testo solamente formatted local
					if (formattedLocal[i].equals(formattedDb[i])) {
			    		newText.append(formattedLocal[i] + "<br>");
			    	}
			    	//altrimenti inseriamo la riga nel testo locale e tra parentesi quadre la stringa nel testo sul db
			    	else {
			    		newText.append(formattedLocal[i] + "<br>["  + formattedDb[i] + "]<br>");
			    	}
			    		 
			    }
				//e insieramo in coda al nuovo testo le righe del testo scaricato non preso in considerazione dal primo ciclo for
				for (int i = 0; i<(dbLength - localLength); i++) {
					newText.append("[" + formattedDb[i+localLength] + "]<br>");
				}
			}
			else {
				//nel caso opposto iteriamo sulla lunghezza del testo nel db
				for (int i = 0; i<dbLength; i++) {
			    	
					if (formattedLocal[i].equals(formattedDb[i])) {
			    		newText.append(formattedLocal[i] + "<br>");
			    	}
			    	
					else {
			    		newText.append(formattedLocal[i] + "<br>["  + formattedDb[i] + "]<br>");
			    	}
			    }
				// quindi andiamo ad aggiungere in coda al nuovo testo le righe del testo locale non considerate nel primo ciclo for
				for (int i = 0; i<(localLength - dbLength); i++) {
					newText.append(formattedLocal[i+dbLength] + "<br>");
				}
			}
			return newText.toString();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

