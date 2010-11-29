package bookings;

import common.Operator;

@SuppressWarnings("serial")
public class CharterBooking extends Booking {
	Operator operator;
	
	/* The constructor. */
	public CharterBooking(int flight, Operator operator, String name, String addr, String phone, String mail, int noSeats, int number){
		super(flight, noSeats,name, addr, phone, mail, number);
		
		this.operator = operator;
	}
	
}
