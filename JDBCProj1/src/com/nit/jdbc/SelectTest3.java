//SelectTest3.java
package com.nit.jdbc;

/*Java App to get Employee details on given initial characters of Employee name */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SelectTest3 {

	public static void main(String[] args) {
		System.out.println("SelectTest3.main()");
		Scanner sc = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			//Read inputs from enduser
			sc = new Scanner(System.in);
			String initChars=null;
			if(sc!=null) {
			System.out.println("Enter initial characters of employee name : ");
			initChars = sc.next();  //gives s
			}
			//convert input values as required for SQL Query
			initChars="'"+initChars+"%'";  //gives 's%'
			
			//Register JDBC driver by loading JDBC Driver class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection with Oracle DB s/w
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			
			//Create Statement object
			if(con!=null)
				st=con.createStatement();
			
			//Prepare SQL Query
			//SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE ENAME LIKE 'S%';
			String query = "SELECT EMPNO,ENAME,JOB,SAL FROM EMP WHERE ENAME LIKE "+initChars;
			System.out.println(query );
			
			//Send and execute SQL query in DB s/w and get JDBC ResultSet object
			if(st!=null)
				rs=st.executeQuery(query);
			
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