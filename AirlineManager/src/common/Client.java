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
	
}
