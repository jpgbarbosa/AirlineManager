package common;

import java.util.GregorianCalendar;
import java.util.Vector;

import bookings.Booking;

public class Flight {
	/* The list of bookings registered for this flight. */
	private Vector <Booking> seats;
	/* The list of waiting clients. */
	private Vector <Booking> waitingList;
	/* The airplane associated to this flight. */
	private Airplane airplane;
	private GregorianCalendar date;
	private int id;
	
	/* The constructor. */
	public Flight(Airplane plane, GregorianCalendar data){
		airplane = plane;
		seats = new Vector <Booking>();
		date = data;
	}
	
	/* Adds a new booking to the flight. */
	public void newBooking (Booking booking){
		seats.add(booking);
	}
	
	/* Removes a booking from the flight. */
	public boolean removeBooking (Booking booking){
		return seats.remove(booking);
	}
	
	/* Adds a new booking to the waiting list. */
	public void newBookingWaiting (Booking booking){
		waitingList.add(booking);
	}
	
	/* Removes a booking from the waiting list. */
	public boolean removeBookingWaiting (Booking booking){
		return waitingList.remove(booking);
	}

	/* Getters and setters. */
	public Vector <Booking> getSeats() {
		return seats;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public GregorianCalendar getDate() {
		return date;
	}

	public int getId() {
		return id;
	}

	public void setSeats(Vector <Booking> seats) {
		this.seats = seats;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/* Checks whether is flight is full or not. */
	public boolean isFull(){
		/* We have no more seats on this flight. */
		if (airplane.getNoSeats() - seats.size() == 0){
			return true;
		}
		
		/* We still have at least one empty seat. */
		return false;
	}
	
	
}
