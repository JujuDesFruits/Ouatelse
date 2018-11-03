package tests.shopManager;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import application.controller.Connection_db;

public class TestShopManager {
	
	@Test
	public final void testAddEmployee(){
		Statement stmt = null;
		String queryPerson = "Insert into Person (Code_Person, LastName,FirstName,Address,City,Zip_Code,Phone) values ('999','Test','John','50, Rue du Test','TestCity','66600','666');";
		String queySalaried = "Insert into Salaried values ((Select Code_Person From Person Where Person.LastName like 'Test' and Person.FirstName like 'John'),'JohnTest666','passwordtest');";
		String querySituation = "Insert into Occupy values ((Select Code_Person From Salaried Where Login like 'JohnTest666'),(Select Code_Situation From Situation Where Situation_Label like 'Supervisor'));";

		try {
			stmt = Connection_db.getConnection().createStatement();
			stmt.executeUpdate(queryPerson);
			stmt.executeUpdate(queySalaried);
			stmt.executeUpdate(querySituation);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "Select LastName from Person Where LastName = 'Test';";

		try {
			ResultSet res = stmt.executeQuery(query);
			res.next();
			assertEquals(res.getString("LastName"), "Test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String queryDeletePerson = "Delete From Person Where LastName = 'Test'; ";
		String queryDeleteSalaried = "Delete From Salaried Where Login = 'JohnTest666'; ";
		String queryDeleteOccupy = "Delete From Occupy Where Code_Person = '999'; ";
		try {
			stmt.executeUpdate(queryDeleteOccupy);
			stmt.executeUpdate(queryDeleteSalaried);
			stmt.executeUpdate(queryDeletePerson);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
