//SelectTest8Assig.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest8Assig {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Scanner sc = null;
		try {
			//Read inputs from end user
			sc = new Scanner(System.in);
			int n=0;
			if(sc!=null) {
				System.out.print("Enter a number to get nth highest salary: ");
				n=sc.nextInt();  //Gives 2
			}
			//Load JDBC Driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement object
			if(con!=null)
				st = con.createStatement();
			
			//Prepare SQL Query
			//select * from (select ename,sal,dense_rank() over(order by sal desc) r from emp) where r=&n
			String query = "SELECT * FROM (SELECT ENAME,SAL,DENSE_RANK() OVER(ORDER BY SAL DESC) R FROM EMP) WHERE R="+n;
			System.out.println(query);
			
			//Send and execute SQL query
			if(st!=null)
				rs = st.executeQuery(query);
			
			//Process the ResultSet(0 or more records)
			if(rs!=null) {
				boolean flag = false;
				while(rs.next()) {
					flag = true;
					System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
				}//while
				if(flag==false)
					System.out.println("No Records found");
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC Objs
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
			catch(Exception e) {
				e.printStackTrace();
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