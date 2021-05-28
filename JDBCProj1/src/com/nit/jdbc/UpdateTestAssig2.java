package com.nit.jdbc;

/* Write a JDBC App to add given percentage of marks to existing avg based on the given 3 city names
 * as the addresses for student.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateTestAssig2 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		try {
			//Reading input values
			sc = new Scanner(System.in);
			String cityName1=null,cityName2=null,cityName3=null;
			float addPercentage=0.0f;
			if(sc!=null) {
				System.out.print("Enter city name 1: ");
				cityName1 = sc.next().toUpperCase();  //DELHI
				System.out.println("Enter city name 2: ");
				cityName2 = sc.next().toUpperCase();  //MUMBAI
				System.out.println("Enter city name 3: ");
				cityName3 = sc.next().toUpperCase();  //Thane
				System.out.println("Enter percentage to add in marks : ");
				addPercentage = sc.nextFloat();  //10
			}
			cityName1="'"+cityName1+"'";
			cityName2="'"+cityName2+"'";
			cityName3="'"+cityName3+"'";

			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle:jdbc:driver:OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create Statement object
			if(con!=null)
				st = con.createStatement();

			//Prepare SQL Query
			//update student set avg=avg+(avg*10/100) where sadd in('DELHI','MUMBAI','THANE')
			String query = "UPDATE STUDENT SET AVG=AVG+(AVG*"+addPercentage/100+") WHERE SADD IN("+cityName1+","+cityName2+","+cityName3+")";
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
