package tests.PurchasingManager;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import application.controller.Connection_db;

public class TestPurchasingManager {

	@Test
	public final void testProductAddingCapsule(){
		Statement stmt = null;
		String queryProduct = "Insert into Product (Code_Product,Label,Price,Description) values ('999','CapsuleTest','6','La capsule de test');";
		String queryCapsule = "Insert into Cap values ((Select Code_Product From Product Where Label like 'CapsuleTest' ),50);";
		String queryStock = "Call product_in_all_shop((Select Code_Product From Product Where Label like 'CapsuleTest'),50);";
		
		try {
			stmt = Connection_db.getConnection().createStatement();
			stmt.executeUpdate(queryProduct);
			stmt.executeUpdate(queryCapsule);
			stmt.executeUpdate(queryStock);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "Select Code_Product from Cap Where Code_Product = '999';";
		
		try {
			ResultSet res = stmt.executeQuery(query);
			res.next();
			assertEquals(res.getString("Code_Product"), "999");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String queryDeleteStock= "Delete From Stock Where Code_Product = '999'; ";
		String queryDeleteCapsule= "Delete From Cap Where Code_Product = '999'; ";
		String queryDeleteProduct = "Delete From Product Where Label = 'CapsuleTest'; ";
		
		try {
			stmt.executeUpdate(queryDeleteStock);
			stmt.executeUpdate(queryDeleteCapsule);
			stmt.executeUpdate(queryDeleteProduct);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public final void testProductAddingMachine(){
		Statement stmt = null;
		String queryProduct = "Insert into Product (Code_Product,Label,Price,Description) values ('777','MachineTest','59','La Machine de test');";
		String queryMachine = "Insert into Cofee_Machine values ((Select Code_Product From Product Where Label like 'MachineTest' ));";
		String queryStock = "Call product_in_all_shop((Select Code_Product From Product Where Label like 'MachineTest'),10);";
		
		try {
			stmt = Connection_db.getConnection().createStatement();
			stmt.executeUpdate(queryProduct);
			stmt.executeUpdate(queryMachine);
			stmt.executeUpdate(queryStock);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "Select Code_Product from Cofee_Machine Where Code_Product = '777';";
		
		try {
			ResultSet res = stmt.executeQuery(query);
			res.next();
			assertEquals(res.getString("Code_Product"), "777");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String queryDeleteStock= "Delete From Stock Where Code_Product = '777'; ";
		String queryDeleteMachine= "Delete From Cofee_Machine Where Code_Product = '777'; ";
		String queryDeleteProduct = "Delete From Product Where Label = 'MachineTest'; ";
		
		try {
			stmt.executeUpdate(queryDeleteMachine);
			stmt.executeUpdate(queryDeleteStock);
			stmt.executeUpdate(queryDeleteProduct);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
