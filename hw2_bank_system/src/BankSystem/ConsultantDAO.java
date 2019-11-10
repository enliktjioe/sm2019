package BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConsultantDAO {

	public static boolean consultantWithNameExist(String consultantName, ConsultantType consultantType){
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
					"SELECT * FROM consultant WHERE consultantName = '"+consultantName+"' AND consultantType = '"+consultantType.name()+"'");
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
			result = stmt.executeUpdate("INSERT INTO consultant VALUES ('"+ consultant.getConsultantName()+"', '"+consultant.getConsultantType().name()+"', '"+consultant.getIsAssigned()+"')");
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

	public static boolean updateAssignedConsultant(Consultant consultant){
		Connection con = null;
		Statement stmt = null;
		int result = 0;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement();
			result = stmt.executeUpdate("UPDATE consultant SET isAssigned = '"+consultant.getIsAssigned()+"' WHERE consultantName = '"+consultant.getConsultantName()+"'");
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


	public static List<Consultant> getConsultants(){
		Connection con = null;
		Statement stmt = null;
		ResultSet result = null;
		ArrayList<Consultant> output = new ArrayList<Consultant>();
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection(
					"jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement();
			result = stmt.executeQuery(
					"SELECT * FROM consultant");

			while(result.next()){
				Consultant c = new Consultant();
				c.setConsultantName(result.getString("consultantName"));
				c.setConsultantType(ConsultantType.valueOf(result.getString("consultantType")));
				output.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return output;
	}
}