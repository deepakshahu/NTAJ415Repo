//PsInsertTestSurrogatePKPostgreSQL.java
package com.nit.jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PsInsertTestSurrogatePKPostgreSQL {
	private static final String PRODUCT_INSERT_QUERY="INSERT INTO PRODUCT VALUES(NEXTVAL('PID_SEQ'),?,?,?,?)";
	public static void main(String[] args) {
		try (	Scanner sc = new Scanner(System.in) ){
			int count = 0;
			if(sc!=null) {
			System.out.println("Enter products count: ");
			count = sc.nextInt();
			}
			//Register JDBC Driver (optional)
			//Class.forName("org.postgresql.Driver");
			
			try(Connection con = DriverManager.getConnection("jdbc:postgresql:NTAJ415DB","postgres","tiger");
					PreparedStatement ps = con.prepareStatement(PRODUCT_INSERT_QUERY) )	{	
			//Read input values from enduser, set them to query param values 
			//and execute the pre-compiled SQL Query for multiple times
			if(ps!=null && sc!=null) {
				for(int i=1; i<=count; ++i) {
					System.out.println("Enter "+i+" product details");
					System.out.println("Enter product name: ");
					String name = sc.next();
					System.out.println("Enter product price: ");
					Float price = sc.nextFloat();
					System.out.println("Enter product quantity: ");
					Float qty = sc.nextFloat();
					System.out.println("Enter product status: ");
					String status = sc.next();
					
					//Set each student details as pre-compiled SQL query params
					ps.setString(1, name);
					ps.setFloat(2, price);
					ps.setFloat(3, qty);
					ps.setString(4, status);
					
					//Execute pre-compiled SQL Query each time
					int result = ps.executeUpdate();
					
					//Process execution result of pre-compiled SQL Query
					if(result==0)
						System.out.println(i+" product details are not inserted");
					else
						System.out.println(i+" product details are inserted");
				}//for
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