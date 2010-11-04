package common;

import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

import backOffice.FlightsManager;
import backOffice.PlanesManager;
import bookings.Booking;


public class Search {
	private FlightsManager flightsManager;
	private PlanesManager planesManager;
	
	/* The constructor. */
	public Search(FlightsManager fM, PlanesManager pM){
		flightsManager = fM;
		planesManager = pM;
	}
	
	/* A method to search for a flight by plane. */
	public Airplane searchFlight(int planeId){
		Iterator<Airplane> it = planesManager.getPlanesList().iterator();
		
		while (it.hasNext()){
			Airplane plane = it.next();
			
			/* If the ID's match, we have found our plane. */
			if (plane.getId() == planeId){
				return plane;
			}
		}
		
		/* There was no plane with this ID. */
		return null;
	}
	
	/* A method to list all the flights for a given date. */
	public Vector<Flight> listFlightsByDate(GregorianCalendar data){
		
		Vector<Flight> finalList = new Vector<Flight>();
		
		/* TODO: Very inefficient method. We can organize the linked list so
		 * we won't look up in the entire structure.
		 */
		/* Collects the list of all flights and creates an iterator over it. */
		Iterator<Flight> it = flightsManager.getFlightsList().iterator();
		while (it.hasNext()){
			Flight flight = it.next();
			
			/* If the two dates match, we add this flight to the final vector. */
			if (flight.getDate().equals(data)){
				finalList.add(flight);
			}
		}
		
		/* If we have elements on the list, we can return the vector.
		 * Otherwise, we simply return null, indicating there were no members.
		 * TODO: Verify if it's better to return null or the vector with 0 flights.
		 */
		if (finalList.size() != 0){
			return finalList;
		}
		return null;
		
	}
	
	/* A method to search for a list of bookings in a flight.
	 * This method is likely to be used only by the BackOffice.
	 */
	public Vector<Booking> listBookingsFlight(Flight flight){
		
		return null;
	}
	
}
