package application.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.AccountType;
import application.util.Employee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

//refactored from "Connection" to "Connection_app" to avoid fail with java.sql.Connection
public class Connection_app {

	@FXML
	private AnchorPane background;
	@FXML
	private TextField login;
	@FXML
	private PasswordField password;
	@FXML
	private Text wrongPasswordMessage;

	private AccountType ac;
	
	private static Employee actualAccount;

	public static Employee getActualAccount() {
		return actualAccount;
	}

	public static void setActualAccount(Employee actualAccount) {
		Connection_app.actualAccount = actualAccount;
	}

	public void connectionTry(){
		
		Statement stmt = null;
		String query = "select * from Salaried inner join Person on Salaried.Code_Person = Person.Code_Person Where Login like '" + login.getText() + "'";
		
		String truePassW = null;
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				actualAccount = new Employee(res.getInt("Code_Person"), res.getString("LastName"), res.getString("FirstName"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getInt("Phone"), Employee.getSituation(res.getInt("Code_Person")));
				truePassW = res.getString("Password");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		if(password.getText().equals(truePassW)){
			switch(actualAccount.getSituation()){
			case "Supervisor":
				ac = AccountType.SUPERVISOR;
				break;
			case "Salesman":
				ac = AccountType.SALESMAN;
				break;
			case "PurchasingManager":
				ac = AccountType.PURCHASING_MANAGER;
				break;
			case "ShopManager":
				ac = AccountType.SHOP_MANAGER;
				break;
			}
		}		
		else{
			wrongPasswordMessage.setVisible(true);
			login.setText("");
			password.setText("");
		}
		
		switch(ac){
		case PURCHASING_MANAGER:
			try {
				validatedConnectionPM();
			} catch (IOException e) {
				e.printStackTrace();
			}	
			break;
		case SHOP_MANAGER:
			try {
				validatedConnectionSM();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SUPERVISOR:
			try {
				validatedConnectionSupervisor();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case SALESMAN:
			try {
				validatedConnectionSalesman();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
	}

	@FXML
	public void validatedConnectionPM() throws IOException{
		AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/purchasingManager/PurchasingManager.fxml"));
		background.getChildren().setAll(newPane);
	}
	
	@FXML
	public void validatedConnectionSM() throws IOException{
		AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/ShopManager.fxml"));
		background.getChildren().setAll(newPane);
	}
	
	@FXML
	public void validatedConnectionSupervisor() throws IOException{
		AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/HomeSupervisor.fxml"));
		background.getChildren().setAll(newPane);
	}

	@FXML
	public void validatedConnectionSalesman() throws IOException{
		AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/Salesman.fxml"));
		background.getChildren().setAll(newPane);
	}
	
	@FXML
	public void enterPressed(ActionEvent ae){
		connectionTry();
	}
	
}
