// PsAgeCalculatorOracle.java
package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PsAgeCalculatorOracle {
	private static final String AGE_CALCULATOR="SELECT ROUND((SYSDATE-DOB)/365.25,2)FROM PERSON_INFO_DATES WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			int pid = 0;
			if(sc!=null) {
				System.out.println("Enter Person id: ");
				pid = sc.nextInt();
			}

			//Load JDBC Driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//crate JDBC PreparedStatement object having pre-complied sql query
			if(con!=null)
				ps = con.prepareStatement(AGE_CALCULATOR);

			//set value to query parameters
			if(ps!=null)
				ps.setInt(1,pid);

			//execute the query
			if(ps!=null)
				rs = ps.executeQuery();

			//Process the ResultSet
			if(rs!=null) {
				if(rs.next()) {
					float age = rs.getFloat(1);
					System.out.println("Person age is: "+age);
				}
				else {
					System.out.println("Person not found");
				}

			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objects
			try {
				if(rs!=null)
					rs.close();
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
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
	}//main
}//class