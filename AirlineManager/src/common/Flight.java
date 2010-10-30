package common;

import java.util.LinkedList;

import bookings.Booking;

public class Flight {
	/* The list of bookings registered for this flight. */
	private LinkedList <Booking> seats;
	/* The airplane associated to this flight. */
	private Airplane airplane;
	
	/* The constructor. */
	public Flight(Airplane plane){
		airplane = plane;
		seats = new LinkedList<Booking>();
	}
	
}
