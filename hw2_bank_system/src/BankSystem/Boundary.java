package BankSystem;

import javax.naming.ldap.Control;
import java.util.List;
import java.util.Scanner;

public class Boundary {

	enum consultantEnum {
		JUNIOR,
		SENIOR
	}
	
	public static void main(String[] args) {
		System.out.println("Do you want to initialize the database? (Y/N)");
		Scanner scan= new Scanner(System.in);
		String line = scan.nextLine();
		if(line.equals("Y") || line.equals("y")){
			DatabaseManager.resetDatabase();
			DatabaseManager.initializeDatabase();
		}
		while(true){
			System.out.println("Select an option:");
			System.out.println("1. Enter Consultant");
			System.out.println("2. Open Customer Folder and Assign Senior Consultant");
			System.out.println("3. See List of Consultant");
			System.out.println("4. See Content of Customer Folder");
			System.out.println("5. Exit");
			int choice = Integer.valueOf(scan.nextLine());
			switch (choice){
			case 1:
				String consultantType = " ";
				System.out.println("Pick one option:");
				System.out.println("1. Enter Junior Consultant");
				System.out.println("2. Enter Senior Consultant");
				int menuOption = Integer.valueOf(scan.nextLine());
				switch (menuOption){
					case 1:
						consultantType = consultantEnum.JUNIOR.name();
						break;
					case 2:
						consultantType = consultantEnum.SENIOR.name();
						break;
					default:
						break;
				}
				System.out.println("Enter Consultant Name:");
				boolean consultantExists = Controller.createConsultant(consultantType.concat(" " + scan.nextLine()));
				if(!consultantExists)
					System.out.println("New Consultant created!");
				else
					System.out.println("Consultant name already exists!");
				break;
			case 2:
				System.out.println("1. Enter Customer Name:");
				String customerName = String.valueOf(scan.nextLine());

				boolean customerExists = Controller.isCustomerExist(customerName);
				if(!customerExists) {
					System.out.println("2. Enter Customer Date of Birth:");
					String customerDOB = String.valueOf(scan.nextLine());
					System.out.println("3. Enter Age:"); //TODO remove this and make a logic for set 'age' value based on DOB
					int customerAge = Integer.valueOf(scan.nextLine());
					System.out.println("4. Enter Senior Consultant Name:");
					String consultantName = String.valueOf(scan.nextLine());
					boolean scExists = Controller.isSeniorConsultantExist(consultantName);
					if (!scExists) {
						Controller.createConsultant("SENIOR " + consultantName);
						System.out.println("New Senior Consultant created!");
					} else {
						Controller.createCustomer(customerName, customerDOB, customerAge, consultantName);
						System.out.println("New Customer Folder created!");
					}
				}
				else {
					System.out.println("Customer name already exists!");
				}

				break;
			case 3:
				break;
			case 4:
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
