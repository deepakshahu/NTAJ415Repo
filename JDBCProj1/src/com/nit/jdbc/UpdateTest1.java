package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateTest1 {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			String desg1=null,desg2=null,desg3=null;
			float hike_percentage=0.0f;
			if(sc!=null) {
				System.out.println("Enter employee desg1: ");
				desg1=sc.next().toUpperCase();
				System.out.println("Enter employee desg2: ");
				desg2=sc.next().toUpperCase();
				System.out.println("Enter employee desg3: ");
				desg3=sc.next().toUpperCase();
				System.out.println("Enter salary hike percentage: ");
				hike_percentage=sc.nextFloat();
			}//if
			
			//Convert input values as required for the SQL Query
			desg1="'"+desg1+"'";  //gives 'CLERK'
			desg2="'"+desg2+"'";  //gives 'MANAGER'
			desg3="'"+desg3+"'";  //gives 'ANALYST'
			
			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement
			if(con!=null)
				st=con.createStatement();
			
			//Prepare SQL Query
			//update emp set sal=sal+(sal*10/100) where job in('CLERK','MANAGER','ANALYST')
			String query = "UPDATE EMP SET SAL=SAL+(SAL*"+hike_percentage/100+") WHERE JOB IN ("+desg1+","+desg2+","+desg3+")";
			System.out.println(query);
			
			//Send and execute SQL Query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);
			
			//Process the Result
			if(count==0) 
				System.out.println("No records found for updation");
				else
					System.out.println("No of records that are effected: "+count);
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()>=900 && se.getErrorCode()<=999)
				System.out.println("Invalid column name or table name or SQL keywords");
			else if(se.getErrorCode()==12899)
				System.out.println("Do not insert more than one col size data to sname, sadd cols");
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