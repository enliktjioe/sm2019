package BankSystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseManager {

	public static void initializeDatabase(){
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement();
			stmt.executeUpdate("CREATE TABLE customerFolder (customerName varchar(20) NOT NULL, dateOfBirth date, age int, assignedConsultant varchar(20) NOT NULL, PRIMARY KEY (customerName));");
			stmt.executeUpdate("CREATE TABLE consultant (consultantName varchar(20) NOT NULL, consultantType varchar(20) NOT NULL, PRIMARY KEY (consultantName));");
			System.out.println("Database initialized successfully");
		}catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public static void resetDatabase(){
		Connection con = null;
		Statement stmt = null;

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
			stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE customerFolder;");
			stmt.executeUpdate("DROP TABLE consultant;");
			System.out.println("Database reset successfully");
		}  catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}



}
