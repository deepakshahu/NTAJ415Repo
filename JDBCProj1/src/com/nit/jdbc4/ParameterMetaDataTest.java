package com.nit.jdbc4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterMetaDataTest {

	public static void main(String[] args) {
		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system","manager");
				PreparedStatement ps = con.prepareStatement("INSERT INTO STUDENT VALUES(?,?,?,?)");
				){
			//Create ParameterMetaData obj
			ParameterMetaData pmd = ps.getParameterMetaData();

			if(pmd!=null) {
				int paramsCount = pmd.getParameterCount();
				for(int i = 1; i <= paramsCount; ++i) {
					System.out.println("Parameter number: "+i);
					System.out.println("Parameter name mode: "+pmd.getParameterMode(i));
					System.out.println("Parameter type name: "+pmd.getParameterTypeName(i));
					System.out.println("Parameter is signed: "+pmd.isSigned(i));
				}//for
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