package common;

import java.io.Serializable;
import java.util.Vector;
import bookings.Booking;
import messages.Message;


public class Client implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The list of bookings related to this client. */
	private Vector <Booking> bookingsList;
	/* The list of messages received by this client. */
	private Vector <Message> messagesList;
	/* The personal information of this client. */
	private String name;
	private String address;
	private String phoneContact;
	private String email;
	
	/* The constructor. */
	public Client(String name, String addr, String phone, String mail){
		/* Initializes the basic information. */
		this.name = name;
		address = addr;
		phoneContact = phone;
		email = mail;
		
		/* Initializes the lists. */
		bookingsList = new Vector <Booking>();
		messagesList = new Vector <Message>();
		
	}
	
	public boolean equals(Object c){
		if(((Client) c).getName().equals(this.name)&&((Client) c).getAddress().equals(this.address))
			return true;
		
		return false;
		
	}
	
	/**GETTERS & SETTERS**/

	public Vector <Booking> getBookingsList() {
		return bookingsList;
	}

	public void setBookingsList(Vector <Booking> bookingsList) {
		this.bookingsList = bookingsList;
	}

	public Vector <Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(Vector <Message> messagesList) {
		this.messagesList = messagesList;
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
