package com.nit.jdbc;

/* Write a JDBC App to insert record into Student DB table by collecting inputs from end user */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertTestWithMySQL {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con =  null;
		Statement st = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			int no=0;
			String name=null,addrs=null;
			float avg = 0.0f;
			if(sc!=null) {
				System.out.println("Enter student number: ");
				no=sc.nextInt();  //gives 101
				System.out.println("Enter student name: ");
				name=sc.next();  //gives harsh
				System.out.println("Enter student address: ");
				addrs=sc.next();  //gives mumbai
				System.out.println("Enter student avg: ");
				avg=sc.nextFloat();  //gives 56.77
			}//if
			
			//Convert input values as required for the SQL Query
			name="'"+name+"'";  //gives 'harsh'
			addrs="'"+addrs+"'";  //gives 'hyd';
			
			//Register JDBC driver by loading JDBC driver class
			//Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:mysql:///NTAJ415DB","root","root");
			
			//Create Statement object
			if(con!=null)
			st = con.createStatement();
			
			//Prepare SQL Query
			//insert into student values(101,'harsh','mumbai',56.77)
			String query="INSERT INTO STUDENT VALUES ("+no+","+name+","+addrs+","+avg+")";
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