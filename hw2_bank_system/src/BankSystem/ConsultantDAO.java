package BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConsultantDAO {

	public static boolean consultantWithNameExist(String consultantName){
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		boolean output = false;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement();
			result = stmt.executeQuery(
					"SELECT * FROM consultant WHERE consultantName = '"+consultantName+"'");
			while(result.next()){
				output= true;
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return output;
	}


	public static boolean insertConsultant(Consultant consultant){
		Connection con = null; 
		Statement stmt = null; 
		int result = 0; 
		try { 
			Class.forName("org.hsqldb.jdbc.JDBCDriver"); 
			con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement(); 
			result = stmt.executeUpdate("INSERT INTO consultant VALUES ('"+ consultant.getName()+"')");
			con.commit();
		}catch (Exception e) { 
			e.printStackTrace(System.out); 
		} 
		if(result == 0){
			return false;
		}
		else{
			return true;
		}
	}
}