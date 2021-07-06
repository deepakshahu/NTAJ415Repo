package com.nit.jdbc2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/*
CREATE DEFINER=`root`@`localhost` PROCEDURE `P_GET_STUDENT_DETAILS_BYADDRS`(IN ADDRS VARCHAR(100))
BEGIN
	SELECT SNO,SNAME,AVG FROM STUDENT WHERE SADD=ADDRS;
END
 */

public class CsProcedureMySQLTest {
	private static final String PROCEDURE_CALL_MYSQL = "{CALL P_GET_STUDENT_DETAILS_BYADDRS(?)}";

	public static void main(String[] args) {
		//Read inputs
		String addrs=null;
		ResultSet rs=null;
		try(Scanner sc = new Scanner(System.in)){
			if(sc!=null) {
				System.out.println("Enter student address to get details: ");
				addrs = sc.next();
			}
			try(Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db","root","root");
					CallableStatement cs=con.prepareCall(PROCEDURE_CALL_MYSQL); ){

				//Set values to IN param
				if(cs!=null)
					cs.setString(1, addrs);
				
				rs = cs.executeQuery();

				//Gather results
				if(cs!=null) {
					System.out.println("The output is: ");
					boolean flag = false;
					while(rs.next()) {
						flag=true;
						System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getFloat(3));
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