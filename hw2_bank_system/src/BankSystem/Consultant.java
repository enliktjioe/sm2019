package BankSystem;

public class Consultant {

	private String consultantName;
	private ConsultantType type;

	public String getConsultantName() {
		return consultantName;
	}
	public ConsultantType getConsultantType() { return this.type; }

	public void setConsultantName(String nextLine) {
		this.consultantName = nextLine;
	}
	public void setConsultantType(ConsultantType type) { this.type = type; }
}
