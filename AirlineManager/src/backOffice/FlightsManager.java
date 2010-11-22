package backOffice;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import org.prevayler.*;

import common.Airplane;
import common.Flight;
import common.FileManager;

public class FlightsManager {
	/* The list of all(?) the flights in the system. */
	private Vector<Flight> flightsList;
	private GregorianCalendar[] cancelledFlights;
	private Prevayler prevayler;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	/* The constructor. */
	public FlightsManager(){
		super();
		
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Vector<Flight>(), "FlightsList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went really bad!");
			System.exit(0);
		} 
		flightsList=(Vector <Flight>) (prevayler.prevalentSystem());
		
		
		
		/*
		 * if(FileManager.loadObjectFromFile("flightsList", flightsList) == null)
		 		flightsList = new Vector<Flight>();
		 		
		 */
	}
	
	/**
	 * 
	 * Write Transactions
	 */
	
	/*
	 * Adds a flight in the flight list
	 */
	private void addFlight(int index, Flight flight){
		
	}
	
	/*
	 * Removes a flight from the flight list
	 */
	private void removeFlight(Flight flight){
		
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
					addFlight(i,flight);
				}
			}
			
			/* We insert it in the last position. */
			if (i == flightsList.size()){
				addFlight(i,flight);
			}
			
			return flight;
		}
		else{
			return null;
		}
	}
	
	/* Cancels a specific flight.  */
	public void cancelFlight(Flight flight){
		/*
		 * TODO: Change and Warn passengers!!
		 */
		
		this.removeFlight(flight);
	}
	
	/* Changes a flight's information. */
	public void reScheduleFlight(Flight flight, GregorianCalendar date, Airplane plane){
		int index;
		if(date!=null){
			flight.setDate(date);
		}
		if(plane != null){
			flight.getAirplane().getFlights().remove(flight);
			flight.setAirplane(plane);
			plane.getFlights().add(flight);
			/* TODO: Change fields like this may lead to some problems. Check!*/
			/* TODO: Check!*/
			/* TODO: Check!*/
			/* TODO: Check!*/
		}
		
		/* TODO: Warn Clients!! */
	}
	
	/**
	 * 
	 * Read Transactions
	 */
	
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
	

	public Vector<Flight> getFlightsList() {
		return flightsList;
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
		int sum = 0;
		int total = flightsList.size();
		for(Flight f:flightsList){
			sum+=f.getOccupiedSeats()/f.getAirplane().getNoSeats()*100;
		}
		return sum/total;
	}
	
	public int getOccupation(GregorianCalendar beginning, GregorianCalendar end){
		int sum=0;
		int total=0;
		Flight aux;
		
		for(int i=0; i<flightsList.size() ;i++){
			aux = flightsList.get(i);
			if(aux.getDate().before(beginning)){
				
			} else if(aux.getDate().after(beginning) && aux.getDate().before(end)){
				total++;
				sum+=aux.getOccupiedSeats()/aux.getAirplane().getNoSeats()*100;
			} else if(aux.getDate().after(end)) {
				break;
			}
		}
		return sum/total;
	}
	
}

class addFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Flight flight;
	private int index;


	public addFlight(int index,Flight flight){
		this.flight=flight;
		this.index=index;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Flight>)arg0).add(index,flight);
	}
	
	
}

class removeFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Flight flight;
	


	public removeFlight(Flight flight){
		this.flight=flight;
		
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Flight>)arg0).remove(flight);
	}
	
	
}
