package application.controller.salesman;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.controller.Connection_db;
import application.util.Capsule;
import application.util.Client;
import application.util.Product;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ShoppingCart {
	@FXML
	private Button back;
	
	@FXML
	private Button client;
	
	@FXML
	private Button command;
	
	@FXML
	private AnchorPane background;
	
	@FXML
	private Label totalPrice;
	
	@FXML
	private Label clientNameLabel;
	
	@FXML
	private TableView productCartTable;
	
	private final ObservableList<Product> dataProduct = FXCollections.observableArrayList();
	
	@FXML
	private TableColumn productNameCol;

	@FXML
	private TableColumn unitPriceCol;
	
	@FXML
	private TableColumn quantityCol;
	
	@FXML
	private TableColumn priceCol;
	
	private static ArrayList<Product> productsCart = new ArrayList<Product>();
	
	private static Client clientCart;
	
	@FXML
	public void initialize(){
		//Customer data
		if(clientCart != null){
			clientNameLabel.setText(clientCart.getLastName().toUpperCase() + " " + clientCart.getFirstName());
		}
		
		//Product data
		dataProduct.clear();
		productCartTable.setItems(dataProduct);
		
		int totalPriceValue = 0;
		
		for(Product p : productsCart){
			productNameCol.setCellValueFactory(new PropertyValueFactory<Product,String>("label"));
			unitPriceCol.setCellValueFactory(new PropertyValueFactory<Product,Integer>("price"));
			quantityCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("quantityCart"));
			
			dataProduct.add(p);
			productCartTable.setItems(dataProduct);
			
			totalPriceValue += (p.getPrice() * p.getQuantityCart());
		}
		
		totalPrice.setText(String.valueOf(totalPriceValue));
		
	}
	
	public void back(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/Salesman.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void client(){
		try {
			AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/SearchClient.fxml"));
			background.getChildren().setAll(newPane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addCommand(){
		if(clientCart != null){
			//withdrawing of the products from the stock
			try {
				//the quantity in stock has been checked before, when adding the product in the cart
				for(Product p : productsCart){
					String queryUpdateStock = "update Stock set Quantity = "
							+ (p.getStock(Salesman.getShopOfEmployee().getCode_shop()) - p.getQuantityCart()) + " where Code_Shop = (select Code_Shop from Shop where Address like '"
							+ Salesman.getShopOfEmployee().getAddress() +"') and Code_Product = (select Code_Product from Product where Label like '"
							+ p.getLabel() +"');";
					Statement stmt = Connection_db.getConnection().createStatement();
					
					stmt.executeUpdate(queryUpdateStock);
				
				}
				
				AnchorPane newPane = FXMLLoader.load(getClass().getClassLoader().getResource("application/interfaces/salesman/CommandConfirmation.fxml"));
				background.getChildren().setAll(newPane);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//adding the command in the database
			String queryCommand = "Insert into Command (Date, Code_Person) values ('24/12/2008', "
					+ "(Select Client.Code_Person From Client Inner join Person On Person.Code_Person = Client.Code_Person Where Person.FirstName like "
					+ "'" + clientCart.getFirstName() +"' and Person.LastName like '" + clientCart.getLastName() +"'));";
		
			try {
				Statement stmt = Connection_db.getConnection().createStatement();
				stmt.executeUpdate(queryCommand);
				
				for(Product p : productsCart){
					String queryContain = "Insert into Contain values ((Select Code_Command from Command Inner Join Client On Client.Code_Person=Command.Code_Person Inner join Person On Person.Code_Person=Client.Code_Person Where Person.FirstName like "
							+ "'" + clientCart.getFirstName() + "' and Person.LastName like '" + clientCart.getLastName() +"'),"
							+ " (Select Code_Product From Product where Label like '" + p.getLabel() + "'), " + p.getQuantityCart() + ");";
					stmt.executeUpdate(queryContain);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else{
			Alert fail= new Alert(AlertType.INFORMATION);
	        fail.setHeaderText("Erreur");
	        fail.setContentText("Vous n'avez pas renseigné de client !");
	        fail.showAndWait();
		}
		
		
	}

	public static ArrayList<Product> getProductsCart() {
		return productsCart;
	}

	public static void setProductsCart(ArrayList<Product> productsCart) {
		ShoppingCart.productsCart = productsCart;
	}

	public static Client getClientCart() {
		return clientCart;
	}

	public static void setClientCart(Client clientCart) {
		ShoppingCart.clientCart = clientCart;
	}
}
