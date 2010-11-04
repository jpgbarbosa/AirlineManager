package common;

import java.util.Vector;
import bookings.Booking;
import messages.Message;


public class Client {
	/* The list of bookings related to this client. */
	private Vector <Booking> bookingsList;
	/* The list of messages received by this client. */
	//TODO: Will it be necessary or feasible?
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
	
}
