package application.controller.supervisor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;

import application.controller.Connection_db;
import application.util.Employee;
import application.util.Person;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class EmployeeDetails {

	private static int code_actual_employee;
	
	@FXML
	private Button back;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private Text employeeCode;
	
	@FXML
	private Text employeeLastName;
	
	@FXML
	private Text employeeFirstName;
	
	@FXML
	private Text employeePhone;
		
	@FXML
	private Text employeeAddress;
	
	@FXML
	private Text employeeCity;
	
	@FXML
	private Text employeeZipCode;
	
	@FXML
	private Text employeeShop;
	
	@FXML
	private Text employeeSituation;
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/SearchEmployee.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		Statement stmt = null;
		String query = "select * from Salaried inner join Person on Salaried.Code_Person = Person.Code_Person where Person.Code_Person like '" + code_actual_employee + "'";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				employeeCode.setText(String.valueOf(code_actual_employee));
				employeeLastName.setText(res.getString("LastName"));
				employeeFirstName.setText(res.getString("FirstName"));
				employeePhone.setText(String.valueOf(res.getInt("Phone")));
				employeeAddress.setText(res.getString("Address"));
				employeeCity.setText(res.getString("City"));
				employeeZipCode.setText(String.valueOf(res.getInt("Zip_Code")));
				employeeSituation.setText(Employee.getSituation(code_actual_employee));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
		
	public static int getCode_actual_employee() {
		return code_actual_employee;
	}


	public static void setCode_actual_employee(int code_actual_employee) {
		EmployeeDetails.code_actual_employee = code_actual_employee;
	}
	
	public void holidayDetails(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/Holiday.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
