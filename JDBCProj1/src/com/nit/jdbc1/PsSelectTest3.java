//PsSelectTest3.java
package com.nit.jdbc1;

/*Java App to get all Employee details */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PsSelectTest3 {
	private static final String SELECT_EMP_QUERY = "SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE JOB IN('CLERK','MANAGER')";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			//Register JDBC driver by loading JDBC Driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection with Oracle DB s/w
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			//Create Statement object
			if(con!=null)
				ps=con.prepareStatement(SELECT_EMP_QUERY);
			
			//Send and execute SQL query in DB s/w and get JDBC ResultSet object
			if(ps!=null)
				rs=ps.executeQuery();
			
			//Process the ResultSet object
			if(rs!=null) {
				boolean flag = false;
				while(rs.next()) {
					flag=true;
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4));
				}//while
				if(flag==false)
					System.out.println("No Records Found");
			}//if
			
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()>=900 && se.getErrorCode()<=999) 
				System.out.println("Invalid Column name or Table name or SQL Keywords");
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
				if(ps!=null)
					ps.close();
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
		}//finally
	}//main
}//class