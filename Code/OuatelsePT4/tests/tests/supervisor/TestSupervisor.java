package tests.supervisor;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import application.controller.Connection_db;
import application.controller.supervisor.AddEmployee;
import application.controller.supervisor.SearchEmployee;
import application.util.Employee;
import application.util.Person;
import javafx.scene.control.cell.PropertyValueFactory;

public class TestSupervisor {

	@Test
	public final void testGetSituation(){
		//we know there is someone named "test test" whose situation is "testeur"
		int code_test = 0;

		Statement stmt = null;
		String query = "select * from Salaried inner join Person on Salaried.Code_Person = Person.Code_Person Where Person.LastName like 'Bond'";

		try{
			stmt = Connection_db.getConnection().createStatement();
			ResultSet res = stmt.executeQuery(query);
			while(res.next()){
				code_test = res.getInt("Code_Person");
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}

		String resSituation = Employee.getSituation(code_test);

		assertEquals(resSituation, "PurchasingManager");
	}

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


	@Test
	public final void testAddShop(){
		Statement stmt = null;
		String queryShop = "Insert into Shop (Code_Shop,Address,City,Zip_Code,Email) values ('999','Rue du Test','TestCity','66600','ouatelseTestCity@gmail.com');";

		try {
			stmt = Connection_db.getConnection().createStatement();
			stmt.executeUpdate(queryShop);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String query = "Select City from Shop Where Code_Shop = '999';";
		try {
			ResultSet res = stmt.executeQuery(query);
			res.next();

			assertEquals(res.getString("City"), "TestCity");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String queryDeleteShop = "Delete From Shop Where Code_Shop = '999'; ";
		
		try {
			stmt.executeUpdate(queryDeleteShop);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
