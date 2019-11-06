package BankSystem;

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
			System.out.println("2. Open Customer Folder");
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
				System.out.println("3. Back");
				int menuOption = Integer.valueOf(scan.nextLine());
				switch (menuOption){
					case 1:
						consultantType = consultantEnum.JUNIOR.name();
						break;
					case 2:
						consultantType = consultantEnum.SENIOR.name();
						break;
					case 3:
						break;
					default:
						break;
				}

				boolean consultantExists = Controller.createConsultant(consultantType.concat(" " + scan.nextLine()));
				if(!consultantExists)
					System.out.println("New Consultant created!");
				else
					System.out.println("Consultant name already exists!");
				break;
			case 2:

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
