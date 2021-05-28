package com.nit.jdbc;

/* Write a JDBC App to add given percentage of marks to existing avg based on the given 3 city names
 * as the addresses for student.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Assig2 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			//Reading input values
			sc = new Scanner(System.in);
			String cityName1=null,cityName2=null,cityName3=null;
			int addPercentage=0;
			if(sc!=null) {
				System.out.print("Enter city name 1: ");
				cityName1 = sc.nextLine();  //DELHI
				System.out.println("Enter city name 2: ");
				cityName2 = sc.nextLine();  //MUMBAI
				System.out.println("Enter city name 3: ");
				cityName3 = sc.nextLine();  //Thane
				System.out.println("Enter percentage to add in marks : ");
				addPercentage = sc.nextInt();  //10
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
			//SELECT SNO,SNAME,AVG+(AVG*20/100) FROM STUDENT WHERE SADD='DELHI' OR SADD='MUMBAI' OR SADD='THANE';
			String query = "SELECT SNO,SNAME,AVG+(AVG*"+addPercentage+"/100) FROM STUDENT WHERE SADD="+cityName1+"OR SADD="+cityName2+" OR SADD="+cityName3;
			System.out.println(query);

			//Send and execute SQL query
			if(st!=null)
				rs = st.executeQuery(query);

			//Process the ResultSet(0 or more records)
			if(rs!=null) {
				boolean flag = false;
				while(rs.next()) {
					flag = true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getFloat(3));
				}//while
				if(flag==false)
					System.out.println("No Records found");
			}//if
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
