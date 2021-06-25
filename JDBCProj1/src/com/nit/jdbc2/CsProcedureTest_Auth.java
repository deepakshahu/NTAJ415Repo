package com.nit.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*
 CREATE OR REPLACE PROCEDURE P_AUTHENTICATE 
(
  USERNAME IN VARCHAR2 
, PASSWORD IN VARCHAR2 
, RESULT OUT VARCHAR2 
) AS 
    CNT NUMBER(5);
BEGIN
  SELECT COUNT(*) INTO CNT FROM IRCTC_TAB WHERE UNAME=USERNAME AND PWD=PASSWORD;
  IF(CNT<>0) THEN
    	RESULT:='VALID CREDENTIALS';
    ELSE
    	RESULT:='INVALID CREDENTIALS';
    END IF;
END P_AUTHENTICATE;
 */

public class CsProcedureTest_Auth {
	private static final String CALL_PROCEDURE_QUERY="{CALL P_AUTHENTICATE(?,?,?)}";
	public static void main(String[] args) {
		//Read inputs
		try(Scanner sc = new Scanner(System.in)){
			String username=null,password=null;
			if(sc!=null) {
				System.out.println("Enter Username: ");
				username=sc.next();
				System.out.println("Enter Password: ");
				password=sc.next();
			}
			try(//Establish the connection
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
					//Create CallableStatement obj
					CallableStatement cs = con.prepareCall(CALL_PROCEDURE_QUERY); ){
				//Register OUT parameters with JDBC types
				if(cs!=null)
					cs.registerOutParameter(3,Types.VARCHAR);

				//Set values to IN parameters
				if(cs!=null) {
					cs.setString(1, username);
					cs.setString(2, password);
				}
				//Call PL/SQL Procedure
				if(cs!=null)
					cs.execute();

				//Get results from OUT param
				String result=null;
				if(cs!=null)
					result=cs.getString(3);
				System.out.println(result);
			}//try2
		}//try1
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class