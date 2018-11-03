package application.controller.shopManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_app;
import application.controller.Connection_db;
import application.util.Shop;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShopManager {

	@FXML
	private Button list;
	
	@FXML
	private Button add;
	
	@FXML
	private AnchorPane background;
	
	private static Shop shopOfEmployee;

	
	@FXML
	private Label name_shop_label;
	
	@FXML
	public void initialize(){
		
		Statement stmt = null;
		String queryShop = "Select * from Shop Inner Join Is_Salaried on Is_Salaried.Code_Shop=Shop.Code_Shop Inner Join Salaried On Salaried.Code_Person=Is_Salaried.Code_Person Inner Join Person on Person.Code_Person=Salaried.Code_Person Where Person.FirstName like '" + Connection_app.getActualAccount().getFirstName() + "' and Person.LastName like '" + Connection_app.getActualAccount().getLastName() + "';";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(queryShop);
				
			while(res.next()){
				shopOfEmployee = new Shop(res.getInt("Code_Shop"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getString("Email"));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		name_shop_label.setText("Ouatelse " + shopOfEmployee.getCity());
	}

	public void employeeList(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/SearchEmployee.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addEmployee(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/AddEmployee.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Shop getShopOfEmployee() {
		return shopOfEmployee;
	}

	public static void setShopOfEmployee(Shop shopOfEmployee) {
		ShopManager.shopOfEmployee = shopOfEmployee;
	}
	
}
