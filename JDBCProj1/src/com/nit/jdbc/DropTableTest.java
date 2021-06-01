//DropTableTest.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DropTableTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			String dtable=null;
			System.out.println("Enter table name which needs to be drop: ");
			dtable = sc.next();
			//Load JDBC Driver Class
			//Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create JDBC Statement object
			if(con!=null)
				st=con.createStatement();

			//Prepare SQL Query
			//drop table temp_student
			String query="DROP TABLE "+dtable;
			System.out.println(query);

			//Send and execute SQL Query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);
			System.out.println("count: "+count);

			//Process the Result
			if(count==0)
				System.out.println("DB table is dropped");
			else
				System.out.println("DB table is not dropped");
		}//try
		catch(SQLException se) {
			//se.printStackTrace();
			//We can work with error codes
			if(se.getErrorCode()==942)
				System.out.println("DB Table does not exist");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objs
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