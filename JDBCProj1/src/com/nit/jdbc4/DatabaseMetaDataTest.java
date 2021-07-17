package com.nit.jdbc4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseMetaDataTest {

	public static void main(String[] args) {
		try(//Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager") 
				Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db", "root","root")){
			//Create DatabaseMetaData obj
			DatabaseMetaData dbmd = con.getMetaData();
			if(dbmd!=null) {
				System.out.println("DB Name: "+dbmd.getDatabaseProductName());
				System.out.println("DB Version: "+dbmd.getDatabaseProductVersion());
				System.out.println("JDBC Driver name: "+dbmd.getDriverName());
				System.out.println("JDBC Driver version: "+dbmd.getDriverVersion());
				System.out.println("All SQL keyword: "+dbmd.getSQLKeywords());
				System.out.println("All Numeric functions: "+dbmd.getNumericFunctions());
				System.out.println("All System functions: "+dbmd.getStringFunctions());
				System.out.println("Max chars in table name: "+dbmd.getMaxTableNameLength());
				System.out.println("Max tables in select query: "+dbmd.getMaxTablesInSelect());
				System.out.println("Max row size: "+dbmd.getMaxRowSize());
				System.out.println("Supports PL/SQL procedures ?: "+dbmd.supportsStoredProcedures());
			}
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class