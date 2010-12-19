//COMPLETELY CHECKED

package bookings;

import java.io.Serializable;

import clients.Client;


/**
 * This class is responsible for saving the client reservation data. It associates the client and the
 * respective flight and has informations about the booking price, booking number, number of seats reserved.
 * It also contains the boarding ticket and the respective hashed version to ensure the the ticket is valid.
 *
 */
public class Booking implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The flight and the client are associated with this booking. */
	private int idFlight;
	private int noSeats;
	private Client client;
	private int bookingNumber;
	private double price;
	
	
	

	/**
	 * Creates a new booking
	 * 
	 * @param idFlight
	 * @param noSeats
	 * @param client
	 * @param number
	 * @param price
	 */
	public Booking(int idFlight, int noSeats, Client client, int number, double price){
		this.idFlight = idFlight;
		this.noSeats = noSeats;
		bookingNumber = number;
		this.client = client;
		this.price = price*noSeats;
		
		
	}
	
	
	public int getIdFlight() {
		return idFlight;
	}
	
	public int getNoSeats() {
		return noSeats;
	}
	
	public int getBookingNumber() {
		return bookingNumber;
	}
	
	public double getPrice(){
		return price;
	}
	
	public Client getClient(){
		return client;
	}
	
	public String toString(){
		return "Flight ID: "+idFlight+"\nBooking ID: "+bookingNumber+"\nName: "+client.getName()+"\nEmail: "+client.getEmail()
			+ "\nNumber of seats: "+ noSeats;
	}
}
