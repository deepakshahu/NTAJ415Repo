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
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

public class MatrimonyRetrieveApp {
	private static final String CUSTOMER_RETRIEVE_QUERY="SELECT CID,CNAME,GENDER,DOB,DOJ,RESUME,BIODATA,PHOTO,AUDIO,VIDEO FROM CUSTOMER_INFO WHERE CID=?";

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in)){
			int cid = 0;
			if(sc!=null) {
				System.out.println("Enter Customer id: ");
				cid = sc.nextInt();
			}
			try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
					PreparedStatement ps = con.prepareStatement(CUSTOMER_RETRIEVE_QUERY);  ){
				//set query parameters
				if(ps!=null) 
					ps.setInt(1, cid);

				//Execute the query
				try(ResultSet rs = ps.executeQuery()){
					//Process the Result
					if(rs!=null) {
						if(rs.next()) {
							cid = rs.getInt(1);
							String name = rs.getString(2);
							String gender = rs.getString(3);
							java.sql.Date sqdob = rs.getDate(4);
							java.sql.Date sqdoj = rs.getDate(5);

							//Convert java.sql.Date class object to String date value
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
							String sdob=sdf.format(sqdob);
							String sdoj=sdf.format(sqdoj);
							System.out.println(cid+" "+name+" "+gender+" "+sdob+" "+sdoj);
							//get InputStream pointing to BLOB value and ReaderStream pointing to CLOB value
							try(Reader reader1 = rs.getCharacterStream(6);
									Reader reader2 = rs.getCharacterStream(7);
									InputStream is1 = rs.getBinaryStream(8);
									InputStream is2 = rs.getBinaryStream(9);
									InputStream is3 = rs.getBinaryStream(10);
									//Create Output Stream pointing to destination file
									Writer writer1 = new FileWriter("D:/Deepak/Classcontent/MatrimonyRetrieve/retrieve_resume.txt");
									Writer writer2 = new FileWriter("D:/Deepak/Classcontent/MatrimonyRetrieve/retrieve_biodata.txt");
									OutputStream os1 = new FileOutputStream("D:/Deepak/Classcontent/MatrimonyRetrieve/retrieve_image.jpg");
									OutputStream os2 = new FileOutputStream("D:/Deepak/Classcontent/MatrimonyRetrieve/retrieve_audio.mp3");
									OutputStream os3 = new FileOutputStream("D:/Deepak/Classcontent/MatrimonyRetrieve/retrieve_video.mp4");     ){
								//Copy BLOB and CLOB column value to destination value
								IOUtils.copy(reader1,writer1); 
								IOUtils.copy(reader2,writer2); 
								IOUtils.copy(is1,os1);
								IOUtils.copy(is2,os2);
								IOUtils.copy(is3,os3);
								System.out.println("BLOB and CLOB value is retrieved and stored in the file");
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