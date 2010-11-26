package bookings;

import common.Flight;
import common.Operator;

public class CharterBooking extends Booking {
	Operator operator;
	
	/* The constructor. */
	public CharterBooking(Flight flight, Operator operator, String name, String addr, String phone, String mail, int noSeats, int number){
		super(flight, noSeats,name, addr, phone, mail, number);
		
		this.operator = operator;
	}
	
}
