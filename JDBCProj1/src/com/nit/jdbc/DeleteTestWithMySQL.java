//DeleteTestWithMySQL.java
package com.nit.jdbc;

/* Write a JDBC App to delete student records based given city name(sadd) */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DeleteTestWithMySQL {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			String city=null;
			if(sc!=null)
				System.out.println("Enter Student address(city name): ");
			city=sc.next();  //gives hyd

			//Convert input values as required for the sql query
			city="'"+city+"'"; //gives 'hyd'

			//Load JDBC Driver class
			//Class.forName("com.mysql.cj.jdbc.Driver");

			//Establish the Connection
			con = DriverManager.getConnection("jdbc:mysql:///NTAJ415DB","root","root");

			//Create JDBC Statement obj
			if(con!=null)
				st = con.createStatement();

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