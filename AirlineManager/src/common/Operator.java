package common;

public class Operator extends Client{
	/* That company that the operator represents. */
	private String company;
	private String password;
	
	/* The constructor. */
	public Operator(String comp, String name, String addr, String phone, String mail,String pass){
		super(name,addr,phone,mail);
		company = comp;
		password = pass;
	}

	/* Getters and setters. */
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPassword(){
		return password;
	}
}
