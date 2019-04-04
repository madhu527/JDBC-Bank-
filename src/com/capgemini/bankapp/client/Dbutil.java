package com.capgemini.bankapp.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class Dbutil {
	 
	private static String driveClassName = "com.mysql.jdbc.Driver";
	private static String dburl = "jdbc:mysql://localhost:3306/bankappdb";
	private static String username = "root";
	private static String password = "root";
	static Connection connection;
	static final Logger logger = Logger.getLogger(Dbutil.class);

	public static Connection getConnection() {      

		try {
			Class.forName(driveClassName); 
			if (connection == null) {

				connection = DriverManager.getConnection(dburl, username, password);
				connection.setAutoCommit(false);
			}

		} catch (ClassNotFoundException e) {
			System.out.println("Driver class not found..");

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return connection;

	}
	public static void commit() {
		try {
   
			if(connection !=null)
				connection.commit();
		
	}
		catch (SQLException e) {
			logger.error("SQLException e");
		}
	 
	}

	public static void rollback() {
		try {
   
			if(connection !=null)
				connection.rollback();
		
	}
		catch (SQLException e) {
	logger.error("SQLException e");
	}
	}

	/*
	 * static { try { File propertiesFile = new File("dbconfig.properties");
	 * FileReader reader = new FileReader(propertiesFile);
	 * 
	 * Properties properties = new Properties(); properties.load(reader);
	 * reader.close(); driveClassName = properties;
	 * 
	 * dburl = properties.getProperty("dburl"); username =
	 * properties.getProperty("username"); password =
	 * properties.getProperty("password"); } catch (IOException e) {
	 * e.printStackTrace(); } }
	 */
}
		
			
		
			 
		 
		 