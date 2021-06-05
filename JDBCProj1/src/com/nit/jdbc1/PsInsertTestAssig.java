package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PsInsertTestAssig {
	private static final String CUSTOMER_INSERT_QUERY="INSERT INTO CUSTOMERS VALUES(?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			int count = 0;
			if(sc!=null) {
				System.out.println("Enter customers count: ");
				count = sc.nextInt();
			}
			//Register JDBC driver
			//Class.forName("oracle.jdbc.driver.OracleDriver);

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create PreparedStatement object having pre-complied SQL Query
			if(con!=null)
				ps = con.prepareStatement(CUSTOMER_INSERT_QUERY);

			//Read input values from end user, set them to query param values
			//and execute the pre-compiled SQL Query for multiple times
			if(ps!=null && sc!=null) {
				for(int i=1; i<=count; ++i) {
					System.out.println("Enter "+i+" customers details");
					System.out.println("Enter customer number: ");
					int no = sc.nextInt();
					System.out.println("Enter customer first name: ");
					String firstName = sc.next();
					System.out.println("Enter customer last name: ");
					String lastName = sc.next();

					//Set each customer details as pre-compiled SQL query params
					ps.setInt(1, no);
					ps.setString(2, firstName);
					ps.setString(3, lastName);

					//Execute pre-compiled SQL Query each time
					int result = ps.executeUpdate();

					//Process execution result of pre-complied SQL Query
					if(result==0)
						System.out.println(i+" customer details are not inserted");
					else
						System.out.println(i+" customer details are inserted");
				}//for
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objs
			try {
				if(ps!=null)
					ps.close();
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