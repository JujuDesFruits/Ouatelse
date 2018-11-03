package application.controller.salesman;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ClientDetails {

	private static int code_actual_customer;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private Label code_customer;
	
	@FXML
	private Label customer_name;
	
	@FXML
	private Label customer_first_name;
	
	@FXML
	private Label customer_phone;
	
	@FXML
	private Label customer_mail;
	
	@FXML
	private Label customer_address;
	
	@FXML
	private Label customer_city;
	
	@FXML
	private Label customer_zip_code;

	@FXML
	public void initialize(){
		Statement stmt = null;
		String query = "select * from Client inner join Person on Client.Code_Person = Person.Code_Person where Person.Code_Person like '" + code_actual_customer + "'";
	
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				code_customer.setText(String.valueOf(code_actual_customer));
				customer_name.setText(res.getString("LastName"));
				customer_first_name.setText(res.getString("FirstName"));
				customer_phone.setText(String.valueOf(res.getInt("Phone")));
				customer_address.setText(res.getString("Address"));
				customer_city.setText(res.getString("City"));
				customer_zip_code.setText(String.valueOf(res.getInt("Zip_Code")));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/SearchClient.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static int getCode_actual_customer() {
		return code_actual_customer;
	}

	public static void setCode_actual_customer(int code_actual_customer) {
		ClientDetails.code_actual_customer = code_actual_customer;
	}
		
}
