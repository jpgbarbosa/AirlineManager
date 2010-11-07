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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
}
