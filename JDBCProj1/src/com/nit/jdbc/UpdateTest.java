//UpdateTest.java
package com.nit.jdbc;

/*Write a JDBC App to modify student name,avg & sadd based on the given student number(sno) */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateTest {

	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		Statement st=null;
		try {
			//Reading input values
			sc = new Scanner(System.in);
			String newCity=null,newName=null;
			float newAvg=0;
			int no=0;
			if(sc!=null) {
				System.out.print("Enter new name for student: ");
				newName = sc.nextLine();  //gives Anil Rao
				System.out.println("Enter new address for student: ");
				newCity = sc.nextLine();  //Thane
				System.out.println("Enter new avg for student: ");
				newAvg = sc.nextFloat();  //80.78
				System.out.println("Enter sno of student: ");
				no=sc.nextInt();  //gives 104
			}
			//Convert input values as required for the SQL Query
			newName="'"+newName+"'";  //gives 'Anil Rao'
			newCity="'"+newCity+"'";  //gives 'Thane'

			//Register JDBC driver by loading JDBC driver class
			//Class.forName("oracle:jdbc:driver:OracleDriver");

			//Establish the connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");

			//Create Statement object
			if(con!=null)
				st = con.createStatement();

			//Prepare SQL Query
			//update student set sname='Anil Rao', sadd='Thane', avg=80.78 where sno=104;
			String query = "UPDATE STUDENT SET SNAME="+	newName+", SADD="+newCity+", AVG="+newAvg+" WHERE SNO="+no;
			System.out.println(query);

			//Send and execute SQL Query in DB s/w
			int count=0;
			if(st!=null)
				count=st.executeUpdate(query);

			//Process the Result
			if(count==0)
				System.out.println("No records for updation");
			else
				System.out.println("Number of records that are effected: "+count);			
		}//try
		catch(SQLException se) {
			if(se.getErrorCode()>=900 && se.getErrorCode()<=999)
				System.out.println("Invalid column name or table name or SQL keywords");
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//Close JDBC objs
			try {
				if(st!=null)
					st.close();
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