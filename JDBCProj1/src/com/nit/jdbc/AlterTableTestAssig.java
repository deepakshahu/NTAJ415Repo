//AlterTableTestAssig.java
package com.nit.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AlterTableTestAssig {

	public static void main(String[] args) {
		Connection con = null;
		Statement st = null;
		try {
			//Load JDBC Driver Class
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create JDBC Statement object
			if(con!=null)
				st=con.createStatement();
			
			//Prepare SQL Query
			//alter table temp_student add location varchar2(10)
			String query="ALTER TABLE TEMP_STUDENT ADD LOCATION VARCHAR2(10)";
			System.out.println(query);
			
			//Send and execute SQL Query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);
			System.out.println("count: "+count);

			//Process the Result
			if(count==0)
				System.out.println("DB table is altered");
			else
				System.out.println("DB table is not altered");
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			//We can work with error codes
		    if(se.getErrorCode()==1430)
		    	System.out.println("Column being added already exists in DB table");
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