package application.controller.salesman;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import application.util.Client;
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

public class SearchClient {
	@FXML
	private Button back;
	
	@FXML
	private Button addClient;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private TextField searchBar;
	
	@FXML 
	private TableView<Client> tableClient;
	
	private final ObservableList<Client> dataClient = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn last_name_column;
	
	@FXML
	private TableColumn first_name_column;
	
	public void initialize(){
		search();
	}
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/ShoppingCart.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addClient(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/AddClient.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void search(){
		dataClient.clear();
		tableClient.setItems(dataClient);
		
		Statement stmt = null;
		String query = "select * from Client inner join Person on Client.Code_Person = Person.Code_Person Where Person.LastName like '%" + searchBar.getText() + "%'";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				//definition of columns
				//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
				last_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
				first_name_column.setCellValueFactory(new PropertyValueFactory<Person,String>("lastName"));
				
				dataClient.add(new Client(res.getInt("Code_Person"),res.getString("LastName"), res.getString("FirstName"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getInt("Phone")));
				tableClient.setItems(dataClient);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void selectClient(){
		ShoppingCart.setClientCart(tableClient.getSelectionModel().getSelectedItem());
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/ShoppingCart.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void details(){
		ClientDetails.setCode_actual_customer(tableClient.getSelectionModel().getSelectedItem().getCode_person());
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/ClientDetails.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
