package application.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_app;
import application.controller.Connection_db;

public class Shop {

	int code_shop;
	String address;
	String city;
	int zip_code;
	String email;

	public Shop(int code_shop, String address, String city, int zip_code, String email){
		this.code_shop = code_shop;
		this.address = address;
		this.city = city;
		this.zip_code = zip_code;
		this.email = email;
	}
	
	public int getCode_shop() {
		return code_shop;
	}

	public void setCode_shop(int code_shop) {
		this.code_shop = code_shop;
	}

	public static int getStock(int code_shop, int code_product){
		Statement stmt = null;
		String queryStock = "select * from Stock inner join Shop on Shop.Code_Shop = Stock.Code_Shop inner join Product on Product.Code_Product = Stock.Code_Product where Shop.Code_Shop like '" + code_shop + "' and Product.Code_Product like '" + code_product + "';";
		
		int returnStock = 0;
		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(queryStock);
			
			while(res.next()){
				returnStock = res.getInt("Quantity");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return returnStock;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
