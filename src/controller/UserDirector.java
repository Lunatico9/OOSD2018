package controller;

import model.Utente;

public class UserDirector {
	public UserDirector(Utente user) throws Exception {
		char ruolo = user.getRuolo();
		switch (ruolo) {
		case 'a':
			AdminController admin = new AdminController();
			break;
		case 'u':
			UserController usr = new UserController(user);
			break;
		case 't':
			TranscriptionController transcriber = new TranscriptionController(user);
			break;
		case 's':
			UploadSupervisorController uploadSupervisor = new UploadSupervisorController();
			break;
		case 'm':
			TranscriptionManagerController transcriptionManager = new TranscriptionManagerController();
			break;
		default:
			Exception e = new Exception();
			throw e;
		}
	}
}
