package view;

import java.util.Scanner;

import controller.LoginController;
import controller.RegistrationController;

public class Home {
	//TODO Placeholder constructor for testing purposes
	public Home () throws Exception {
		System.out.println("Press l for login, r for registration");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		if (input.equals("l")) {
			LoginController login = new LoginController();
		} else {
			if (input.equals("r")) {
				RegistrationController registration = new RegistrationController();
			} else {
				Exception e = new Exception ();
				throw e;
			}
		}
	}
}
