package com.nit.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*
CREATE OR REPLACE FUNCTION FX_GET_STUDENT_DETAILS_BY_NO 
(
  NO IN NUMBER 
, NAME OUT VARCHAR2 
, ADDRS OUT VARCHAR2 
) RETURN FLOAT AS 
  PERCENTAGE FLOAT;
BEGIN
  SELECT SNAME,SADD,AVG INTO NAME,ADDRS,PERCENTAGE FROM STUDENT WHERE SNO=NO;

  RETURN PERCENTAGE;
END FX_GET_STUDENT_DETAILS_BY_NO;
 */

public class CsFunctionTest {
	private static final String CALL_FX_QUERY="{?=call FX_GET_STUDENT_DETAILS_BY_NO(?,?,?)}";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){
			//Read inputs
			int no=0;
			if(sc!=null) {
				System.out.println("Enter student number: ");
				no=sc.nextInt();
			}
			try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
					CallableStatement cs=con.prepareCall(CALL_FX_QUERY); ){
				//Register return,OUT params with JDBC types
				if(cs!=null) {
					cs.registerOutParameter(1,Types.FLOAT);  //return parameter
					cs.registerOutParameter(3,Types.VARCHAR);  //out parameter(index 3)
					cs.registerOutParameter(4,Types.VARCHAR);  //out parameter(index 4)	
				}
				//Set values to IN params
				if(cs!=null)
					cs.setInt(2,no);

				//Execute or call PL/SQL Procedure
				if(cs!=null)
					cs.execute();

				//Gather results from OUT parameters
				if(cs!=null) {
					System.out.println("Student Name: "+cs.getString(3));  //out param
					System.out.println("Student Address: "+cs.getString(4));  //out param
					System.out.println("Student Avg: "+cs.getFloat(1));  //return param
				}//if
			}//try2
		}//try1
		catch(SQLException se) {
			System.out.println("Records not found");
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}