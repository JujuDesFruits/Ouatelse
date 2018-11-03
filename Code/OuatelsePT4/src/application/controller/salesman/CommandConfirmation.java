package application.controller.salesman;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import application.util.Client;
import application.util.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;


public class CommandConfirmation {
	
	@FXML
	private AnchorPane background;
	
	public void goHome(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/Salesman.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void generatePdf(){
		String pdfName = null;
		
		if(ShoppingCart.getClientCart() != null){
			pdfName = "Facture" + ShoppingCart.getClientCart().getLastName() + ShoppingCart.getClientCart().getFirstName();
			try {
				PdfWriter writer = new PdfWriter(pdfName);
				PdfDocument pdfBill = new PdfDocument(writer);
				Document bill = new Document(pdfBill);
				
				Client billClient = ShoppingCart.getClientCart();
				//adding information on the bill
				bill.add(new Paragraph("Ouatelse Facture, client : " + billClient.getFirstName() + " " + billClient.getLastName()));
				
				int totalPrice = 0;
				
				for(Product p : ShoppingCart.getProductsCart()){
					bill.add(new Paragraph(" " + p.getLabel() +" --- " + p.getPrice() * p.getQuantityCart() + "€"));
					totalPrice += p.getPrice() * p.getQuantityCart();
				}
				
				bill.add(new Paragraph("Prix total : " + totalPrice + "€"));
				
				bill.close();
				
				Alert fail= new Alert(AlertType.INFORMATION);
		        fail.setHeaderText("Succès");
		        fail.setContentText("Le pdf a été généré");
		        fail.showAndWait();
		        
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else{
			//Alert no client selected
			Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Vous n'avez pas renseigné de client");
	        fail.showAndWait();
	        try {
				AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/ShoppingCart.fxml"));
				background.getChildren().setAll(newPane);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
