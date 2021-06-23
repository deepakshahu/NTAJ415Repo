// PsAgeCalculatorPostgreSQL.java
package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PsAgeCalculatorPostgreSQL {
	private static final String AGE_CALCULATOR="SELECT AGE(dob) FROM PERSON WHERE PID=?";
	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in) ){
			int pid = 0;
			if(sc!=null) {
				System.out.println("Enter Person id: ");
				pid = sc.nextInt();
			}
			try(//Establish the Connection with Oracle DB s/w
					Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/NTAJ415DB", "postgres", "tiger");
					//Create Statement object
					PreparedStatement ps = con.prepareStatement(AGE_CALCULATOR);	  ){

				//set value to query parameters
				if(ps!=null)
					ps.setInt(1,pid);

				//Execute the query
				try(ResultSet rs = ps.executeQuery()){
					//Process the ResultSet
					if(rs!=null) {
						if(rs.next()) {
							String age = rs.getString(1);
							System.out.println("Person age is: "+age);
						}
						else {
							System.out.println("Person not found");
						}
					}//if
				}//try3
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