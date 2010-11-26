package bookings;

import common.Flight;

public class RegularBooking extends Booking {
	
	/* The constructor. */
	public RegularBooking(Flight flight, int noSeats, String name, String addr, String phone, String mail){
		super(flight, noSeats, name, addr, phone, mail);
	}
}
