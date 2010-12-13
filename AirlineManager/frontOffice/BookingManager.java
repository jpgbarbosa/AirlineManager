package frontOffice;

import bookings.Booking;
import common.Flight;

public class BookingManager {
	
	/* The constructor. */
	public BookingManager(){
		
	}
	
	//TODO: We can remove this class. 
	
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
