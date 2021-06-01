//CreateTableTest.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		try {
			//Load JDBC Driver Class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement obj
			if(con!=null)
				st=con.createStatement();
			
			//Prepare SQL Query
			//create table temp_student(sno number(5) primary key, sname varchar2(15))
			String query="CREATE TABLE TEMP_STUDENT(SNO NUMBER(5) PRIMARY KEY, SNAME VARCHAR2(15))";
			System.out.println(query);
			
			//Send and execute SQL Query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);
			System.out.println("count: "+count);

			//Process the Result
			if(count==0)
				System.out.println("DB table is created");
			else
				System.out.println("DB table is not created");
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			//We can work with error codes
		    if(se.getErrorCode()==955)
		    	System.out.println("DB Table is already created");
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
		}//finally
	}//main
}//class