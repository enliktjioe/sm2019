package BankSystem;

import java.util.List;
import java.util.Scanner;
import java.text.ParseException;

public class Boundary {
	
	public static void main(String[] args) throws ParseException {
		System.out.println("Do you want to initialize the database? (Y/N)");
		Scanner scan= new Scanner(System.in);
		String line = scan.nextLine();
		if(line.equals("Y") || line.equals("y")){
			DatabaseManager.resetDatabase();
			DatabaseManager.initializeDatabase();
		}
		while(true){
			System.out.println("\nSelect an option:");
			System.out.println("1. Enter Consultant");
			System.out.println("2. Open Customer Folder and Assign Senior Consultant");
            System.out.println("3. Assign Senior Consultant");
			System.out.println("4. See List of Consultant");
			System.out.println("5. See Content of Customer Folder");
			System.out.println("6. Exit");
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
						consultantType = "JUNIOR";
						break;
					case 2:
						consultantType = "SENIOR";
						break;
					default:
						break;
				}
				System.out.println("Enter Consultant Name:");
				boolean consultantExists = Controller.createConsultant(scan.nextLine(), consultantType);
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
					System.out.println("2. Enter Customer Date of Birth: \n(FORMAT -> yyyy-mm-dd)");
					String customerDOB = String.valueOf(scan.nextLine());

					// count customerAge based on DOB and currentDate
					int customerAge = Controller.countCustomerAge(customerDOB);
                    Controller.createCustomer(customerName, customerDOB, customerAge);

					System.out.println("3. Enter Senior Consultant Name:");
					String consultantName = String.valueOf(scan.nextLine());
                    boolean scExists = Controller.isSeniorConsultantExist(consultantName);
					if (!scExists) {
						Controller.createConsultant(consultantName, "SENIOR");
						System.out.println("New Senior Consultant created!");
					} else {
						Controller.assignSeniorConsultant(customerName, consultantName);
						System.out.println("Senior Consultant " + consultantName + " assigned to Customer Folder " + customerName);
					}
				}
				else {
					System.out.println("Customer name already exists!");
				}

				break;
            case 3:
                System.out.println("1. Enter Customer Name:");
                String cn = String.valueOf(scan.nextLine());

                boolean cnExists = Controller.isCustomerExist(cn);
                if(cnExists) {
                    System.out.println("2. Enter Senior Consultant Name:");
                    String scName = String.valueOf(scan.nextLine());
                    boolean scExists = Controller.isSeniorConsultantExist(scName);
                    if (!scExists) {
                        Controller.createConsultant(scName, "SENIOR");
                        System.out.println("New Senior Consultant created!");
                    } else {
                        Controller.assignSeniorConsultant(cn, scName);
                        System.out.println("Senior Consultant [" + scName + "] assigned to Customer Folder [" + cn + "]");
                    }
                }
                else {
                    System.out.println("Customer name did not exist!");
                }

                break;
			case 4:
				List<Consultant> consultants = Controller.getConsultants();
				System.out.println("The List of Consultants Available:");
                System.out.println("Name" + " / " + "Consultant Type");
				for(Consultant c : consultants){
					System.out.println(c.getConsultantName() + " / " + c.getConsultantType());
				}
				break;
			case 5:
				System.out.println("Enter Customer Name:");
				String custName = String.valueOf(scan.nextLine());
				boolean isCustExists = Controller.isCustomerExist(custName);
				if (isCustExists){
					List<CustomerFolder> customerFolders = Controller.getCustomerFolder(custName);
					System.out.println("Content of the Customer Folder:");
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
