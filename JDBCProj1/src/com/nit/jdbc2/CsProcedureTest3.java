//CsProcedureTest3.java
package com.nit.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*
CREATE OR REPLACE PROCEDURE P_GET_STUDENTDETAILS_BY_ID 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, ADDRESS OUT VARCHAR2 
, AVERAGE OUT VARCHAR2 
) AS 
BEGIN
  SELECT SNAME,SADD,AVG INTO NAME,ADDRESS,AVERAGE FROM STUDENT WHERE SNO=NO;
END;
 */

public class CsProcedureTest3 {
	private static final String CALL_PROCEDURE="{CALL P_GET_STUDENTDETAILS_BY_ID(?,?,?,?) }";
	public static void main(String[] args) {
		//Read inputs
		int sno=0;
		try(Scanner sc = new Scanner(System.in)){
			System.out.println("Enter Student Details: ");
			if(sc!=null) {
				System.out.println("Enter student number: ");
				sno = sc.nextInt();
			}
			//Establish the Connection
			try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
					//Create CallableStatement object having the query calling PL/SQL procedure as the pre-compiled SQL query
					CallableStatement cs = con.prepareCall(CALL_PROCEDURE) ){
				//Register OUT params with JDBC data types
				if(cs!=null) {
					cs.registerOutParameter(2, Types.VARCHAR);
					cs.registerOutParameter(3, Types.VARCHAR);
					cs.registerOutParameter(4, Types.FLOAT);
				}
				
				//Set values to IN params
				if(cs!=null)
					cs.setInt(1,sno);

				//Execute/Call the PL/SQL function
				if(cs!=null)
					cs.execute();
				
				//Gather	results from OUT param
				if(cs!=null) {
					String name = cs.getString(2);
					String addrs = cs.getString(3);
					String avg = cs.getString(4);
					System.out.println("Name: "+name+" Address: "+addrs+" Average: "+avg);
				}
			}//try2
		}//try1
		catch(SQLException se) {
			System.out.println("Requested data is not available");
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class