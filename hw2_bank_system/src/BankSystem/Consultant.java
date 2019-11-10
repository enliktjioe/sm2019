package BankSystem;

public class Consultant {

	private String consultantName;
	private ConsultantType type;
	private boolean isAssigned;

	public String getConsultantName() {
		return consultantName;
	}
	public ConsultantType getConsultantType() { return this.type; }
    public boolean getIsAssigned() { return this.isAssigned; }

	public void setConsultantName(String nextLine) { this.consultantName = nextLine; }
	public void setConsultantType(ConsultantType type) { this.type = type; }
    //public void setIsAssigned(boolean isAssigned) { this.isAssigned = isAssigned; }
}
