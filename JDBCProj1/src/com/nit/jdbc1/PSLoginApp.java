package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class PSLoginApp {
	private static final String LOGIN_QUERY="SELECT COUNT(*) FROM IRCTC_TAB WHERE UNAME=? AND PWD=?";
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//Read Inputs
			sc = new Scanner(System.in);
			String user=null,pass=null;
			if(sc!=null) {
				System.out.println("Enter Login Username: ");
				user = sc.nextLine();  //gives anil babu
				System.out.println("Enter Login Password: ");
				pass = sc.nextLine();  //gives rao rao
			}//if

			//Load JDBC driver class(optional)
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create JDBC PreparedStatement object
			if(con!=null) 
				ps=con.prepareStatement(LOGIN_QUERY);
			
			//Set values to params of pre-compiled SQL Query
			if(ps!=null) {
				ps.setString(1,user);
				ps.setString(2,pass);
			}

			//Send and execute the SQL query in DB s/w
			if(ps!=null)
				rs=ps.executeQuery();

			//Process the ResultSet Object
			if(rs!=null) {
				rs.next();  //move the cursor to first record from BFR
				int count=rs.getInt(1);  //get first cal value of that first record
				//Process the ResultSet
				if(count==0)
					System.out.println("Invalid credentials");
				else
					System.out.println("Valid credentials");
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
				if(rs!=null)
					rs.close();
			}//try
			catch(SQLException se) {
				se.printStackTrace();
			}
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