package application.controller.salesman;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_app;
import application.controller.Connection_db;
import application.util.Capsule;
import application.util.CoffeeMaker;
import application.util.Product;
import application.util.Shop;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;

public class Salesman {
	@FXML
	private Button shoppingCart;

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
	private TableColumn capsule_stock_column;

	@FXML
	private TableColumn cm_name_column;

	@FXML
	private TableColumn cm_desc_column;

	@FXML
	private TableColumn cm_price_column;

	@FXML
	private TableColumn cm_stock_column;
	
	@FXML
	private TextField searchBar;
	
	private static Shop shopOfEmployee;

	@FXML
	public void initialize(){
		loadShop();
		list();			
	}
	
	public void loadShop(){
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
			
				capsule_stock_column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Capsule, String>, ObservableValue<String>>(){

					@Override
					public ObservableValue<String> call(CellDataFeatures<Capsule, String> param) {
						if(param.getValue() != null){
							return new SimpleStringProperty(String.valueOf(param.getValue().getStock(shopOfEmployee.getCode_shop())));
						}
						else{
							return new SimpleStringProperty("nul");
						}
					}
					
				});
				
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
				
				cm_stock_column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CoffeeMaker, String>, ObservableValue<String>>(){

					@Override
					public ObservableValue<String> call(CellDataFeatures<CoffeeMaker, String> param) {
						if(param.getValue() != null){
							return new SimpleStringProperty(String.valueOf(param.getValue().getStock(shopOfEmployee.getCode_shop())));
						}
						else{
							return new SimpleStringProperty("nul");
						}
					}
					
				});

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

	public void shoppingCart(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/ShoppingCart.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCart(){
		boolean isAlreadyInCart = false;
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Succès");
        
        Alert alertFail = new Alert(AlertType.INFORMATION);
        alertFail.setHeaderText("Erreur");

		if(capsuleTab.isSelected()){
			//is there enough of the product in stock ?
			if(capsuleTable.getSelectionModel().getSelectedItem().getStock(shopOfEmployee.getCode_shop()) > 0){
				for(Product p : ShoppingCart.getProductsCart()){
					if(p.getCode_product() == capsuleTable.getSelectionModel().getSelectedItem().getCode_product()){
						isAlreadyInCart = true;
					}
				}
				
				if(isAlreadyInCart){
					capsuleTable.getSelectionModel().getSelectedItem().setQuantityCart(capsuleTable.getSelectionModel().getSelectedItem().getQuantityCart() + 1);	
				}
				else{
					capsuleTable.getSelectionModel().getSelectedItem().setQuantityCart(capsuleTable.getSelectionModel().getSelectedItem().getQuantityCart() + 1);
					ShoppingCart.getProductsCart().add(capsuleTable.getSelectionModel().getSelectedItem());	
				}
				
		        alert.setContentText("Produit : " + capsuleTable.getSelectionModel().getSelectedItem().getLabel() + " ajouté au panier avec succès");
		        alert.showAndWait();
			}
			else{
				alertFail.setContentText("Il n'y a pas assez de ce produit en stock !");
				alertFail.showAndWait();
			}
			
		}else if(coffeeMakerTab.isSelected()){
			if(coffeeMakerTable.getSelectionModel().getSelectedItem().getStock(shopOfEmployee.getCode_shop()) > 0){
				for(Product p : ShoppingCart.getProductsCart()){
					if(p.getCode_product() == coffeeMakerTable.getSelectionModel().getSelectedItem().getCode_product()){
						isAlreadyInCart = true;
					}
				}
				
				if(isAlreadyInCart){
					coffeeMakerTable.getSelectionModel().getSelectedItem().setQuantityCart(coffeeMakerTable.getSelectionModel().getSelectedItem().getQuantityCart() + 1);	
				}
				else{
					coffeeMakerTable.getSelectionModel().getSelectedItem().setQuantityCart(coffeeMakerTable.getSelectionModel().getSelectedItem().getQuantityCart() + 1);	
					ShoppingCart.getProductsCart().add(coffeeMakerTable.getSelectionModel().getSelectedItem());	
				}
				
		        alert.setContentText("Produit : " + coffeeMakerTable.getSelectionModel().getSelectedItem().getLabel() + " ajouté au panier avec succès");
		        alert.showAndWait();
			}else{
				alertFail.setContentText("Il n'y a pas assez de ce produit en stock !");
				alertFail.showAndWait();
			}
			
		}
		
	}

	public static Shop getShopOfEmployee() {
		return shopOfEmployee;
	}

	public static void setShopOfEmployee(Shop shopOfEmployee) {
		Salesman.shopOfEmployee = shopOfEmployee;
	}
}
