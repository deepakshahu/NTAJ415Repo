package com.nit.jdbc1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*CREATE TABLE `artist_info` (
		  `aid` int NOT NULL AUTO_INCREMENT,
		  `name` varchar(15) DEFAULT NULL,
		  `addrs` varchar(15) DEFAULT NULL,
		  `photo` longblob,
		  PRIMARY KEY (`aid`),
		  UNIQUE KEY `aid_UNIQUE` (`aid`)
		) */

public class PsBlobInsertMySQLTest {
	private static final String INSERT_ARTIST_QUERY = "INSERT INTO ARTIST_INFO(NAME,ADDRS,PHOTO) VALUES(?,?,?)";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){
			//Read inputs
			String name=null,addrs=null,photoLocation=null;
			if(sc!=null) {
				System.out.println("Enter artist name: ");
				name = sc.next();
				System.out.println("Enter artist address: ");
				addrs = sc.next();
				System.out.println("Enter artist photo location: ");
				photoLocation = sc.next().replace("?","");
			}//if
			//Create inputStream pointing to photo file
			try(InputStream is = new FileInputStream(photoLocation)){
				//Establish the connection and prepare statement object
				try(Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db2","root","root");
						PreparedStatement ps = con.prepareStatement(INSERT_ARTIST_QUERY); ){
					//Set values to query param
					if(ps!=null) {
						ps.setString(1,name);
						ps.setString(2,addrs);
						ps.setBinaryStream(3,is);
					}
					//Execute the query
					int count = 0;
					if(ps!=null)
						count = ps.executeUpdate();

					//Process the Results
					if(count==0)
						System.out.println("Record not inserted");
					else
						System.out.println("Record inserted");
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