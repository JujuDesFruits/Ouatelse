package application.controller.purchasingManager;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;
import application.util.Capsule;
import application.util.CoffeeMaker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PurchasingManager {
	@FXML
	private Button addProduct;

	@FXML
	private AnchorPane background;
	
	@FXML
	private Tab capsuleTab;

	@FXML
	private Tab coffeeMakerTab;
	
	@FXML 
	private TableView<Capsule> capsuleTable;

	private final ObservableList<Capsule> dataCapsule = FXCollections.observableArrayList();
	
	@FXML
	private TableView<CoffeeMaker> coffeeMakerTable;
	
	private final ObservableList<CoffeeMaker> dataCoffeeMaker = FXCollections.observableArrayList();

	@FXML
	private TableColumn capsule_name_column;

	@FXML
	private TableColumn capsule_desc_column;

	@FXML
	private TableColumn capsule_price_column;

	@FXML
	private TableColumn cm_name_column;

	@FXML
	private TableColumn cm_desc_column;

	@FXML
	private TableColumn cm_price_column;
	
	@FXML
	private TextField searchBar;
	
	public void add(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/purchasingManager/ProductAdding.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize(){
		list();			
	}

	public void list(){
		dataCapsule.clear();
		dataCoffeeMaker.clear();
		capsuleTable.setItems(dataCapsule);
		coffeeMakerTable.setItems(dataCoffeeMaker);
		
		//fill capsule grid
		Statement stmtCap = null;
		String queryCap = "select * from Cap inner join Product on Cap.Code_Product = Product.Code_Product ";

		try{
			stmtCap = Connection_db.getConnection().createStatement();
			ResultSet res = stmtCap.executeQuery(queryCap);

			while(res.next()){				
				capsule_name_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("label"));
				capsule_desc_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("description"));
				capsule_price_column.setCellValueFactory(new PropertyValueFactory<Capsule,Integer>("price"));

				dataCapsule.add(new Capsule(res.getInt("Code_Product"), res.getString("Label"), res.getInt("Price"), res.getString("Description")));
				capsuleTable.setItems(dataCapsule);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		//fill coffeeMaker grid
		Statement stmtCM = null;
		String queryCM = "select * from Cofee_Machine inner join Product on Cofee_Machine.Code_Product = Product.Code_Product";

		try{
			stmtCM = Connection_db.getConnection().createStatement();
			ResultSet res = stmtCM.executeQuery(queryCM);

			while(res.next()){
				cm_name_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("label"));
				cm_desc_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("description"));
				cm_price_column.setCellValueFactory(new PropertyValueFactory<Capsule,Integer>("price"));

				dataCoffeeMaker.add(new CoffeeMaker(res.getInt("Code_Product"), res.getString("Label"), res.getInt("Price"), res.getString("Description")));
				coffeeMakerTable.setItems(dataCoffeeMaker);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void search(){
		dataCapsule.clear();
		dataCoffeeMaker.clear();
		capsuleTable.setItems(dataCapsule);
		coffeeMakerTable.setItems(dataCoffeeMaker);
		
		//fill capsule grid
		Statement stmtCap = null;
		String queryCap = "select * from Cap inner join Product on Cap.Code_Product = Product.Code_Product where Product.Label like '%" + searchBar.getText() + "%'";

		try{
			stmtCap = Connection_db.getConnection().createStatement();
			ResultSet res = stmtCap.executeQuery(queryCap);

			while(res.next()){				
				capsule_name_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("label"));
				capsule_desc_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("description"));
				capsule_price_column.setCellValueFactory(new PropertyValueFactory<Capsule,Integer>("price"));

				dataCapsule.add(new Capsule(res.getInt("Code_Product"), res.getString("Label"), res.getInt("Price"), res.getString("Description")));
				capsuleTable.setItems(dataCapsule);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		//fill coffeeMaker grid
		Statement stmtCM = null;
		String queryCM = "select * from Cofee_Machine inner join Product on Cofee_Machine.Code_Product = Product.Code_Product where Product.Label like '%" + searchBar.getText() + "%'";

		try{
			stmtCM = Connection_db.getConnection().createStatement();
			ResultSet res = stmtCM.executeQuery(queryCM);

			while(res.next()){
				cm_name_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("label"));
				cm_desc_column.setCellValueFactory(new PropertyValueFactory<Capsule,String>("description"));
				cm_price_column.setCellValueFactory(new PropertyValueFactory<Capsule,Integer>("price"));

				dataCoffeeMaker.add(new CoffeeMaker(res.getInt("Code_Product"), res.getString("Label"), res.getInt("Price"), res.getString("Description")));
				coffeeMakerTable.setItems(dataCoffeeMaker);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}

}
