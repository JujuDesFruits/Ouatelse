package application.controller;

import java.sql.Connection;
import java.sql.DriverManager;

//This class is a singleton which can be used 
//everywhere in the application this way :
//Connection_db.getConnection().method();
public class Connection_db {
	static Connection SqlConnection = null;
	
	public static Connection getConnection(){
		if(SqlConnection != null){
			return SqlConnection;
		}
		
		String url = "jdbc:mysql://localhost:3306/Ouatelse_Gp4_P1";
		String username = "root";
		String password = "";
		
		return getConnection(url, username, password);
	}
	
	private static Connection getConnection(String url, String username, String password){
		try{	
			SqlConnection = DriverManager.getConnection(url,username,password);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return SqlConnection;
	}

}
