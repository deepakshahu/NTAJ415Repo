package com.nit.jdbc1;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*CREATE TABLE "SYSTEM"."AQ$_QUEUE_TABLES" 
   (	"SCHEMA" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"NAME" VARCHAR2(30 BYTE) NOT NULL ENABLE, 
	"UDATA_TYPE" NUMBER NOT NULL ENABLE, 
	"OBJNO" NUMBER NOT NULL ENABLE, 
	"FLAGS" NUMBER NOT NULL ENABLE, 
	"SORT_COLS" NUMBER NOT NULL ENABLE, 
	"TIMEZONE" VARCHAR2(64 BYTE), 
	"TABLE_COMMENT" VARCHAR2(2000 BYTE), 
	 CONSTRAINT "AQ$_QUEUE_TABLES_PRIMARY" PRIMARY KEY ("OBJNO")

CREATE SEQUENCE  "SYSTEM"."JSID_SEQ"  MINVALUE 1001 MAXVALUE 100000 INCREMENT BY 1 START WITH 1001 CACHE 20 NOORDER  NOCYCLE ;
 */

public class PsClobInsertOracleTest {
	private static final String INSERT_JOBSEEKER_QUERY = "INSERT INTO JOBSEEKER_INFO VALUES(JSID_SEQ.NEXTVAL,?,?,?)";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){  //try1
			//Read inputs
			String name=null,addrs=null,resumeLocation=null;
			if(sc!=null) {
				System.out.println("Enter job seeker name: ");
				name = sc.next();
				System.out.println("Enter job seeker address: ");
				addrs = sc.next();
				System.out.println("Enter resume location: ");
				resumeLocation = sc.next().replace("?"," ");
			}//if
			//Create ReaderStream pointing to resume file
			try(Reader reader = new FileReader(resumeLocation)){  //try2
				//Establish the connection and prepare statement object
				try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
						PreparedStatement ps = con.prepareStatement(INSERT_JOBSEEKER_QUERY); ){
					//Set values to query param
					if(ps!=null) {
						ps.setString(1,name);
						ps.setString(2,addrs);
						ps.setCharacterStream(3,reader);
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