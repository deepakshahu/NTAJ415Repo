package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PsInsertTestSurrogatePK {
	private static final String STUDENT_INSERT_QUERY="INSERT INTO STUDENT VALUES(SNO_SEQ1.NEXTVAL,?,?,?)";
	public static void main(String[] args) {
		Scanner sc = null;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			//Read inputs
			sc = new Scanner(System.in);
			int count = 0;
			if(sc!=null) {
			System.out.println("Enter students count: ");
			count = sc.nextInt();
			}
			//Register JDBC Driver
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			
			//Create PreparedStatement object having pre-complied SQL Query
			if(con!=null)
			ps = con.prepareStatement(STUDENT_INSERT_QUERY);
			
			//Read input values from enduser, set them to query param values 
			//and execute the pre-compiled SQL Query for multiple times
			if(ps!=null && sc!=null) {
				for(int i=1; i<=count; ++i) {
					System.out.println("Enter "+i+" student details");
					System.out.println("Enter Student name: ");
					String name = sc.next();
					System.out.println("Enter Student address: ");
					String addrs = sc.next();
					System.out.println("Enter Student avg: ");
					Float avg = sc.nextFloat();
					
					//Set each student details as pre-compiled SQL query params
					ps.setString(1, name);
					ps.setString(2, addrs);
					ps.setFloat(3, avg);
					
					//Execute pre-compiled SQL Query each time
					int result = ps.executeUpdate();
					
					//Process execution result of pre-compiled SQL Query
					if(result==0)
						System.out.println(i+" student details are not inserted");
					else
						System.out.println(i+" student details are inserted");
				}//for
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objs
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