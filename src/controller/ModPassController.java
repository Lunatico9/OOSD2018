package controller;

import dao.UtenteDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModPassController {
	
	@FXML
	private TextField txtOldPass;
	@FXML
	private TextField txtNewPass1;
	@FXML
	private TextField txtNewPass2;
	@FXML
	private Label lblStat;
	
	/**
	 * Modifica la password nel Cookie e nel database
	 * @param ActionEvent event
	 */
	public void change(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!op.equals(Cookie.user.getPassw())) {
				System.out.println("if 1");
				lblStat.setText("Password Errata");
			}
			
			if (!np1.equals(np2) || np1.isEmpty()) {
				System.out.println("if 1");
				lblStat.setText("Le password non combaciano");
			}
			
			if (op.equals(Cookie.user.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
				System.out.println("else");
				Cookie.user.setPassw(np1);
				db.modifyPassw(np1, Cookie.user.getId());
				Main.toUserProfile(event);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}

}
