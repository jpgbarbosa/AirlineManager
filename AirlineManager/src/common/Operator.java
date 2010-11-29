package common;

import java.io.Serializable;

public class Operator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* That company that the operator represents. */
	private String company;
	private String password;
	private String name;
	private String address;
	private String phoneContact;
	private String email;
	
	/* The constructor. */
	public Operator(String comp, String name, String addr, String phone, String mail,String pass){
		company = comp;
		password = pass;
		this.name = name;
		address = addr;
		phoneContact = phone;
		email = mail;
		
	}
	
	public boolean equals(Object c){
		if(((Operator) c).getName().equals(this.name)&&((Operator) c).getAddress().equals(this.address))
			return true;
		
		return false;
		
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneContact() {
		return phoneContact;
	}

	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
