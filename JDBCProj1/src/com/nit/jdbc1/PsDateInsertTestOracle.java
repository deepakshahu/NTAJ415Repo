package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PsDateInsertTestOracle {
	private static final String INSERT_DATE_QUERY = "INSERT INTO PERSON_INFO_DATES VALUES(PID_SEQ.NEXTVAL,?,?,?,?)";

	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			String name=null, sdob=null,sdoj=null,sdom=null;
			if(sc!=null) {
				System.out.println("Person name:: ");
				name = sc.next();
				System.out.println("Person DOB(dd-MM-yyyy) :: ");
				sdob = sc.next();
				System.out.println("Person DOJ(yyyy-MM-dd) :: ");
				sdoj = sc.next();
				System.out.println("Person DOM(MMM-dd-yyyy) :: ");
				sdom = sc.next();
			}
			//Convert String date values to java.sql.Date class objects
			//For DOB
			//Converting String date value to java.util.Date class object
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date udob = sdf1.parse(sdob);
			//Converting java.util.Date class object to java.sql.Date class object
			long ms = udob.getTime();
			java.sql.Date sqdob = new java.sql.Date(ms);

			//For DOJ
			//Converting String date value to java.sql.Date class objects
			java.sql.Date sqdoj = java.sql.Date.valueOf(sdoj);

			//For DOM
			//Converting String date value to java.util.Date class object
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMM-dd-yyyy");
			java.util.Date udom = sdf2.parse(sdom);
			//Converting java.util.Date class object to java.sql.Date class object
			ms = udom.getTime();
			java.sql.Date sqdom = new java.sql.Date(ms);

			//Load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create PreparedStatement object
			if(con!=null)
				ps = con.prepareStatement(INSERT_DATE_QUERY);

			//Set values to Query param
			if(ps!=null) {
				ps.setString(1,name);
				ps.setDate(2,sqdob);
				ps.setDate(3, sqdoj);
				ps.setDate(4, sqdom);
			}

			//Execute Query
			int count = 0;
			if(ps!=null)
				count = ps.executeUpdate();

			//Process the Results
			if(count==0)
				System.out.println("Record not inserted");
			else
				System.out.println("Record inserted");
		}//try
		catch(SQLException se) {
			se.printStackTrace();
			System.out.println("Problem in record insertion");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objects
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