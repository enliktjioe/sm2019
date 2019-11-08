package BankSystem;

import java.util.List;
import java.util.Scanner;
import java.text.ParseException;

public class Boundary {

	public static void main(String[] args) throws ParseException {
        String consultantName;
        ConsultantType consultantType;

        String customerName;
		String customerDOB;

        boolean consultantNameExists;
        boolean customerNameExists;

		System.out.println("Do you want to initialize the database? (Y/N)");
		Scanner scan= new Scanner(System.in);
		String line = scan.nextLine();
		if(line.equals("Y") || line.equals("y")){
			DatabaseManager.resetDatabase();
			DatabaseManager.initializeDatabase();
		}
		while(true){
			System.out.println("\nSelect an option:");
			System.out.println("1. Add Consultant");
			System.out.println("2. Open Customer Folder and Assign Senior Consultant");
            System.out.println("3. Assign Senior Consultant");
			System.out.println("4. See List of Consultant");
			System.out.println("5. See Content of Customer Folder");
			System.out.println("6. Exit");
			int choice = Integer.valueOf(scan.nextLine());
			switch (choice){
			case 1:
				System.out.println("Pick one option:");
				System.out.println("1. Enter Junior Consultant");
				System.out.println("2. Enter Senior Consultant");
				int menuOption = Integer.valueOf(scan.nextLine());
				switch (menuOption){
					case 1:
						consultantType = ConsultantType.JUNIOR;
						break;
					case 2:
						consultantType = ConsultantType.SENIOR;
						break;
					default:
                        consultantType = ConsultantType.JUNIOR;
						break;
				}
				System.out.println("Enter Consultant Name:");
				consultantName = String.valueOf(scan.nextLine());

				consultantNameExists = Controller.createConsultant(consultantName, consultantType);

				if(consultantNameExists) {
                    System.out.println("Consultant name already exists!");
                }
				else{
                    System.out.println("New Consultant added!");
                }
				break;
			case 2:
				System.out.println("1. Enter Customer Name:");
				customerName = String.valueOf(scan.nextLine());

				System.out.println("2. Enter Customer Date of Birth: \n(FORMAT -> yyyy-mm-dd)");
				customerDOB = String.valueOf(scan.nextLine());

				customerNameExists = Controller.createCustomer(customerName, customerDOB);
				if(customerNameExists) {
                    System.out.println("Customer name already exists!");
				}
				else {
                    System.out.println("Customer Folder Added!");
                    System.out.println(" ");
                    System.out.println("3. Assign Senior Consultant. Enter consultant name:");
                    consultantName = String.valueOf(scan.nextLine());

                    consultantNameExists = Controller.assignSeniorConsultantToCustomerFolder(consultantName, customerName);
                    if (consultantNameExists) {
                        System.out.println("Senior Consultant [" + consultantName + "] assigned to Customer Folder [" + customerName + "]");
                    } else {
                        System.out.println("New Senior Consultant created!");
                    }
				}
				break;
            case 3:
                System.out.println("1. Enter Customer Name:");
                customerName = String.valueOf(scan.nextLine());

                customerNameExists = Controller.CustomerWithNameExist(customerName);
                if(customerNameExists) {
                    System.out.println("2. Enter Senior Consultant Name:");
                    consultantName = String.valueOf(scan.nextLine());
                    consultantNameExists = Controller.assignSeniorConsultantToCustomerFolder(consultantName, customerName);
                    if (consultantNameExists) {
                        System.out.println("Senior Consultant [" + consultantName + "] assigned to Customer Folder [" + customerName + "]");
                    }
                    else {
                        System.out.println("New Senior Consultant created!");}
                	}
                else {
                    System.out.println("Customer name did not exist!");
                }
                break;
			case 4:
				List<Consultant> listAvailableConsultants = Controller.getListAvailableConsultant();
				System.out.println("The List of Consultants Available:");
                System.out.println("Name" + " / " + "Consultant Type");
				for(Consultant c : listAvailableConsultants){
					System.out.println(c.getConsultantName() + " / " + c.getConsultantType().name());
				}
				break;
			case 5:
				System.out.println("Enter Customer Name:");
				customerName = String.valueOf(scan.nextLine());
				customerNameExists = Controller.updateAgeCustomer(customerName); // Updating customer age based on current date
				if (customerNameExists){
					List<CustomerFolder> customerFolders = Controller.getContentCustomer(customerName);
					System.out.println("Content of the Customer Folder [" + customerName + "] :");
					for(CustomerFolder cust : customerFolders){
						System.out.println("Customer Name = " + cust.getName());
						System.out.println("Date of Birth = " + cust.getDOB());
						System.out.println("Age = " + cust.getAge());
					}
				}
				else {
					System.out.println("Customer name is not exist");
				}
				break;
			default:
				choice = 6;
				break;
			}
			if(choice == 6)
				break;
		}
		scan.close();
	}
}
