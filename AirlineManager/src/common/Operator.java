package common;

import java.io.Serializable;

public class Operator extends Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* That company that the operator represents. */
	private String company;
	private String password;
	
	/* The constructor. */
	public Operator(String comp, String name, String addr, String phone, String mail,String pass){
		super(name, addr, phone, mail);
		company = comp;
		password = pass;
		
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
	
	public String toString(){
		return "Name: "+ name + "\nCompany: "+company + "\nContact: "+ phoneContact + "\nEmail: "+email + "\nAddress: " 
			+ address;
	}
	

}
