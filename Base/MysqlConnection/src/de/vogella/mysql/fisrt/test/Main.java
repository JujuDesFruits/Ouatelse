package de.vogella.mysql.fisrt.test;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
//	public static void main(String[] args) throws Exception {
//		MySQLAccess dao = new MySQLAccess();
//		dao.readDataBase();
//	}
	public static void main(String[] args)  {
		String url = "jdbc:mysql://localhost:3306/Ouatelse_Gp4_P1";
		String username = "root";
		String password = "root";

		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}
}
