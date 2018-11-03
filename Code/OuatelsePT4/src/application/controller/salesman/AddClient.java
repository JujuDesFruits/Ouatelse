package application.controller.salesman;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AddClient {
	@FXML
	private Button cancel;
	
	@FXML
	private Button validate;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField firstName;
	
	@FXML
	private TextField phone;
	
	@FXML
	private TextField address;
	
	@FXML
	private TextField city;
	
	@FXML
	private TextField zipCode;
	
	public void cancel(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/SearchClient.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(){
		Statement stmt = null;
		String query = "Insert into Person (LastName, FirstName, Address, City, Zip_Code, Phone) values ('" 
				+ name.getText() + "','" + firstName.getText() + "','" + address.getText() + "','" 
				+ city.getText() + "','" + zipCode.getText() + "','" + phone.getText()+"');";
		String queryClient = "Insert into Client values ((Select Code_Person From Person Where Person.LastName like '" 
				+ name.getText() + "' and Person.FirstName like '" + firstName.getText() + "'));"; 
		try {
			stmt = Connection_db.getConnection().createStatement();
			int res = stmt.executeUpdate(query);
			int resClient = stmt.executeUpdate(queryClient);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		 
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/SearchClient.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
