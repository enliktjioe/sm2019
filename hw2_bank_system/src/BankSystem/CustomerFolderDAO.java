package BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerFolderDAO {

    public static boolean customerWithNameExist(String customerName){
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
                    "SELECT * FROM customerFolder WHERE customerName = '"+customerName+"'");
            while(result.next()){
                output= true;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return output;
    }


    public static boolean insertCustomer(CustomerFolder customer, String DOB, int age, String consultantName){
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("INSERT INTO customerFolder VALUES ('"+ customer.getName()+"', '"+DOB+"', '"+age+"', '"+consultantName+"')");
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
