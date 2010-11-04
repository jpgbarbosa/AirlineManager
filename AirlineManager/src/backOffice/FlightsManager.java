package backOffice;

import java.util.Vector;

import common.Flight;

public class FlightsManager {
	/* The list of all(?) the flights in the system. */
	private Vector<Flight> flightsList;
	
	/* The constructor. */
	public FlightsManager(){
		
	}
	
	/* Schedules a new flight. */
	public void scheduleFlight(){
		
	}
	
	/* Cancels a specific flight.  */
	public void cancelFlight(Flight flight){
		
	}
	
	/* Changes a flight's information. */
	public void reScheduleFlight(Flight flight){
		
	}

	public Vector<Flight> getFlightsList() {
		return flightsList;
	}

	public void setFlightsList(Vector<Flight> flightsList) {
		this.flightsList = flightsList;
	}
	
	public int getNumFlights(){
		return flightsList.size();
	}
	
	public int getNumCancelled(){
		
		return 0;
	}
	
	public int getOccupation(){
		
		return 0;
	}
	
}
