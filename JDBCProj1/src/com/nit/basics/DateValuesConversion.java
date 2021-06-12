package com.nit.basics;

import java.text.SimpleDateFormat;

public class DateValuesConversion {

	public static void main(String[] args) throws Exception{
		//Converting String date value to java.util.Date class object
		String s1 = "41-22-1990";  //dd-MM-yyyy
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date ud1 = sdf.parse(s1);
		System.out.println("String date value	:: "+s1);
		System.out.println("util date:: "+ud1);
		
		//Converting java.util.Date class object to java.sql.Date class obj
		long ms = ud1.getTime();  //gives no of milliseconds that elapsed b/w 
		                                            //ut1 date and time and 1970 jan 1st mid night 00:00 hrs (Epoach Standard)
		java.sql.Date sqd1 = new java.sql.Date(ms);
		System.out.println("util date	:: "+ud1);
		System.out.println("sql date:: "+sqd1);
		
		//if String date value pattern is yyyy-MM-dd pattern then it can be converted directly to java.sql.Date class object
		//without converting to java.sql.Date class object
		String s2 = "1991-12-25";  //yyyy-MM-dd
		java.sql.Date sqd2 = java.sql.Date.valueOf(s2);
		System.out.println("String date value :: "+s2);
		System.out.println("sql date value :: "+sqd2);
		
		//Converting java.sql.Date class object to java.util.Date class object
		//here we can use java.util.Date class ref to refer java.sql.Date class object(java.util.Date is super class for java.sql.Date)
		java.util.Date ud2 = sqd2;
		System.out.println("sql date2 :: "+sqd2);
		System.out.println("util date2 :: "+ud2);
		// (OR)
		java.util.Date ud3 = new java.util.Date(sqd2.getTime());
		System.out.println("sql date2 :: "+sqd2);
		System.out.println("util date2 :: "+ud3);
		
		//Converting java.sql.Date class object or java.util.Date class object to String date value
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MMM-yyyy");
		String s3 = sdf2.format(ud3);
		String s4 = sdf2.format(sqd2);
		System.out.println("util date :: "+ud3);
		System.out.println("String date :: "+s3);
		System.out.println("String date :: "+s4);
	}
}