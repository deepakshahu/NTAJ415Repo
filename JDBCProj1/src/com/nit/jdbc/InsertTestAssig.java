package com.nit.jdbc;

/* Write a JDBC App to insert into EMP DB table only 4 columns(eno,ename,job,sal) by collecting from end user */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTestAssig {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con =  null;
		Statement st = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			int eno=0;
			String name=null,job=null;
			float sal = 0.0f;
			if(sc!=null) {
				System.out.println("Enter employee number: ");
				eno=sc.nextInt();  //gives 8888
				System.out.println("Enter employee name: ");
				name=sc.next().toUpperCase();  //gives Deepak
				System.out.println("Enter employee job: ");
				job=sc.next().toUpperCase();  //gives Programmer
				System.out.println("Enter employee salary: ");
				sal=sc.nextFloat();  //gives 6000
			}//if
			
			//Convert input values as required for the SQL Query
			name="'"+name+"'";  //gives 'Deepak'
			job="'"+job+"'";  //gives 'Programmer';
			
			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create Statement object
			if(con!=null)
			st = con.createStatement();
			
			//Prepare SQL Query
			//insert into emp(empno,ename,job,sal) values(8888,'Deepak','Programmer',6000)
			String query="INSERT INTO EMP(EMPNO,ENAME,JOB,SAL) VALUES ("+eno+","+name+","+job+","+sal+")";
			System.out.println(query);
			
			//Send and execute SQL query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);
			
			//Process the Result
			if(count==0)
				System.out.println("Records not inserted");
			else
				System.out.println("Records inserted");
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()==1)
				System.out.println("Duplicates can not be inserted to PK columns");
			if(se.getErrorCode()==1400)
				System.out.println("Null can not be inserted to PK column");
			if(se.getErrorCode()>=900 && se.getErrorCode()<=999)
				System.out.println("Invalid column name or table name or SQL Keywords");
			else if(se.getErrorCode()==12899)
				System.out.println("Do not insert more than col size data to sname,sadd cols");
			System.out.println("Problem in record insertion...");
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