package bookings;

import common.Flight;

public class NormalBooking extends Booking {
	
	/* The constructor. */
	public NormalBooking(int flight, String name, String addr, String phone, String mail, int noSeats, int number){
		super(flight, noSeats, name, addr, phone, mail, number);
	}
}
