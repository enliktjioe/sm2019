package BankSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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


    public static boolean insertCustomer(CustomerFolder customer){
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("INSERT INTO customerFolder VALUES ('"+customer.getName()+"', '"+customer.getDOB()+"', '"+customer.getAge()+"', '')");
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

    public static boolean assignSeniorConsultantToCustomerFolder(CustomerFolder customer){
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("UPDATE customerFolder SET assignedConsultant = '"+customer.getConsultantName()+"' WHERE customerName = '"+customer.getName()+"'");
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

    public static String getCustomerDOB(String customerName){
        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;
        String output = "";
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeQuery(
                    "SELECT dateOfBirth FROM customerFolder WHERE customerName = '"+customerName+"'");

            while(result.next()){
                output = result.getString("dateOfBirth");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return output;
    }

    public static boolean updateAgeCustomer(String customerName, int customerAge){
        Connection con = null;
        Statement stmt = null;
        int result = 0;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection( "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeUpdate("UPDATE customerFolder SET age = '"+customerAge+"' WHERE customerName = '"+customerName+"'");
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

    public static List<CustomerFolder> getContentCustomer(String customerName){
        Connection con = null;
        Statement stmt = null;
        ResultSet result = null;
        ArrayList<CustomerFolder> output = new ArrayList<CustomerFolder>();
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            con = DriverManager.getConnection(
                    "jdbc:hsqldb:hsql://localhost/bankdb", "SA", "");
            stmt = con.createStatement();
            result = stmt.executeQuery(
                    "SELECT customerName, dateOfBirth, age FROM customerFolder WHERE customerName = '"+customerName+"'");

            while(result.next()){
                CustomerFolder cust = new CustomerFolder();
                cust.setNameCustomer(result.getString("customerName"));
                cust.setDOBCustomer(result.getString("dateOfBirth"));
                cust.setAge(result.getInt("age"));
                output.add(cust);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return output;
    }
}
