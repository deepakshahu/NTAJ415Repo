package com.nit.jdbc4;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;

public class CachedRowSetDemo {

	public static void main(String[] args) {
		try(OracleCachedRowSet crowset = new OracleCachedRowSet()){
			crowset.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crowset.setUsername("system");
			crowset.setPassword("manager");
			crowset.setCommand("SELECT * FROM STUDENT");
			crowset.execute();
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+" "+crowset.getString(2)+" "+crowset.getString(3)+" "+crowset.getString(4));
			}//while
			System.out.println("Stop DB s/w from services.msc");
			Thread.sleep(40000);
			crowset.absolute(3);
			crowset.updateFloat(4, 99.99f);
			crowset.updateRow();
			System.out.println("Offline modifications happened");
			System.out.println("Start DB s/w from services.msc");
			Thread.sleep(60000);
			crowset.acceptChanges();
			while(crowset.next()) {
				System.out.println(crowset.getInt(1)+" "+crowset.getString(2)+" "+crowset.getString(3)+" "+crowset.getString(4));
			}//while
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class