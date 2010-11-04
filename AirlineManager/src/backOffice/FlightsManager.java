package backOffice;

import java.util.GregorianCalendar;
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
	
	public int getNumFlights(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Flight f:flightsList){
			if(f.getDate().after(beginning) && f.getDate().before(end)){
				num++;
			}
		}
		
		return num;
	}
	
	public int getNumCancelled(){
		
		return 0;
	}
	
	public int getNumCancelled(GregorianCalendar beginning, GregorianCalendar end){
		
		return 0;
	}
	
	public int getOccupation(){
		
		return 0;
	}
	
	public int getOccupation(GregorianCalendar beginning, GregorianCalendar end){
		
		return 0;
	}
	
}
