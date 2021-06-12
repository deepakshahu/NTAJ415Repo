package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class PsDateRetrieveByDateRange {
	private static final String RETRIEVE_DATES_QUERY = "SELECT PID,PNAME,DOB,DOJ,DOM FROM PERSON_INFO_DATES WHERE DOB>=? AND DOB<=?";

	public static void main(String[] args) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Scanner sc = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			String sdob=null,edob=null;
			if(sc!=null) {
				System.out.println("Enter start range of DOB(dd-MM-yyyy) : ");
				sdob = sc.next();
				System.out.println("Enter end range of DOB(dd-MM-yyyy) : ");
				edob = sc.next();
			}
			
			//Converting String Date values to java.sql.util.Date Class object
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			java.sql.Date ssqdob = new java.sql.Date(sdf.parse(sdob).getTime());
			java.sql.Date esqdob = new java.sql.Date(sdf.parse(edob).getTime());
			
			//Load JDBC driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create PreparedStatement object
			if(con!=null)
				ps = con.prepareStatement(RETRIEVE_DATES_QUERY);
			
			//Set values to SQL query param
			if(ps!=null) {
				ps.setDate(1, ssqdob);
				ps.setDate(2, esqdob);
			}

			//Execute Query
			if(ps!=null)
				rs = ps.executeQuery();

			//Process the ResultSet object
			/* if(rs!=null) {
				while(rs.next()!=false) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
				}//while
			}//if */
			System.out.println("---------------------------------------------");
			//Process the ResultSet object
			if(rs!=null) {
				boolean flag = false;
				while(rs.next()!=false) {
					flag = true;
					int pid = rs.getInt(1);
					String name = rs.getString(2);
					java.sql.Date sqdob = rs.getDate(3);
					java.sql.Date sqdoj = rs.getDate(4);
					java.sql.Date sqdom = rs.getDate(5);
					
					//Convert java.sql.Date class object to String date value
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					String dob=sdf1.format(sqdob);
					String doj=sdf1.format(sqdoj);
					String dom=sdf1.format(sqdom);
					System.out.println(pid+" "+name+" "+dob+" "+doj+" "+dom);
				}//while
				if(flag==false) 
					System.out.println("No records are found");
			}//if
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