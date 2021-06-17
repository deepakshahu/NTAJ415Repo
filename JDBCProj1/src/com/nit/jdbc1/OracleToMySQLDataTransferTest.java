//OracleToMySQLDataTransferTest.java
package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleToMySQLDataTransferTest {
	private static final String ORACLE_SELECT_STUDENT = "SELECT SNO,SNAME,SADD,AVG FROM STUDENT";
	private static final String MYSQL_INSERT_STUDENT = "INSERT INTO STUDENT (SNAME,SADD,AVG) VALUES(?,?,?)";

	public static void main(String[] args) {
		Connection oraCon = null, mysqlCon = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//Register JDBC Drivers
			/*Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName("com.mysql.cj.jdbc.Driver");*/

			//Establish the connection
			oraCon = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
			mysqlCon = DriverManager.getConnection("jdbc:mysql:///ntaj415db","root","root");

			//Create Statement object
			if(oraCon!=null)
				st = oraCon.createStatement();
			if(mysqlCon!=null)
				ps = mysqlCon.prepareStatement(MYSQL_INSERT_STUDENT);

			//Send and execute select SQL query in oracle db s/w and get ResultSet obj
			if(st!=null)
				rs = st.executeQuery(ORACLE_SELECT_STUDENT);

			//Gather each record of RS object and insert into mysql DB table
			if(rs!=null && ps!=null) {
				while(rs.next()) {
					//gather each record from RS
					int no = rs.getInt(1);
					String name = rs.getString(2);
					String addrs = rs.getString(3);
					float avg = rs.getFloat(4);
					//set each record values as INSERT Query param values to insert to mySQL DB table
					ps.setString(1, name);
					ps.setString(2, addrs);
					ps.setFloat(3, avg);
					//Execute the Query
					ps.executeUpdate();
				}//while
				System.out.println("Records are copied from oracle db table to mysql db table successfully");
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			System.out.println("Records are not copied from oracle db table to mysql db table");
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Problems in app execution");
		}
		finally {
			//Close jdbc objs
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(st!=null)
					st.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(mysqlCon!=null)
					mysqlCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(oraCon!=null)
					oraCon.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
		}//finally
	}//main
}//class