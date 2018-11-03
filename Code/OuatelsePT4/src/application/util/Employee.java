package application.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.controller.Connection_db;

public class Employee extends Person{

	private String situation;
	
	public Employee(int code_person, String lastName, String firstName, String address, String city, int zip_code, int phone, String situation) {
		super(code_person, lastName, firstName, address, city, zip_code, phone);
		this.setSituation(situation);
	}

	public String getSituation() {
		return situation;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}
	
	public static String getSituation(int code_person){
		Statement stmtSituation = null;
		String querySituation = "Select * from Occupy inner join Situation on Occupy.Code_Situation = Situation.Code_Situation";
		String situationLabel = null;
		
		try{
			stmtSituation = Connection_db.getConnection().createStatement();
			ResultSet res = stmtSituation.executeQuery(querySituation);
			while(res.next()){
				if(res.getInt("Code_Person") == code_person){
					situationLabel = res.getString("Situation_Label");
					break;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return situationLabel;
	}

}
