package com.nit.jdbc;

/* Write a JDBC App to hike emp salary by given percentage for the employees whose salary 
 * is in the given range (start range to end range)
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Assig1 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		try {
			//Reading input values
			sc = new Scanner(System.in);
			int startRange=0,endRange=0;
			int increasePercentage=0;
			if(sc!=null) {
				System.out.print("Enter start range of salary: ");
				startRange = sc.nextInt();  //2000
				System.out.println("Enter end range of salary: ");
				endRange = sc.nextInt();  //3000
				System.out.println("Enter hike percentage to increase salary: ");
				increasePercentage = sc.nextInt();  //20
			}

			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle:jdbc:driver:OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create Statement object
			if(con!=null)
				st = con.createStatement();

			//Prepare SQL Query
			//SELECT EMPNO,ENAME,JOB,SAL+(SAL*20/100) FROM EMP WHERE SAL>=2000 AND SAL<=3000
			String query = "SELECT EMPNO,ENAME,JOB,SAL+(SAL*"+increasePercentage+"/100) FROM EMP WHERE SAL>="+startRange+"AND SAL<="+endRange;
			System.out.println(query);

			//Send and execute SQL query
			if(st!=null)
				rs = st.executeQuery(query);

			//Process the ResultSet(0 or more records)
			if(rs!=null) {
				boolean flag = false;
				while(rs.next()) {
					flag = true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
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
