package bookings;

import java.io.Serializable;

import common.Flight;

public abstract class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The flight and the client associated with this booking. */
	protected Flight flight;
	protected int noSeats;
	private String name;
	private String address;
	private String phoneContact;
	private String email;
	private int bookingNumber;
	
	/* The constructor. */
	public Booking(Flight flight, int noSeats, String name, String addr, String phone, String mail, int number){
		this.flight = flight;
		this.noSeats = noSeats;
		this.name = name;
		address = addr;
		phoneContact = phone;
		email = mail;
		bookingNumber = number;
		
	}

	public Flight getFlight() {
		return flight;
	}
	
	public int getNoSeats() {
		return noSeats;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	public void setNoSeats(int noSeats) {
		this.noSeats = noSeats;
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
	
	public int getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(int number) {
		this.bookingNumber = number;
	}
}
