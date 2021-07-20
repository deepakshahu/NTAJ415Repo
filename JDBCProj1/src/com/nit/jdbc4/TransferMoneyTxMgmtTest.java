package com.nit.jdbc4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class TransferMoneyTxMgmtTest {

	public static void main(String[] args) {
		//Read inputs
		long srcAcno=0,destAcno=0;
		double amount=0.0;
		try(Scanner sc = new Scanner(System.in)){
			if(sc!=null) {
				System.out.println("Enter source account number: ");
				srcAcno=sc.nextLong();
				System.out.println("Enter destination account number: ");
				destAcno=sc.nextLong();
				System.out.println("Enter amount to transfer: ");
				amount=sc.nextDouble();
			}//if
		}//try
		catch(Exception e) {
			e.printStackTrace();
		}
		try(Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
				Statement st = con.createStatement();
				){
			//Begin Tx
			if(con!=null)
				con.setAutoCommit(false);
			
			if(st!=null) {
				//Add queries to batch
				//For withDraw operation
				st.addBatch("update JDBC_ACCOUNT set balance = balance - "+amount+" where acno="+srcAcno);
				//For deposit operation
				st.addBatch("update JDBC_ACCOUNT set balance = balance + "+amount+" where acno="+destAcno);
				//Execute the batch
				int result[]=st.executeBatch();

				//Process the results from TxMgmt
				boolean flag = true;
				for(int i = 0; i<result.length; ++i) {
					if(result[i]==0) {
						flag=false;
						break;
					}
				}
				if(flag==true) {
					con.commit();
					System.out.println("Tx commited, Money Transfered");
				}
				else {
					con.rollback();
					System.out.println("Tx rollback, Money not Transfered");
				}
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