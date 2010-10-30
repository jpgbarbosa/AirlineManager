package bookings;

import common.Flight;
import common.Operator;

public class RegularBooking extends Booking {
	
	/* The constructor. */
	public RegularBooking(Flight flight, Operator client){
		super(flight,client);
	}
}
