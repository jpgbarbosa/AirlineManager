package backOffice;

import java.util.Vector;

import common.Flight;
import common.FileManager;

public class FlightsManager {
	/* The list of all(?) the flights in the system. */
	private Vector<Flight> flightsList;
	
	/* The constructor. */
	public FlightsManager(){
		if(FileManager.loadObjectFromFile("flightsList", flightsList) == null)
			flightsList = new Vector<Flight>();
	}
	
	/* Schedules a new flight. */
	public void scheduleFlight(){
		
	}
	
	/* Search a fligh */
	public Flight searchFlight(){
		return null;
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
