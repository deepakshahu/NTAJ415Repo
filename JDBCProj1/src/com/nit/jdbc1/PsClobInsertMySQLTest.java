package com.nit.jdbc1;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/*CREATE TABLE `jobseeker_info` (
  `JSID` int NOT NULL AUTO_INCREMENT,
  `JSNAME` varchar(25) DEFAULT NULL,
  `JSADDRS` varchar(25) DEFAULT NULL,
  `RESUME` longtext,
  `PHOTO` longblob,
  PRIMARY KEY (`JSID`),
  UNIQUE KEY `JSID_UNIQUE` (`JSID`)
)
 */

public class PsClobInsertMySQLTest {
	private static final String INSERT_JOBSEEKER_QUERY = "INSERT INTO JOBSEEKER_INFO(JSNAME,JSADDRS,RESUME,PHOTO) VALUES(?,?,?,?)";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){  //try1
			//Read inputs
			String name=null,addrs=null,resumeLocation=null,photoLocation=null;
			if(sc!=null) {
				System.out.println("Enter job seeker name: ");
				name = sc.next();
				System.out.println("Enter job seeker address: ");
				addrs = sc.next();
				System.out.println("Enter resume location: ");
				resumeLocation = sc.next().replace("?"," ");
				System.out.println("Enter photo location: ");
				photoLocation = sc.next().replace("?"," ");
			}//if
			//Create ReaderStream pointing to resume file
			try(Reader reader = new FileReader(resumeLocation);
					InputStream is = new FileInputStream(photoLocation)){  //try2
				//Establish the connection and prepare statement object
				try(Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db2","root","root");
						PreparedStatement ps = con.prepareStatement(INSERT_JOBSEEKER_QUERY); ){
					//Set values to query param
					if(ps!=null) {
						ps.setString(1,name);
						ps.setString(2,addrs);
						ps.setCharacterStream(3,reader);
						ps.setBinaryStream(4, is);
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