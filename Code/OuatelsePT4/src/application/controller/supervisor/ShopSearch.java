package application.controller.supervisor;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import application.util.Employee;
import application.util.Person;
import application.util.Shop;
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

public class ShopSearch {

	@FXML
	private Button back;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private TextField searchBar;
	
	@FXML
	private Button refresh;
	
	@FXML
	private TableView<Shop> tableShop;
	
	private final ObservableList<Shop> dataShop = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn shopAddress;
	
	@FXML
	private TableColumn cityName;
	
	@FXML
	private TableColumn managerName;

	public void initialize(){
		search();
	}
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/supervisor/HomeSupervisor.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void search(){	
		//Removing
		dataShop.clear();
		tableShop.setItems(dataShop);
		
		Statement stmt = null;
		String query = "select * from Shop where Shop.City like '%" + searchBar.getText() + "%'";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				//definition of columns
				//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
				shopAddress.setCellValueFactory(new PropertyValueFactory<Shop,String>("address"));
				cityName.setCellValueFactory(new PropertyValueFactory<Shop,String>("city"));
				//managerName.setCellValueFactory(new PropertyValueFactory<Shop,String>("responsable"));
				
				dataShop.add(new Shop(res.getInt("Code_Shop"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getString("Email")));
				tableShop.setItems(dataShop);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void list(){
		dataShop.clear();
		tableShop.setItems(dataShop);
		
		searchBar.setText("");
		
		Statement stmt = null;
		String query = "select * from Shop";
		
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				//definition of columns
				//inspired by : http://www.java2s.com/Code/Java/JavaFX/AddnewrowtoTableView.htm
				shopAddress.setCellValueFactory(new PropertyValueFactory<Shop,String>("address"));
				cityName.setCellValueFactory(new PropertyValueFactory<Shop,String>("city"));
				managerName.setCellValueFactory(new PropertyValueFactory<Shop,String>("responsable"));
				
				dataShop.add(new Shop(res.getInt("Code_Shop"), res.getString("Address"), res.getString("City"), res.getInt("Zip_Code"), res.getString("Email")));
				tableShop.setItems(dataShop);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
}
