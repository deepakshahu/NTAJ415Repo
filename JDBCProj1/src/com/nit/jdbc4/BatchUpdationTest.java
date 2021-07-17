package com.nit.jdbc4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BatchUpdationTest {

	public static void main(String[] args) {
		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				Statement st = con.createStatement() ){
			//Add queries to batch..these queries can belong to same db table or different db tables but must be non-select Queries
			st.addBatch("INSERT INTO STUDENT VALUES(6000, 'Suraj','Pune',45.74)");
			st.addBatch("UPDATE STUDENT SET AVG=AVG+10 WHERE SNO>=10000");
			st.addBatch("DELETE FROM STUDENT WHERE SNO<=10");
			
			//Execute the batch
			int result[] = st.executeBatch();
			
			//Find sum of the records that are effected
			int sum = 0;
			for(int i = 0; i<result.length; ++i) {
				sum=sum+result[i];
			}
			System.out.println("Total number of records that are effected : "+sum);
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}