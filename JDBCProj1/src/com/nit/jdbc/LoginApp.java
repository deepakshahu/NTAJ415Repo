package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class LoginApp {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
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

			//Convert input values as required for the SQL Query
			user="'"+user+"'";  //gives 'anil babu'
			pass="'"+pass+"'";  //gives 'rao rao'

			//Load JDBC driver class(optional)
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create JDBC Statement object
			if(con!=null) 
				st=con.createStatement();

			//Prepare SQL Query
			//select count(*) from irctc_tab where uname='anil babu' and pwd='rao rao'
			String query = "SELECT COUNT(*) FROM IRCTC_TAB WHERE UNAME="+user+"AND PWD="+pass;
			System.out.println(query);

			//Send and execute the SQL query in DB s/w
			if(st!=null)
				rs=st.executeQuery(query);

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