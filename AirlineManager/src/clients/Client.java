package clients;

import java.io.Serializable;

/**
 * Class associated with each Client. Has information about the Client, such as:
 * name, address, contacts, etc.
 * 
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author Penetra
 * @author Barbosa
 * @author Ricardo Bernardino
 * 
 */
public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	protected String address;
	protected String phoneContact;
	protected String email;
	/* The number of kilometers flown so far. */
	protected double kilometers;
	
	/**
	 * Creates a new client according to his  personal data
	 * 
	 * @param name
	 * @param addr
	 * @param phone
	 * @param mail
	 */
	public Client(String name, String addr, String phone, String mail){
		/* Initializes the basic information. */
		this.name = name;
		address = addr;
		phoneContact = phone;
		email = mail;
		
	}
	
	/**
	 * Override of method equals. It compares a client by his name and address
	 */
	@Override
	public boolean equals(Object c){
		if(((Client) c).getName().equals(this.name)&&((Client) c).getAddress().equals(this.address))
			return true;
		
		return false;
		
	}
	
	/*GETTERS & SETTERS**/

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
	
	public double getKilometers(){
		return kilometers;
	}
	
	public void increaseKilometers(double no){
		kilometers += no;
	}
	
	public void setKilometers(Double km){
		kilometers = km;
	}
	
	public String toString(){
		return "Name: "+ name +"\nContact: "+ phoneContact + "\nEmail: "+email + "\nAddress: " 
			+ address + "\nKilometers: "+ kilometers;
	}
	
	
}
