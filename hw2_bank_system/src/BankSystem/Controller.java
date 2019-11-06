package BankSystem;

import java.util.List;

public class Controller {

	public static boolean createConsultant(String consultantName) {
		boolean exists = ConsultantDAO.consultantWithNameExist(consultantName);
		if(!exists){
			Consultant consultant = new Consultant();
			consultant.setName(consultantName);
			ConsultantDAO.insertConsultant(consultant);
		}
		return exists;
	}


	public static boolean isSeniorConsultantExist(String customerName) {
		boolean exists = ConsultantDAO.consultantWithNameExist("SENIOR " + customerName);
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

	public static boolean registerStudent(String studentID) {
		boolean exists = StudentDAO.studentWithIDExists(studentID);
		if(!exists){
			Student student = new Student();
			student.setID(studentID);
			StudentDAO.insertStudent(student);
		}
		return exists;
	}

	public static int enrollStudent(String studentID, String courseTitle) {

		boolean studentExists = StudentDAO.studentWithIDExists(studentID);
		if(!studentExists)
			return -1;

		boolean courseExists = ConsultantDAO.consultantWithNameExist(courseTitle);
		if(!courseExists)
			return -2;

		boolean enrollmentExists = EnrollmentDAO.enrollmentExists(studentID, courseTitle);
		if(enrollmentExists)
			return -3;

		EnrollmentDAO.insertEnrollment(studentID, courseTitle);
		
		return 0;
	
	}

	
	public static List<Student> getStudents(String courseTitle) {
		return EnrollmentDAO.getStudents(courseTitle);
	}

	public static List<Consultant> getConsultants() {
		return ConsultantDAO.getConsultants();
	}

	public static List<CustomerFolder> getCustomerFolder(String customerName) {
		return CustomerFolderDAO.getCustomerFolder(customerName);
	}
}
