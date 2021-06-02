package com.nit.jdbc;

/* Note: Eclipse IDE is still not supporting Console class .. so use Scanner support in Eclipse IDE
 * if want to avoid echoing of password on console monitor then we need to use "Console.readPassword()" method as shown below */

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginApp1 {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Console cons = null;
		try {
			//Read Inputs
			cons = System.console(); //java.io.Console does not work in Eclipse IDE
			String user=null,pass=null;
			if(cons!=null) {
				System.out.println("Enter Login Username: ");
				user = cons.readLine();  //gives anil babu
				System.out.println("Enter Login Password: ");
				pass = new String(cons.readPassword());  //gives rao rao  
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
		}//finally
	}//main
}//class