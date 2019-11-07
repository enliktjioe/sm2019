package BankSystem;

import java.util.Date;

public class CustomerFolder {
    private String customerName;
    private String dateOfBirth;
    private int age;
    private String consultantName;

    public String getName() {
        return customerName;
    }
    public String getDOB() {
        return dateOfBirth;
    }
    public int getAge() {
        return age;
    }
    public String getConsultantName() {
        return consultantName;
    }


    public void setName(String nextLine) {
        this.customerName = nextLine;
    }
    public void setDateOfBirth(String nextLine) { this.dateOfBirth = nextLine; }
    public void setAge(int nextLine) {
        this.age = nextLine;
    }
    public void setConsultantName(String nextLine) { this.consultantName = nextLine; }

}
