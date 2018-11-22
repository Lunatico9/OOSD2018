package it.bibliotecadigitale.helper;

class Password {
  	/**
  	 *  Definisco il workload di BCrypt da usare quando genera gli hash della password.
  	 *  Valore accettabile 10-31
  	 */
	private static int workload = 12;

	/**
	 * Questo metodo viene usato per generare una stringa che rappresenta la password di un account
	 * idoneo per essere salvato su un database. Sarà formattato secondo lo stile OpenBSD crypt(3)
	 * con una stringa hash di lunghezza=60
	 * Il workload di bcrypt è specificato nella precedente variabile statica, un valore da 10 a 31.
	 * Un workload ragionevole di 12, dovrebbe risultare sicuro come standard dal 2013.
	 * Questo metodo gestisce automaticamente una generazione e l'archiviagione della stringa
	 * salt a 128 bit all'interno dell'hash.
	 * @param password_plaintext La password in chiaro dell'account viene fornito durante
	 *               la creazione dell'account o quando si modifica la password dell'account.
	 * @return String - Una stringa di lunghezza 60 che è la password cifrata in bcrypt in formato in crypt(3).
	 */
	public static String hashPassword(String password_plaintext) {
		String salt = BCrypt.gensalt(workload);
		String hashed_password = BCrypt.hashpw(password_plaintext, salt);

		return(hashed_password);
	}

	/**
	 * Questo metodo può essere usato per verificare un hash calcolato da un testo in chiaro
	 * (es. dopo una richiesta di login), con l'hash archiviato sul database. L'hash della password
	 * dal database deve essere passata come seconda variabile.
	 * @param password_plaintext Password in chiaro dell'account fornita durante la richiesta di login
	 * @param stored_hash Hash password archiviata dell'account recuperata dal database
	 * @return boolean - true se la password coincide con la password dell'hash archiviato, altrimenti false
	 */
	public static boolean checkPassword(String password_plaintext, String stored_hash) {
		boolean password_verified = false;

		if(null == stored_hash || !stored_hash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Hash fornito non valido per il confronto");

		password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

		return(password_verified);
	}
}
