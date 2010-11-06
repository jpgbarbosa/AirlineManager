package backOffice;

import java.util.GregorianCalendar;
import java.util.Vector;

import common.Airplane;
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
	public Flight scheduleFlight(Airplane plane, GregorianCalendar date, String destiny){
		Flight flight = new Flight(plane, date, destiny);
		int i;
		boolean completed;
		
		/* First, we check if we can insert in this specific plane. */
		completed = plane.associateFlight(flight);
		
		if (completed){
			/* Inserts the flight ordered by date. */
			for (i = 0; i < flightsList.size(); i++){
				if (flightsList.get(i).getDate().after(flight.getDate())){
					flightsList.add(i, flight);
				}
			}
			
			/* We insert it in the last position. */
			if (i == flightsList.size()){
				flightsList.add(i,flight);
			}
			
			return flight;
		}
		else{
			return null;
		}
	}
	
	/* Search a flight by Date and plane*/
	public Flight searchFlightByDate(Airplane plane, GregorianCalendar date){
		for(int i=0; i<flightsList.size(); i++){
			/* TODO: Check if we need to override .equals methods*/
			if(flightsList.get(i).getAirplane().equals(plane) && 
					flightsList.get(i).getDate().equals(date))
				return flightsList.get(i);
		}		
		return null;
	}
	
	/* Search a flight by ID*/
	public Flight searchFlightById(int id){		
		for(int i=0; i<flightsList.size(); i++){
			if(flightsList.get(i).getId() == id)
				return flightsList.get(i);
		}
		return null;
	}
	
	/* Cancels a specific flight.  */
	public void cancelFlight(Flight flight){
		/*
		 * TODO: Change and Warn passengers!!
		 */
		
		flightsList.remove(flight);
	}
	
	/* Changes a flight's information. */
	public void reScheduleFlight(Flight flight, GregorianCalendar date, Airplane plane){
		if(date!=null){
			flight.setDate(date);
		}
		if(plane != null){
			flight.getAirplane().getFlights().remove(flight);
			flight.setAirplane(plane);
			plane.getFlights().add(flight);
			/* TODO: Change fields like this may lead to some problems. Check!*/
		}
		
		/* TODO: Warn Clients!! */
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
