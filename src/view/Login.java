package view;

import java.util.Scanner;

import controller.LoginController;

public class Login {
	//TODO Placeholder constructor for testing purposes
	public Login(LoginController controller) throws Exception {
		System.out.println("Insert username:");
		Scanner scanner = new Scanner(System.in);
		String login = scanner.nextLine();
		System.out.println("Insert password:");
		String password = scanner.nextLine();
		boolean test = controller.login(login, password);
		if (!test) {
			System.out.println("Login Unsuccessful");
		}
	}
}
