//SelectTestTWR.java
package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectTestTWR {

	public static void main(String[] args) {
		try(//Establish the Connection with Oracle DB s/w
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
				//Create Statement object
				Statement st=con.createStatement();
				//Send and execute SQL query in DB s/w and get JDBC ResultSet object
				ResultSet rs=st.executeQuery("SELECT SNO,SNAME,SADD,AVG FROM STUDENT");   ) {	
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
		}//try  //all jdbc objects will be closed
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class