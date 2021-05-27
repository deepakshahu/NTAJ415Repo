//DeleteTest.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/* Write a JDBC App to delete student records based given city name(sadd) */

public class DeleteTest {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		try {
			//Reading inputs
			sc = new Scanner(System.in);
			String city = null;
			if(sc!=null) {
				System.out.print("Enter Student address(city name): ");
				city=sc.next(); //gives hyd
			}
			//Convert input values as required for the sql query
			city="'"+city+"'"; //gives 'hyd'
			
			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create Statement object
			if(con!=null) 
				st=con.createStatement();
			
			//Prepare SQL Query
			//delete from student where sadd='HYD'
			String query = "DELETE FROM STUDENT WHERE SADD="+city;
			System.out.println(query);
			
			//Send and execute SQL Query in DB s/w
			int count = 0;
			if(st!=null) 
				count = st.executeUpdate(query);
			
			//Process the Result
			if(count==0)
				System.out.println("No records found to delete");
			else
				System.out.println("Number of records that are effected: "+count);
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()>=900 && se.getErrorCode()<999)
				System.out.println("Invalid column names or table name or SQL keywords");
			se.printStackTrace();
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
			try {
				if(sc!=null)
					sc.close();
			}//try
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class