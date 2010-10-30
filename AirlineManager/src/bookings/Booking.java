package bookings;

import common.Client;
import common.Flight;

public abstract class Booking {
	/* The flight and the client associated with this booking. */
	protected Flight flight;
	protected Client client;
	
	/* The constructor. */
	public Booking(Flight flight, Client client){
		this.flight = flight;
		this.client = client;
		
	}
	
}
