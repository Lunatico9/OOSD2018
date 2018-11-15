package controller;

import dao.UtenteDao;
import javafx.event.ActionEvent;

public class ModPassAdminController extends ModPassController {

	public void change(ActionEvent event) {
		String op = txtOldPass.getText();
		String np1 = txtNewPass1.getText();
		String np2 = txtNewPass2.getText();
		
		UtenteDao db = new UtenteDao();
		
		try {
			
			if (!op.equals(Cookie.selectedUser.getPassw())) {
				lblStat.setText("Password Errata");
			}
			
			if (!np1.equals(np2) || np1.isEmpty()) {
				lblStat.setText("Le password non combaciano");
			}
			
			if (op.equals(Cookie.selectedUser.getPassw()) && np1.equals(np2) && !np1.isEmpty()) {
				Cookie.selectedUser.setPassw(np1);
				db.modifyPassw(np1, Cookie.selectedUser.getId());
				Main.toUserProfileAdmin(event);
			}
		}
		catch (Exception e) {
			System.out.println("Database error");
			e.printStackTrace();
		}
	}
}
