//SelectTest4.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest4 {

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Read inputs from end user
			sc = new Scanner(System.in);
			int dno=0;
			if(sc!=null) {
				System.out.print("Enter a dept number: ");
				dno=sc.nextInt();  //Gives 40
			}
			
			//Load JDBC driver class
			    //Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			//Create JDBC Statement obj
			st=con.createStatement();
			
			//Prepare SQL Query
			    //Select * from dept where deptno=40
			String query = "SELECT DEPTNO,DNAME,LOCATION FROM DEPT WHERE DEPTNO="+dno;
			System.out.println(query);
			
			//Send and execute SQL Query
			if(st!=null)
				rs=st.executeQuery(query);
			
			//Process the ResultSet object (0 or 1 record)
			if(rs!=null) {
				if(rs.next())
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
				else
					System.out.println("No Records Found");
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
		}
	}
}