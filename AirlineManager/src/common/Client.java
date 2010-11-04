package common;

import java.util.LinkedList;

import bookings.Booking;

import messages.Message;


public class Client {
	/* The list of bookings related to this client. */
	private LinkedList <Booking> bookingsList;
	/* The list of messages received by this client. */
	//TODO: Will it be necessary or feasible?
	private LinkedList <Message> messagesList;
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
		bookingsList = new LinkedList<Booking>();
		messagesList = new LinkedList<Message>();
		
	}
	
	/**GETTERS & SETTERS**/

	public LinkedList<Booking> getBookingsList() {
		return bookingsList;
	}

	public void setBookingsList(LinkedList<Booking> bookingsList) {
		this.bookingsList = bookingsList;
	}

	public LinkedList<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(LinkedList<Message> messagesList) {
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
