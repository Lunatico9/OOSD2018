package it.bibliotecadigitale.controller;

import it.bibliotecadigitale.dao.OperaDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import it.bibliotecadigitale.vo.Datazione;

public class OperaInfoController {

	@FXML
	private Label lblTit;
	@FXML
	private Label lblAut;
	@FXML
	private Label lblDate;
	@FXML
	private Label lblCat;
	@FXML
	private Button btnDownload;
	@FXML
	private Button btnApp;
	@FXML
	private Button btnMod;
	
	/**
	 * Realizza view personalizzata sulla base dei dati dell'utente
	 * @throws Exception
	 */
	
	public void defineView() throws Exception {
		lblTit.setText(Cookie.selectedOpera.getTitolo());
		lblAut.setText(Cookie.selectedOpera.getAutore());
		lblCat.setText(Cookie.selectedOpera.getCategoria());
		
		if (Cookie.selectedOpera.getDatazione().getAnno() > 1200) {
			Integer i = new Integer(Cookie.selectedOpera.getDatazione().getAnno());
			lblDate.setText(i.toString());
		}
		else {
			lblDate.setText(Cookie.selectedOpera.getDatazione().getDatazione());
		}
		
	
		if (Cookie.user.getPriv()) {
			btnDownload.setVisible(true);
		}
		
		if ((Cookie.user.getRuolo() == 'a' || Cookie.user.getRuolo() == 'm') && !Cookie.selectedOpera.getApp()) {
			btnApp.setVisible(true);
		}
		
		if (Cookie.user.getRuolo() == 'a') {
			btnMod.setVisible(true);
		}
		
		//Devo generare i thumbnail delle pagine
	}
	
	/**
	 * Permette il download dell'opera
	 * @param ActionEvent event
	 */
	public void download() {
		//Non abbiamo definito la procedura di download
	}
	
	/**
	 * Permette di approvare l'opera
	 * @param ActionEvent event
	 */
	public void appOpera(ActionEvent event) {
		Cookie.selectedOpera.setApp(true);
		
		OperaDao db = new OperaDao();
		try {
			db.approveOpera(Cookie.selectedOpera.getId());
		} 
		catch (Exception e) {
			System.out.println("Database Error");
			e.printStackTrace();
		}
	}
	
	/**
	 * Indirizza alla pagina di modifica opera
	 * @param ActionEvent event
	 */
	public void modOpera(ActionEvent event) {
		//Main.toModOpera(event);
	}
	
}
