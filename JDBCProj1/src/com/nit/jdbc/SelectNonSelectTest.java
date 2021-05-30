package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectNonSelectTest {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Reading input values from end user
			sc = new Scanner(System.in);
			String query = null;
			if(sc!=null) {
			System.out.println("Enter SQL Query(Select or non select): ");
			query = sc.nextLine();
			}//Read inputs
			
			//Load JDBC Driver Class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement obj
			if(con!=null)
				st=con.createStatement();
			
			//Send and execute SQL Query in DB s/w
			if(st!=null) {
				boolean flag=st.execute(query);
				System.out.println(flag);
				if(flag==true) {
					System.out.println("SELECT SQL Query executed");
					
					//gather and process the ResultSet
					rs=st.getResultSet();
					
					//Process the ResultSet obj
					if(rs!=null) {
						while(rs.next()) {
							System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
						}//While
					}//if
					
				}//if
				else {
					System.out.println("Non-Select SQL Query executed");
					//gather result
					int count=st.getUpdateCount();  //long count=st.getLargeUpdateCount();
					System.out.println("No of records that are effected: "+count);
				}//else
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		    //We can work with error codes
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objs
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