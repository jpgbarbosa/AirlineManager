package frontOffice;

import bookings.Booking;
import common.Flight;

public class BookingManager {
	
	/* The constructor. */
	public BookingManager(){
		
	}
	
	/* Books a flight. */
	public String bookFlight(Flight flight, Booking booking){
		/* First, we need to check if we still have space in this flight. */
		if (flight.isFull()){
			return "This flight is full, the client was added to the waiting list.\n";
		}
		
		flight.newBooking(booking);
		return "This client was successfully booked in this flight.\n";
		
	}
	
	/* Cancels a booking. */
	public String cancelBooking(Flight flight, Booking booking){
		/* We have to first check */
		if (flight.removeBooking(booking)){
			return "The booking was removed from the fligh.\n";
		}
		
		/* This booking wasn't found in any place. */
		return "This booking isn't registered in this flight.\n";
		
		
	}
	
	/* Changes a specific booking. We may want to go to a different
	 * destiny, change to a later/earlier time,...
	 */
	public void changeBooking(Flight oldFlight, Flight newFlight, Booking bookings){
		
	}
	
}
