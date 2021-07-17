package com.nit.jdbc3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdatableRSTest {
	private static final String STUDENT_SELECT_QUERY="SELECT SNO,SNAME,SADD,AVG FROM STUDENT";

	public static void main(String[] args) {
		try(Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "manager");
				Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet rs = st.executeQuery(STUDENT_SELECT_QUERY);  ){
			if(rs!=null) {
				System.out.println("RS records top to bottom");
				while(rs.next()) {
					System.out.println(rs.getRow()+"----->"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
				}
				/*//Insert operation
				rs.moveToInsertRow();
				rs.updateInt(1, 3455);
				rs.updateString(2, "Suresh");
				rs.updateString(3, "Gujrat");
				rs.updateFloat(4, 54.55f);
				rs.insertRow();
				System.out.println("Record inserted");*/
				
				//update	 operation
				rs.absolute(4);
				rs.updateFloat(4,99.9f);
				rs.updateRow();
				
				//Delete operation
				rs.absolute(4);
				rs.deleteRow();
				
			}//if
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class