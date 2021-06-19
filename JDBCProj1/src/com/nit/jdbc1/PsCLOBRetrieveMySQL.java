package com.nit.jdbc1;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class PsCLOBRetrieveMySQL {
	private static final String JS_RETRIEVE_QUERY="SELECT JSID,JSNAME,JSADDRS,RESUME,PHOTO FROM JOBSEEKER_INFO WHERE JSID=?";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){  //try1
			int jsid = 0;
			if(sc!=null) {
				System.out.println("Enter Job Seeker id: ");
				jsid = sc.nextInt();
				//Load JDBC Driver class
				Class.forName("com.mysql.cj.jdbc.Driver");
			}
			try(Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db2","root","root");
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
							//get ReaderStream pointing to CLOB value and InputStream pointing to BLOB value
							try(Reader reader = rs.getCharacterStream(4);
									InputStream is = rs.getBinaryStream(5);
									//Create Output Stream pointing to destination file
									Writer writer = new FileWriter("retrieve_resume.txt");
									OutputStream os = new FileOutputStream("retrieve_image.jpg");){  //try4
								//Copy BLOB and CLOB column value to destination value
								IOUtils.copy(is,os);  //takes image and store
								IOUtils.copy(reader,writer);  //takes text file and store
								System.out.println("BLOB,CLOB value are retrieved and stored in the files");
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
	}

}
