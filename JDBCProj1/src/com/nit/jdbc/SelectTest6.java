//SelectTest6.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTest6 {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Load JDBC Driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement object
			if(con!=null)
				st = con.createStatement();
			
			//Prepare SQL Query
			//select empno,ename,job,sal from emp where sal=(select max(sal) from emp)
			String query = "SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE SAL=(SELECT MAX(SAL) FROM EMP)";
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
		}//finally
	}//main
}//class