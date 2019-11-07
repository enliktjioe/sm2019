package BankSystem;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class Controller {

	public static boolean createConsultant(String consultantName, String consultantType) {
		boolean exists = ConsultantDAO.consultantWithNameExist(consultantName, consultantType);
		if(!exists){
			Consultant consultant = new Consultant();
			consultant.setConsultantName(consultantName);
			consultant.setConsultantType(consultantType);
			ConsultantDAO.insertConsultant(consultant);
		}
		return exists;
	}

	public static boolean isSeniorConsultantExist(String customerName) {
		boolean exists = ConsultantDAO.consultantWithNameExist(customerName, "SENIOR");
		return exists;
	}

	public static boolean isCustomerExist(String customerName) {
		boolean exists = CustomerFolderDAO.customerWithNameExist(customerName);
		return exists;
	}

	// Reference:
    // http://candidjava.com/tutorial/java-program-to-calculate-age-from-date-of-birth/
    public static int countCustomerAge(String DOB) throws ParseException {
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
        System.out.println("Age: " + diff1.getYears() + "years");

        return diff1.getYears();
    }

	public static void createCustomer(String customerName, String DOB, int age) {
        CustomerFolder customerFolder = new CustomerFolder();
        customerFolder.setName(customerName);
        customerFolder.setDateOfBirth(DOB);
        customerFolder.setAge(age);
        CustomerFolderDAO.insertCustomer(customerFolder);
        return;
    }

    public static void assignSeniorConsultant(String customerName, String consultantName) {
        CustomerFolder customerFolder = new CustomerFolder();
        customerFolder.setName(customerName);
        customerFolder.setConsultantName(consultantName);
        CustomerFolderDAO.assignSeniorConsultant(customerFolder);
        return;
    }

	public static List<Consultant> getConsultants() {
		return ConsultantDAO.getConsultants();
	}

	public static List<CustomerFolder> getCustomerFolder(String customerName) {
		return CustomerFolderDAO.getCustomerFolder(customerName);
	}
}
