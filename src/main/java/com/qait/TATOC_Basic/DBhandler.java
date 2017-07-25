package com.qait.TATOC_Basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBhandler {

	
	public ResultSet DBhandler() throws ClassNotFoundException, SQLException
	{
		
		// Load mysql jdbc driver
				Class.forName("com.mysql.jdbc.Driver");
				// Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

				String dbUrl = "jdbc:mysql://10.0.1.86:3306/tatoc";

				// Database Username
				String username = "tatocuser";
				// Database Password
				String password = "tatoc01";

				// Query to Execute
				//String query = "select *  from identity;";
				String query1 = "select *  from credentials;";
				// Create Connection to DB
				Connection con = DriverManager.getConnection(dbUrl, username, password);

				// Create Statement Object
				Statement stmt = con.createStatement();

				// Execute the SQL Query. Store results in ResultSet
				
				//ResultSet r = stmt.executeQuery(query);
				ResultSet r1 = stmt.executeQuery(query1);
				return r1;
	}
	
}
