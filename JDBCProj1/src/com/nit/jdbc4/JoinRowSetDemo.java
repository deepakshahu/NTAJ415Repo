package com.nit.jdbc4;

import java.sql.SQLException;

import oracle.jdbc.rowset.OracleCachedRowSet;
import oracle.jdbc.rowset.OracleJoinRowSet;

public class JoinRowSetDemo {

	public static void main(String[] args) {
		try(OracleCachedRowSet crs1 = new OracleCachedRowSet();
				OracleCachedRowSet crs2 = new OracleCachedRowSet();
				OracleJoinRowSet jnRowSet = new OracleJoinRowSet()){
			crs1.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crs1.setUsername("system");
			crs1.setPassword("manager");
			crs1.setMatchColumn(5);
			crs1.setCommand("SELECT EMPNO,ENAME,JOB,SAL,DEPTNO FROM EMP");
			crs1.execute();

			crs2.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
			crs2.setUsername("system");
			crs2.setPassword("manager");
			crs2.setMatchColumn(1);
			crs2.setCommand("SELECT DEPTNO,DNAME,LOCATION FROM DEPT");
			crs2.execute();

			//Add multiple cached rowsets to joinrowset
			jnRowSet.addRowSet(crs2);
			jnRowSet.addRowSet(crs1);

			//Process the JoinRowSet
			while(jnRowSet.next()) {
				System.out.println(jnRowSet.getInt(1)+" "+jnRowSet.getString(2)+" "+jnRowSet.getString(3)+" "+jnRowSet.getString(4)+" "+jnRowSet.getString(5)+" "+jnRowSet.getString(6)+" "+jnRowSet.getString(7));
			}
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}//main
}//class