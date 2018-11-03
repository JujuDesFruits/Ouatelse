package tests.salesman;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import application.controller.Connection_db;

public class TestSalesman {
	
	@Test
	public final void testAddClient(){
		Statement stmt = null;
		String queryPerson = "Insert into Person (Code_Person, LastName,FirstName,Address,City,Zip_Code,Phone) values ('999','Test','John','50, Rue du Test','TestCity','66600','666');";
		String queyClient = "Insert into Client values ((Select Code_Person From Person Where Person.LastName like 'Test' and Person.FirstName like 'John'));"; 
		try {
			stmt = Connection_db.getConnection().createStatement();
			stmt.executeUpdate(queryPerson);
			stmt.executeUpdate(queyClient);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query = "Select Code_Person from Client Where Code_Person = '999';";

		try {
			ResultSet res = stmt.executeQuery(query);
			res.next();
			assertEquals(res.getString("Code_Person"), "999");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String queryDeleteClient= "Delete From Client Where Code_Person = '999'; ";
		String queryDeletePerson = "Delete From Person Where LastName = 'Test'; ";

		try {
			stmt.executeUpdate(queryDeleteClient);
			stmt.executeUpdate(queryDeletePerson);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
