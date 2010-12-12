package bookings;

import java.io.Serializable;

import common.Client;

public class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The flight and the client associated with this booking. */
	private int idFlight;
	private int noSeats;
	private Client client;
	private int bookingNumber;
	private double price;
	private String boardingTicket;
	private int hashBoardingTicket;
	
	

	/* The constructor. */
	public Booking(int idFlight, int noSeats, Client client, int number, double price){
		this.idFlight = idFlight;
		this.noSeats = noSeats;
		bookingNumber = number;
		this.client = client;
		this.price = price*noSeats;
		boardingTicket=client.getName()+idFlight+number+price+"";
		hashBoardingTicket=boardingTicket.hashCode();
		
	}
	
	public String getBoardingTicket() {
		return boardingTicket;
	}

	

	public int getHashBoardingTicket() {
		return hashBoardingTicket;
	}

	public int getIdFlight() {
		return idFlight;
	}
	
	public int getNoSeats() {
		return noSeats;
	}

	public void setFlight(int flight) {
		this.idFlight = flight;
	}
	
	public void setNoSeats(int noSeats) {
		this.noSeats = noSeats;
	}
	
	public int getBookingNumber() {
		return bookingNumber;
	}

	public void setBookingNumber(int number) {
		this.bookingNumber = number;
	}
	
	public String toString(){
		return "Flight ID: "+idFlight+"\nBooking ID: "+bookingNumber+"\nName: "+client.getName()+"\nEmail: "+client.getEmail()
			+ "\nNumber of seats: "+ noSeats;
	}
	
	public double getPrice(){
		return price;
	}
	
	public Client getClient(){
		return client;
	}
}
