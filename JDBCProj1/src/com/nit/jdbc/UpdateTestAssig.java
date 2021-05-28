package com.nit.jdbc;

/* Write a JDBC App to hike emp salary by given percentage for the employees whose salary 
 * is in the given range (start range to end range)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateTestAssig {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		try {
			//Reading input values
			sc = new Scanner(System.in);
			int startRange=0,endRange=0;
			float hike_Percentage=0.0f;
			if(sc!=null) {
				System.out.print("Enter start range of salary: ");
				startRange = sc.nextInt();  //2000
				System.out.println("Enter end range of salary: ");
				endRange = sc.nextInt();  //3000
				System.out.println("Enter hike percentage to increase salary: ");
				hike_Percentage = sc.nextFloat();  //20
			}

			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle:jdbc:driver:OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create Statement object
			if(con!=null)
				st = con.createStatement();

			//Prepare SQL Query
			//update emp set sal=sal+(sal*10/100) where sal>=2000 and sal<=3000
			String query = "UPDATE EMP SET SAL=SAL+(SAL*"+hike_Percentage/100+") WHERE SAL>="+startRange+"AND SAL<="+endRange;
			System.out.println(query);

			//Send and execute SQL query
			int count = 0;
			if(st!=null)
				count = st.executeUpdate(query);

			//Process the Result
			if(count==0) 
				System.out.println("No records found for updation");
				else
					System.out.println("Number of records that are effected: "+count);
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()>=900 && se.getErrorCode()<=999)
				System.out.println("Invalid column name or table name or SQL keywords");
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
