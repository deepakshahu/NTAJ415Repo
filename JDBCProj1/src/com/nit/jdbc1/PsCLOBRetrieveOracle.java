package com.nit.jdbc1;

import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class PsCLOBRetrieveOracle {
	private static final String JS_RETRIEVE_QUERY="SELECT JSID,JSNAME,JSADDRS,RESUME FROM JOBSEEKER_INFO WHERE JSID=?";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){  //try1
			int jsid = 0;
			if(sc!=null) {
				System.out.println("Enter Job Seeker id: ");
				jsid = sc.nextInt();
				//Load JDBC Driver class
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
					PreparedStatement ps = con.prepareStatement(JS_RETRIEVE_QUERY);  ){  //try2
				//set query params
				if(ps!=null) 
					ps.setInt(1, jsid);

				//Execute the query
				try(ResultSet rs = ps.executeQuery()){  //try3
					//Process the Result
					if(rs!=null) {
						if(rs.next()) {
							jsid = rs.getInt(1);
							String name = rs.getString(2);
							String addrs = rs.getString(3);
							System.out.println(jsid+" "+name+" "+addrs);
							//get ReaderStream pointing to CLOB value
							try(Reader reader = rs.getCharacterStream(4);
									//Create Output Stream pointing to destination file
									Writer writer = new FileWriter("retrieve_resume.txt");){  //try4
								//Copy CLOB column value to destination value
								IOUtils.copy(reader,writer);  //takes text file and store
								System.out.println("CLOB value are retrieved and stored in the file");
							}//try4
						}//if
						else {
							System.out.println("Record not found");
						}//else
					}//if
				}//try3
			}//try2
		}//try1
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}//main
}//class