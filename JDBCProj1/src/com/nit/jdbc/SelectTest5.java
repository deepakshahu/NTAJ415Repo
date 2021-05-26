//SelectTest5.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest5 {

	public static void main(String[] args) {
		Connection con=null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Load JDBC Driver class
		    //Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement obj
			if(con!=null)
				st = con.createStatement();
			
			//Prepare SQL Query
			String query = "SELECT COUNT(*) FROM STUDENT";
			System.out.println(query);
			
			//Send and execute SQL Query
			if(st!=null)
				rs = st.executeQuery(query);
			
			//Process the ResultSet
			if(rs!=null) {
				rs.next();
			    //int count = rs.getInt(1);
			    int count = rs.getInt("COUNT(*)");
			    System.out.println("Records count in Student DB table:: "+count);
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC Objs
			try {
				if(rs!=null)
					rs.close();
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class
