package com.nit.jdbc1;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class PsBLOBPhotoRetrieveMySQL {
	private static final String ARTIST_RETRIEVE_QUERY="SELECT AID,NAME,ADDRS,PHOTO FROM ARTIST_INFO WHERE AID=?";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){
			int aid = 0;
			if(sc!=null) {
				System.out.println("Enter Artist id: ");
				aid = sc.nextInt();
			}
			try(Connection con = DriverManager.getConnection("jdbc:mysql:///ntaj415db2","root","root");
					PreparedStatement ps = con.prepareStatement(ARTIST_RETRIEVE_QUERY);  ){
				//set query params
				if(ps!=null) 
					ps.setInt(1, aid);

				//Execute the query
				try(ResultSet rs = ps.executeQuery()){
					//Process the Result
					if(rs!=null) {
						if(rs.next()) {
							aid = rs.getInt(1);
							String name = rs.getString(2);
							String addrs = rs.getString(3);
							System.out.println(aid+" "+name+" "+addrs);
							//get InputStream pointing to BLOB value
							try(InputStream is = rs.getBinaryStream(4);
									//Create Output Stream pointing to destination file
									OutputStream os = new FileOutputStream("retrieve_image.jpg");
									){
								//Copy BLOB col value to destination value
								IOUtils.copy(is,os);
								System.out.println("BLOB value is retrieved and stored in the file");
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
