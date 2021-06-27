package com.nit.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import oracle.jdbc.OracleTypes;

/*
 CREATE OR REPLACE PROCEDURE P_GET_EMPS_BYNAME_INITIAL 
(
  INITIALCHARS IN VARCHAR2 
, DETAILS OUT SYS_REFCURSOR 
) AS 
BEGIN
  OPEN DETAILS FOR
        SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP WHERE ENAME LIKE INITIALCHARS;
END P_GET_EMPS_BYNAME_INITIAL;
*/

public class CsProcedureCursorTest {
	private static final String PROCEDURE_CALL_QUERY="{call P_GET_EMPS_BYNAME_INITIAL(?,?)}";

	public static void main(String[] args) {
		String initChars = null;
		try(Scanner sc = new Scanner(System.in)){
			if(sc!=null) {
			System.out.println("Enter Initial characters of employee name: ");
			initChars = sc.next()+"%";
			}
			try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
					CallableStatement cs = con.prepareCall(PROCEDURE_CALL_QUERY);  ){
				//Register OUT parameters with JDBC Type
				if(cs!=null)
					cs.registerOutParameter(2, OracleTypes.CURSOR);
				
				//Set values to IN param
				if(cs!=null)
					cs.setString(1, initChars);
				
				//Execute or call PL/SQL Procedure
				if(cs!=null)
					cs.execute();
				
				//Gather results from OUT parameters
				if(cs!=null) {
					ResultSet rs = (ResultSet)cs.getObject(2);
					System.out.println("The output is: ");
					boolean flag = false;
					while(rs.next()) {
						flag=true;
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getFloat(4)+" "+rs.getInt(5));
					}//while
					if(flag==false)
						System.out.println("Records not found");
				}//if
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