package view;

import java.util.Scanner;

import controller.LoginController;
import controller.RegistrationController;

public class Registration {
	//TODO placeholder constructor for testing purposes
	public Registration (RegistrationController controller) throws Exception {
		Scanner scanner = new Scanner(System.in);
		String login;
		String password;
		String nome;
		String cognome;
		System.out.println("Insert new username:");
		login = scanner.nextLine();
		System.out.println("Insert new password:");
		password = scanner.nextLine();
		System.out.println("Insert name:");
		nome = scanner.nextLine();
		System.out.println("Insert surname:");
		cognome = scanner.nextLine();
		boolean success = controller.registerUser(login, password, nome, cognome);
		if (success) {
			System.out.println("registration successful, login using your new credentials");
			LoginController logincontrol = new LoginController();
		} else {
			System.out.println("username duplicated, try again with a different username");
		}
	}
}
