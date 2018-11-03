 package application.controller.supervisor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.glass.events.MouseEvent;

import application.controller.Connection_db;
import application.util.Employee;
import application.util.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

public class AddShop {

	@FXML
	private Button cancel;
	
	@FXML
	private AnchorPane background;
	
	@FXML 
	private TextField cityTf;
	
	@FXML 
	private TextField addressTf;
	
	@FXML 
	private TextField zipCodeTf;
	
	@FXML 
	private MenuButton managerMb;
	
	public void cancel(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/HomeSupervisor.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		Statement stmt = null;
		String query = "select * from Person Inner Join Is_Salaried on Person.Code_Person = Is_Salaried.Code_Person";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				MenuItem miPerson = new MenuItem(res.getString("FirstName") + " " + res.getString("LastName"));
				
				EventHandler<ActionEvent> action = choosePerson();
				
				miPerson.setOnAction(action);
				
				managerMb.getItems().add(miPerson);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//inspired from http://www.java2s.com/Code/Java/JavaFX/Menuitemeventhandler.htm
	private EventHandler<ActionEvent> choosePerson() {
		 return new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent event) {
	                MenuItem mItem = (MenuItem) event.getSource();
	                managerMb.setText(mItem.getText());
	            }
	        };
	}

	public void add(){
		if(validation()){
			String completeName[] = managerMb.getText().split(" ");
			String firstName = completeName[0];
			String lastName = completeName[1];
			
			
			Statement stmt = null;
			String queryShop = "Insert into Shop (Address,City,Zip_Code,Email) values ('"
					+ addressTf.getText()
					+ "','" + cityTf.getText()
					+ "','" + zipCodeTf.getText()
					+ "','ouatelse" + cityTf.getText() + "@gmail.com');";
			//we need to add the shopManager
			String querySalaried = "Insert into Is_Salaried values((Select Code_Person From Person Where LastName like '"
					+ lastName + "' "
					+ "and FirstName like '" + firstName
					+ "'),(Select Code_Shop From Shop Where Address like '" + addressTf.getText() +"'));";
			String updateTable = "Update Occupy Inner Join Salaried On Salaried.Code_Person = Occupy.Code_Person "
					+ "Inner Join Person On Person.Code_Person = Salaried.Code_Person "
					+ "Set Code_Situation = (select Code_Situation From Situation where Situation_Label like 'ShopManager') "
					+ "Where Person.LastName like '" + lastName + "' and Person.FirstName like '" + firstName + "';";
			try {
				stmt = Connection_db.getConnection().createStatement();
				stmt.executeUpdate(queryShop);
				stmt.executeUpdate(querySalaried);
				stmt.executeUpdate(updateTable);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cancel();
		}
		
	}
	
	public boolean validation(){
		boolean validate = true;
		if(cityTf.getText() == null || cityTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Ville ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //first name verification
	    else if(addressTf.getText() == null || addressTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Adresse ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
	    
	    //phone number verification
	    else if(zipCodeTf.getText() == null || zipCodeTf.getText().trim().isEmpty()){
	        Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Le champ Code Postal ne doit pas être vide");
	        fail.showAndWait();
	        validate = false;
	    }
		
	    else if(managerMb.getText().equals("RESPONSABLE")){
	    	Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Vous devez choisir un responsable au magasin");
	        fail.showAndWait();
	        validate = false;
	    }
	    else {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setHeaderText("Succès");
	        alert.setContentText("Magasin créé avec succès!");
	        alert.showAndWait();
	    }
		return validate;	
	}
	
	public void enterPressed(ActionEvent ae){
		add();
	}
	
}
