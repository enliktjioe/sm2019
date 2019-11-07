package BankSystem;

import java.util.List;

public class Controller {

	public static boolean createConsultant(String consultantName, String consultantType) {
		boolean exists = ConsultantDAO.consultantWithNameExist(consultantName, consultantType);
		if(!exists){
			Consultant consultant = new Consultant();
			consultant.setName(consultantName);
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

	public static void createCustomer(String customerName, String DOB, int age, String consultantName) {
		CustomerFolder customerFolder = new CustomerFolder();
		customerFolder.setName(customerName);
		CustomerFolderDAO.insertCustomer(customerFolder, DOB, age, consultantName);
		return;
	}

	public static List<Consultant> getConsultants() {
		return ConsultantDAO.getConsultants();
	}

	public static List<CustomerFolder> getCustomerFolder(String customerName) {
		return CustomerFolderDAO.getCustomerFolder(customerName);
	}
}
