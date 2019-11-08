package BankSystem;

public class CustomerFolder {
    private String customerName;
    private String dateOfBirth;
    private int age;
    private String assignedConsultant;

    public String getName() {
        return customerName;
    }
    public String getDOB() { return dateOfBirth; }
    public int getAge() {
        return age;
    }
    public String getAssignedConsultant() {
        return assignedConsultant;
    }


    public void setNameCustomer(String nextLine) {
        this.customerName = nextLine;
    }
    public void setDOBCustomer(String nextLine) { this.dateOfBirth = nextLine; }
    public void setAge(int nextLine) {
        this.age = nextLine;
    }
    public void setAssignedConsultant(String nextLine) { this.assignedConsultant = nextLine; }

}
