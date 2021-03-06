package BankSystem;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Controller {

    public static boolean ConsultantWithNameExists(String consultantName, ConsultantType consultantType) {
        boolean exists = ConsultantDAO.consultantWithNameExist(consultantName, consultantType);
        return exists;
    }

    public static boolean CustomerWithNameExist(String customerName) {
        boolean exists = CustomerFolderDAO.customerWithNameExist(customerName);
        return exists;
    }

	public static boolean createConsultant(String consultantName, ConsultantType consultantType) {
		boolean exists = ConsultantWithNameExists(consultantName, consultantType);
		if(!exists){
			Consultant consultant = new Consultant();
			consultant.setConsultantName(consultantName);
			consultant.setConsultantType(consultantType);
			ConsultantDAO.insertConsultant(consultant);
		}
		return exists;
	}

	// Reference:
    // http://candidjava.com/tutorial/java-program-to-calculate-age-from-date-of-birth/
    private static int getCustomerAge(String DOB) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(DOB);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);
        System.out.println("Age: " + diff1.getYears() + " years");

        return diff1.getYears();
    }

    public static boolean updateCustomerAge(String customerName) throws ParseException {
        boolean exists = CustomerWithNameExist(customerName);
        if(exists){
            String customerDOB = CustomerFolderDAO.getCustomerDOB(customerName);
            int customerAge = getCustomerAge(customerDOB);
            CustomerFolderDAO.updateCustomerAge(customerName, customerAge);
        }
        return exists;
    }

	public static boolean createCustomer(String customerName, String DOB) throws ParseException {
        boolean exists = CustomerWithNameExist(customerName);
        int customerAge = getCustomerAge(DOB);
        if (!exists){
            CustomerFolder customer = new CustomerFolder();
            customer.setNameCustomer(customerName);
            customer.setDOBCustomer(DOB);
            customer.setAgeCustomer(customerAge);
            CustomerFolderDAO.insertCustomer(customer);
        }
        return exists;
    }

    public static boolean assignSeniorConsultantToCustomerFolder(String seniorConsultantName, String customerName) {
	    boolean exists = ConsultantWithNameExists(seniorConsultantName, ConsultantType.SENIOR);
	    if (exists){
            CustomerFolder customer = new CustomerFolder();
            customer.setAssignedConsultant(seniorConsultantName);
            customer.setNameCustomer(customerName);
            CustomerFolderDAO.assignSeniorConsultantToCustomerFolder(customer);
        }
	    else{
	        createConsultant(seniorConsultantName, ConsultantType.SENIOR);
        }
        return exists;
    }

	public static List<Consultant> getListAvailableConsultant() {
		return ConsultantDAO.getConsultants();
	}

	public static List<CustomerFolder> getContentCustomer(String customerName) {
		return CustomerFolderDAO.getContentCustomer(customerName);
	}
}
