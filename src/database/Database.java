package database;

import java.sql.Connection;
import java.sql.DriverManager;

//import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

	public static Connection connectDb() {
				
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/hoteldb","root","");
			
			System.out.print(connect);
			return connect;
		}catch(Exception e) {
			e.printStackTrace();
			}
		
		return null;
	}
}