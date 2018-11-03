package application.controller.purchasingManager;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;
import java.sql.Statement;
import application.controller.Connection_db;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;


public class ProductAdding {

	@FXML
	private Button cancel;

	@FXML
	private AnchorPane background;

	@FXML
	private TextField name;

	@FXML
	private TextField price;

	@FXML
	private TextField intensity;

	@FXML
	private TextField description;

	@FXML
	private MenuButton type;

	@FXML
	private TextField quantity;


	public void cancel(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/purchasingManager/PurchasingManager.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void switchCapsule(){
		type.setText("Capsule");
	}

	public void switchMachine(){
		type.setText("Machine");
	}

	public void add(){

		if(validation()){
			if(type.getText().equals("Capsule")){

				Statement stmt = null;
				String queryProduct = "Insert into Product (Label,Price,Description) values ('"
						+ name.getText()
						+ "','" + price.getText()
						+ "','" + description.getText() + "');";



				String queryCapsule = "Insert into Cap values ((Select Code_Product From Product Where Label like '" + name.getText()
				+ "' ),"+quantity.getText()+");";

				String queryStock = "Call product_in_all_shop((Select Code_Product From Product Where Label like '"+ name.getText() +"'),"+ quantity.getText() +");";

				try {
					stmt = Connection_db.getConnection().createStatement();
					stmt.executeUpdate(queryProduct);
					stmt.executeUpdate(queryCapsule);
					stmt.executeUpdate(queryStock);
				} catch (SQLException e) {
					e.printStackTrace();
				}

			}


			if(type.getText().equals("Machine")){

				Statement stmt = null;
				String queryProduct = "Insert into Product (Label,Price,Description) values ('"
						+ name.getText()
						+ "','" + price.getText()
						+ "','" + description.getText() + "');";



				String queryCapsule = "Insert into Cofee_Machine values ((Select Code_Product From Product Where Label like '" + name.getText()
				+ "' ));";

				String queryStock = "Call product_in_all_shop((Select Code_Product From Product Where Label like '"+ name.getText() +"'),"+ quantity.getText() +");";

				System.out.println(queryProduct +" " + queryCapsule + " " + queryStock);
				try {
					stmt = Connection_db.getConnection().createStatement();
					stmt.executeUpdate(queryProduct);
					stmt.executeUpdate(queryCapsule);
					stmt.executeUpdate(queryStock);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			cancel();
		}
	}

	public boolean validation(){
		boolean validate = true;
		Alert fail= new Alert(AlertType.INFORMATION);
		fail.setHeaderText("Erreur");
		//type verification
		if(type.getText().equals("TYPE")){

			fail.setContentText("Veuillez choisir un type");
			fail.showAndWait();
			validate = false;
		}
		//name verification
		else if(name.getText() == null || name.getText().trim().isEmpty()){
			fail.setContentText("Le champ Nom ne doit pas être vide");
			fail.showAndWait();
			validate = false;
		}

		//name length verification
		else if(name.getLength() > 20 ){
			fail.setContentText("Le champ Nom doit faire moins de 20 caractères");
			fail.showAndWait();
			validate = false;
		}

		//price verification
		else if(price.getText() == null || price.getText().trim().isEmpty()){
			fail.setContentText("Le champ Prix ne doit pas être vide");
			fail.showAndWait();
			validate = false;
		}
		//verification that the price is an int
		else if(!isNumber(price.getText())){
			fail.setContentText("Veuillez entrer un nombre valable pour le prix");
			fail.showAndWait();
			validate = false;
		}

		//quantity verification
		else if(quantity.getText() == null || quantity.getText().trim().isEmpty()){
			fail.setContentText("Le champ Quantité ne doit pas être vide");
			fail.showAndWait();
			validate = false;
		}
		
		//verification that the quantity is an int
		else if(!isNumber(quantity.getText())){
			fail.setContentText("Veuillez entrer un nombre valable pour la quantité");
			fail.showAndWait();
			validate = false;
		}
		//description verification
		else if(description.getText() == null || description.getText().trim().isEmpty()){
			fail.setContentText("Le champ Description ne doit pas être vide");
			fail.showAndWait();
			validate = false;
		}
		//description length verification
		else if(description.getLength() > 100){
			fail.setContentText("La description doit faire moins de 100 caractères");
			fail.showAndWait();
			validate = false;
		}
		else {
			if(type.getText().equals("Capsule")){
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Succès");
			alert.setContentText("Capsule ajoutée avec succès");
			alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Succès");
				alert.setContentText("Machine ajoutée avec succès");
				alert.showAndWait();
			}
		}
		return validate;
	}

	
	@FXML
	public void enterPressed(ActionEvent ae){
		add();
	}
	
	
	//inspired from StackOverflow
	public static boolean isNumber(String str) {
	    try {
	        double v = Double.parseDouble(str);
	        return true;
	    } catch (NumberFormatException e) {
	    }
	    return false;
	}
}
