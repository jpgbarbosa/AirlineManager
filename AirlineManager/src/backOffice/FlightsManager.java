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
	FlightsCleaner flightsCleaner;
	
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
			System.exit(-1);
		} 
		flightsList=(Vector <Flight>) (prevayler.prevalentSystem());
		
		
		if(flightsList.size()>0){
			idCreator=getLastID();
		}
		
		feedBackManager = feed;
		//TODO: prevayler regularFlights and finishedFlights
		
		/*TODO: Temporarily, this will replace the prevayler initialization. */
		finishedFlights = new Vector <Flight>();
		regularFlights = new Hashtable<Integer, Vector<RFlight>>();
		
		/* Calendar.SUNDAY = 1  (...) Calendar.SATURDAY = 7 */
		for (int i = 1; i < 8; i++){
			Vector<RFlight> regularFlightsList = new Vector<RFlight>();
			regularFlights.put(i, regularFlightsList);
		}
		
		flightsCleaner = new FlightsCleaner(this, flightsList, finishedFlights);
		

	}
	
	private int getLastID(){
		int id=0;
		for(Flight a:flightsList)
			if(a.getId()>id){
				id=a.getId();
			}
		id++;
		return id;
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
	/**
	 * 
	 * add a new Booking
	 * @return
	 */
	public void addBookingFlight(Flight id, Booking booking){
		prevayler.execute(new addBookingFlight(id, booking));
		
	}
	
	/**
	 * 
	 * cancels a booking
	 * @return
	 */
	public void removeBookingFlight(Flight id, Booking booking){
		prevayler.execute(new removeBookingFlight(id, booking));
		
	}
	
	public Flight removeFlight(int index){
		Flight flight = flightsList.get(index);
		prevayler.execute(new removeFlight(flightsList.get(index)));
		
		return flight;
		
	}
	
	/* Schedules a new flight. */
	public Flight scheduleFlight(Airplane plane, GregorianCalendar date, String origin, String destination, boolean isRegular, boolean isCharter){
		Flight flight = new Flight(plane, date, origin, destination, isRegular, isCharter);
		int i;
		boolean completed;
		
		flight.setId(idCreator);
		idCreator++;
		/* First, we check if we can insert in this specific plane. */
		completed = plane.associateFlight(flight);
		
		
		if (completed){
			if(isRegular){
				RFlight rflight = new RFlight(origin, destination, date.get(Calendar.DAY_OF_WEEK), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), plane.getId(), flight.getId());

				Vector<RFlight> aux = regularFlights.get(date.get(Calendar.DAY_OF_WEEK));
				for(i=0; i<aux.size();i++){
					if(aux.get(i).getOrigin() == origin && aux.get(i).getDestination() == destination)
						return null;
				}
				regularFlights.get(date.get(Calendar.DAY_OF_WEEK)).add(rflight);
			}
			
			/* Inserts the flight ordered by date. */
			for (i = 0; i < flightsList.size(); i++){
				if (flightsList.get(i).getDate().after(flight.getDate())){
					this.addFlight(i,flight);
					return flight;
				}
			}
			
			/* We insert it in the last position.*/
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
					"The Flight "+flight.getId()+" with destination to "+ flight.getDestination()+", in "+ 
					calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+ " at "+
					calendar.get(Calendar.HOUR_OF_DAY)+":"+(calendar.get(Calendar.MINUTE)+1)+ 
					", was cancelled.\nWe are deeply sorry for all the trouble that might incur.");
				
		}
			
		
		this.removeFlight(flight);
	}
	
	/* Changes a flight's information. */
	public void reScheduleFlight(Flight flight, GregorianCalendar date, Airplane plane){
		Flight temp=flight;
		int i;
		if(date!=null){
			temp.setDate(date);
			this.removeFlight(flight);
			/* Inserts the flight ordered by date. */
			for (i = 0; i < flightsList.size(); i++){
				if (flightsList.get(i).getDate().after(flight.getDate())){
					this.addFlight(i,temp);
					break;
				}
			}
			
			/* We insert it in the last position.*/
			if (i == flightsList.size()){
				addFlight(i,temp);
			}
			
			GregorianCalendar calendar=temp.getDate();
			for(Booking r: temp.getBookings()){
				feedBackManager.sendNotificationUser(r.getEmail(), "Notification", 
						"The Flight "+flight.getId()+" with destination to "+ flight.getDestination()+", in "+ 
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
	
	public String listFlights(){
		String text = "";

		/* Prints the flights. */
		text += "FLIGHTS\n";
		for (int i = 0; i < flightsList.size(); i++){
			Flight flight = flightsList.get(i);
			text += flight.getId()+ "\t" + flight.getAirplane().getId() + "\t" 
					+ flight.getOrigin() + "/" + flight.getDestination() + "\t"+ flight.getData().toString() + "\n";
		}
		
		/* Prints the regular flights. */
		text += "\nREGULAR FLIGHTS\n";
		for (int i = 1; i < 8; i++){
			Vector<RFlight> aux = regularFlights.get(i);
			for (int z = 0; z < aux.size() ;z++){
				RFlight rFlight = aux.get(z);
				text += rFlight.getIdFlight()+ "\t" + rFlight.getIdPlane() + "\t" 
				+rFlight.getOrigin() + "/" + rFlight.getDestination() + "\t"+ rFlight.getData() + "\n";
			}
		}
		
		return text;
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
	
	public FlightsCleaner getFlightsCleaner(){
		return flightsCleaner;
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
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Flight>)arg0).remove(flight);
	}
	
	
}

class addBookingFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Flight id;
	private Booking booking;
	


	public addBookingFlight(Flight id,Booking booking){
		this.id=id;
		this.booking=booking;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		((Vector<Flight>)arg0).get(((Vector<Flight>)arg0).indexOf(id)).newBooking(booking);
		((Vector<Flight>)arg0).get(((Vector<Flight>)arg0).indexOf(id)).increaseOccupied(booking.getNoSeats());
	}
	
	
}

class removeBookingFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Flight id;
	private Booking booking;
	


	public removeBookingFlight(Flight id,Booking booking){
		this.id=id;
		this.booking=booking;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		((Vector<Flight>)arg0).get(((Vector<Flight>)arg0).indexOf(id)).removeBooking(booking);
		((Vector<Flight>)arg0).get(((Vector<Flight>)arg0).indexOf(id)).decreaseOccupied(booking.getNoSeats());
	}
	
	
}

