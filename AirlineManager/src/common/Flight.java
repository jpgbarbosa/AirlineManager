package common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import bookings.Booking;

/**
 * This class represents a Flight. Each flight must have a booking vector that stores all clients' bookings.
 * It has a date, an origin, destination and its own identification number. It can also be a regular or a
 * charter flight. If it was cancelled, wasCancelled flag is raised up.
 * Every flight must be associated with a specific Airplane. 
 *
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author João Penetra
 * @author João Barbosa
 * @author Ricardo Bernardino
 */
@SuppressWarnings("serial")
public class Flight implements Serializable{
	/* The list of bookings registered for this flight. */
	private Vector <Booking> bookings;
	private int occupied;
	
	/* The airplane associated to this flight. */
	private Airplane airplane;
	private GregorianCalendar date;
	private String origin;
	private String destination;
	private boolean isRegular;
	private boolean isCharter;
	private boolean wasCancelled;
	private int id;
	/* Lock object to prevent errors while executing concurrent operations */
	public Lock lock = new Lock();
	
	/**
	 * Creates a new Flight.
	 * 
	 * @param plane
	 * @param data
	 * @param origin
	 * @param dest
	 * @param isRegular
	 * @param isCharter
	 */
	public Flight(Airplane plane, GregorianCalendar data, String origin, String dest, boolean isRegular, boolean isCharter){
		this.airplane = plane;
		this.origin = origin;
		this.destination = dest;
		this.bookings = new Vector <Booking>();
		this.date = data;
		this.isRegular = isRegular;
		this.wasCancelled = false;
		this.isCharter = isCharter;
	}
	
	/**
	 * Override method. It compares two flights according to its id, destination and date
	 * 
	 * @param Flight object
	 */
	public boolean equals(Object obj){
		Flight f=(Flight) obj;
		if(airplane.getId()==f.getAirplane().getId()&&destination.equals(f.getDestination())){
			if(date.get(Calendar.DAY_OF_YEAR)==f.getDate().get(Calendar.DAY_OF_YEAR)&&
					date.get(Calendar.YEAR)==f.getDate().get(Calendar.YEAR)&&
					date.get(Calendar.HOUR)==f.getDate().get(Calendar.HOUR))
				return true;
		}
		
		return false;
	}
	
	/** Adds a new booking to the flight.
	 * @param booking
	 */
	public void newBooking (Booking booking){
		bookings.add(booking);
	}
	
	/**
	 * Removes a booking from the flight.
	 * 
	 * @param booking
	 * @return the removed booking
	 */
	public boolean removeBooking (Booking booking){
		return bookings.remove(booking);
	}

	/**
	 * Searches a booking by ID
	 * 
	 * @param id
	 * @return the booking if found, null if not
	 */
	public Booking findBookingById(int id){
		for (int i = 0; i < bookings.size(); i++){
			if (bookings.elementAt(i).getBookingNumber() == id)
				return bookings.elementAt(i);
		}
		
		return null;
	}
	
	/* Getters and setters. */
	public Vector <Booking> getBookings() {
		return bookings;
	}
	
	public Airplane getAirplane() {
		return airplane;
	}

	public GregorianCalendar getDate() {
		return date;
	}
	
	public Date getData() {
		return date.getTime();
	}

	public int getId() {
		return id;
	}
	
	public String getDestination() {
		return destination;
	}
	
	public boolean isFull(){
		
		if (occupied == airplane.getNoSeats()){
			return true;
		}
		
		return false;
	}

	public void setBookings(Vector <Booking> seats) {
		this.bookings = seats;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 *  The number of empty seats for this flight. 
	 *	@return return int - the available seats  
	 */
	public int getEmptySeats(){
		return airplane.getNoSeats() - occupied;
	}

	public int getOccupiedSeats() {
		return occupied;
	}
	
	public String getOrigin() {
		return origin;
	}

	public boolean isRegular() {
		return isRegular;
	}

	public boolean isWasCancelledp() {
		return wasCancelled;
	}

	public void setWasCancelled(boolean wasCancelled) {
		this.wasCancelled = wasCancelled;
	}
	
	public boolean isCharter() {
		return isCharter;
	}
	
	public String toString(){
		return "ID: "+ id + "\nDate: "+date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+date.get(Calendar.YEAR)+
		"\nOrigin: "+ origin + "\nDestination: "+destination + "\nRegular:"+ new Boolean(isRegular).toString() + 
		"\nCancelled: "+ new Boolean(wasCancelled).toString() +"\nOver: "+ (getEmptySeats() > 0 ? "Not closed." : "Closed.") + "\n\n";
	}
	
	public void increaseOccupied(int no){
		occupied += no;
	}
	
	public void decreaseOccupied(int no){
		occupied -= no;
	}
}
