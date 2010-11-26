package backOffice;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Vector;
import org.prevayler.*;

import bookings.Booking;

import common.Airplane;
import common.Flight;

import common.RFlight;

public class FlightsManager {
	FeedBackManager feedBackManager;
	
	/* The list of all(?) the flights in the system. */
	
	private Vector<Flight> flightsList;
	private Vector<Flight> finishedFlights;
	public static int idCreator = 0;
	private Prevayler prevayler;
	//TODO: usar o prevayler para gravar isto
	private Hashtable<Integer, Vector<RFlight>> regularFlights;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	
	/* The constructor. */
	@SuppressWarnings("unchecked")
	public FlightsManager(FeedBackManager feed){
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
		if(flightsList.size()>0)
			for(Flight f : flightsList){
				if(f.getId()>idCreator)
					idCreator=f.getId()+1;
			}
		
		feedBackManager = feed;
		//TODO: prevayler regularFlights
	}
	
	/**
	 * 
	 * Write Transactions
	 */
	
	/*
	 * Adds a flight in the flight list
	 */
	private void addFlight(int index, Flight flight){
		prevayler.execute(new addFlight(index,flight));
	}
	
	/*
	 * Removes a flight from the flight list
	 */
	private void removeFlight(Flight flight){
		prevayler.execute(new removeFlight(flight));
		
	}
	
	/* Schedules a new flight. */
	public Flight scheduleFlight(Airplane plane, GregorianCalendar date, String origin, String destination, boolean isRegular){
		Flight flight = new Flight(plane, date, origin, destination, isRegular);
		int i;
		boolean completed;
		
		
		flight.setId(idCreator++);
		/* First, we check if we can insert in this specific plane. */
		completed = plane.associateFlight(flight);
		
		if (completed){
			if(isRegular){
				//TODO: Change origin
				RFlight rflight = new RFlight(origin, destination);
				Vector<RFlight> aux = regularFlights.get(date.DAY_OF_WEEK);
				for(i=0;i<aux.size();i++){
					if(aux.get(i).getOrigin()==origin && aux.get(i).getDestination()==destination)
						return null;
				}
				regularFlights.get(date.DAY_OF_WEEK).add(rflight);
			}
			
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
		 * DONE: Change and Warn passengers!!
		 */
		GregorianCalendar calendar=flight.getDate();
		for(Booking r: flight.getBookings()){
			feedBackManager.sendNotificationUser(r.getEmail(), "Notification", 
					"The Flight "+flight.getId()+" with destination to "+ flight.getDestiny()+", in "+ 
					calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+ " at "+
					calendar.get(Calendar.HOUR_OF_DAY)+":"+(calendar.get(Calendar.MINUTE)+1)+ 
					", was cancelled.\nWe are deeply sorry for all the trouble that might incur.");
				
		}
			
		
		this.removeFlight(flight);
	}
	
	/* Changes a flight's information. */
	public void reScheduleFlight(Flight flight, GregorianCalendar date, Airplane plane){
		if(date!=null){
			flight.setDate(date);
			/* TODO: Warn Clients!! */
			GregorianCalendar calendar=flight.getDate();
			for(Booking r: flight.getBookings()){
				feedBackManager.sendNotificationUser(r.getEmail(), "Notification", 
						"The Flight "+flight.getId()+" with destination to "+ flight.getDestiny()+", in "+ 
						calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+ " at "+
						calendar.get(Calendar.HOUR_OF_DAY)+":"+(calendar.get(Calendar.MINUTE)+1)+ 
						", was rescheduled.\nWe are deeply sorry for all the trouble that might incur.");
					
			}
		}
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
		if (total==0)
			return 0;
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
		if (total==0)
			return 0;
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
