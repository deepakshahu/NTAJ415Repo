package com.nit.jdbc1;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/*
CREATE TABLE "SYSTEM"."CUSTOMER_INFO" 
   (	"CID" NUMBER(10,0) NOT NULL ENABLE, 
	"CNAME" VARCHAR2(20 BYTE), 
	"GENDER" VARCHAR2(20 BYTE), 
	"DOB" DATE, 
	"DOJ" DATE, 
	"RESUME" CLOB, 
	"BIODATA" CLOB, 
	"PHOTO" BLOB, 
	"AUDIO" BLOB, 
	"VIDEO" BLOB, 
	 CONSTRAINT "CUSTOMER_INFO_PK" PRIMARY KEY ("CID")

CREATE SEQUENCE  "SYSTEM"."CID_SEQ"  MINVALUE 1000 MAXVALUE 100000 INCREMENT BY 1 START WITH 1020 CACHE 20 NOORDER  NOCYCLE ;
 */

public class MatrimonyInsertApp {
	private static final String INSERT_CUSTOMER_QUERY = "INSERT INTO ES(CID_SEQ.NEXTVAL,?,?,?,?,?CUSTOMER_INFO VALU,?,?,?,?)";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){
			//Read inputs
			String name=null,gender=null,dob=null,doj=null,resumeLocation=null,bioDataLocation=null,photoLocation=null,audioLocation=null,videoLocation=null;
			if(sc!=null) {
				System.out.println("Enter customer name: ");
				name = sc.next();
				System.out.println("Enter customer gender: ");
				gender = sc.next();
				System.out.println("Enter customer DOB(dd-MM-yyyy): ");
				dob = sc.next();
				System.out.println("Enter customer DOJ(yyyy-MM-dd): ");
				doj = sc.next();
				System.out.println("Enter resume location: ");
				resumeLocation = sc.next().replace("?"," ");
				System.out.println("Enter biodata location: ");
				bioDataLocation = sc.next().replace("?"," ");
				System.out.println("Enter photo location: ");
				photoLocation = sc.next().replace("?"," ");
				System.out.println("Enter audio location: ");
				audioLocation = sc.next().replace("?"," ");
				System.out.println("Enter video location: ");
				videoLocation = sc.next().replace("?"," ");
			}//if
			//Convert String date values to java.sql.Date class objects
			//For DOB
			//Converting String date value to java.util.Date class object
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date udob = sdf1.parse(dob);
			//Converting java.util.Date class object to java.sql.Date class object
			long ms = udob.getTime();
			java.sql.Date sqdob = new java.sql.Date(ms);

			//For DOJ
			//Converting String date value to java.sql.Date class objects
			java.sql.Date sqdoj = java.sql.Date.valueOf(doj);

			//Create inputStream pointing to photo,audio and video file
			try(Reader reader1 = new FileReader(resumeLocation);
					Reader reader2 = new FileReader(bioDataLocation);
					InputStream is1 = new FileInputStream(photoLocation);
					InputStream is2 = new FileInputStream(audioLocation);
					InputStream is3 = new FileInputStream(videoLocation)){
				//Establish the connection and prepare statement object
				try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
						PreparedStatement ps = con.prepareStatement(INSERT_CUSTOMER_QUERY); ){
					//Set values to query parameters
					if(ps!=null) {
						ps.setString(1,name);
						ps.setString(2,gender);
						ps.setDate(3,sqdob);
						ps.setDate(4,sqdoj);
						ps.setCharacterStream(5,reader1);
						ps.setCharacterStream(6,reader2);
						ps.setBinaryStream(7,is1);
						ps.setBinaryStream(8,is2);
						ps.setBinaryStream(9,is3);
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