package application.controller.shopManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import application.controller.shopManager.EmployeeDetails;
import application.util.Employee;
import application.util.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class SearchEmployee {

	@FXML
	private Button back;
	
	@FXML
	private Button search;
	
	@FXML
	private Button refresh;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private TableView<Employee> tableEmployee;
	
	private final ObservableList<Employee> dataEmployee = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn last_name_column;
	
	@FXML
	private TableColumn first_name_column;
	
	@FXML
	private TableColumn situation_column;

	@FXML
	private TextField searchBar;
	
	@FXML
	public void initialize(){
		//launched when the fxml is initialized
		list();
	}
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/ShopManager.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void employeeDetails(){
		System.out.println(tableEmployee.getSelectionModel().getSelectedItem().getCode_person());
		EmployeeDetails.setCode_actual_employee(tableEmployee.getSelectionModel().getSelectedItem().getCode_person());
		
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/shopManager/EmployeeDetails.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void list(){	
		
		//Removing of all elements of the table
		dataEmployee.clear();
		tableEmployee.setItems(dataEmployee);
		
		Statement stmt = null;
		String query = "select * from Salaried inner join Person on Salaried.Code_Person = Person.Code_Person "
				+ "Inner Join Is_Salaried on Person.Code_Person = Is_Salaried.Code_Person Where Is_Salaried.Code_Shop = '" + ShopManager.getShopOfEmployee().getCode_shop() + "';";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				//definition of columns
				//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
				last_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
				first_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
				situation_column.setCellValueFactory(new PropertyValueFactory<Employee,String>("situation"));
				
				dataEmployee.add(new Employee(res.getInt("Code_Person"),res.getString("LastName"), res.getString("FirstName"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getInt("Phone"), Employee.getSituation(res.getInt("Code_Person"))));
				tableEmployee.setItems(dataEmployee);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//called when search button clicked or key typed in the searchBar
	public void search(){
		//Removing of all elements of the table
				dataEmployee.clear();
				tableEmployee.setItems(dataEmployee);
				
				Statement stmt = null;
				String query = "select * from Salaried inner join Person on Salaried.Code_Person = Person.Code_Person Inner Join Is_Salaried on Person.Code_Person = Is_Salaried.Code_Person Where Person.LastName like '" + searchBar.getText() + "%' and Is_Salaried.Code_Shop = '" + ShopManager.getShopOfEmployee().getCode_shop() + "';";
				
				try{
					stmt = Connection_db.getConnection().createStatement();
					ResultSet res = stmt.executeQuery(query);
					while(res.next()){
						//definition of columns
						//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
						last_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
						first_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
						situation_column.setCellValueFactory(new PropertyValueFactory<Employee,String>("situation"));
						
						dataEmployee.add(new Employee(res.getInt("Code_Person"),res.getString("LastName"), res.getString("FirstName"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getInt("Phone"), Employee.getSituation(res.getInt("Code_Person"))));
						tableEmployee.setItems(dataEmployee);
					}
				}
				catch(SQLException e){
					e.printStackTrace();
				}
	}

}
