package backOffice;

import java.rmi.RemoteException;
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
	private Prevayler prevaylerFinished;
	private Prevayler prevaylerRegular;
	//TODO: usar o prevayler para gravar isto
	private Hashtable<Integer, Vector<RFlight>> regularFlights;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	public Prevayler getPrevaylerFinished() {
		return prevaylerFinished;
	}
	public Prevayler getPrevaylerRegular() {
		return prevaylerRegular;
	}
	
	/* The constructor. */
	@SuppressWarnings("unchecked")
	public FlightsManager(FeedBackManager feed){
		super();
		
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Vector<Flight>(), "FlightsList");
			prevaylerFinished = PrevaylerFactory.createPrevayler(new Vector<Flight>(), "FinishedFlightsList");
			prevaylerRegular = PrevaylerFactory.createPrevayler(new Hashtable<Integer, Vector<RFlight>>(), "RegularFlightsList");
			
		} catch (Exception e) {
			System.out.println("Prevayler error, exiting.");
			System.exit(-1);
		} 
		flightsList=(Vector <Flight>) (prevayler.prevalentSystem());
		finishedFlights=(Vector <Flight>) (prevaylerFinished.prevalentSystem());
		regularFlights=(Hashtable<Integer, Vector<RFlight>>)(prevaylerRegular.prevalentSystem());
		
		if(flightsList.size()>0){
			idCreator=getLastID();
		}
		
		feedBackManager = feed;
		//TODO: prevayler regularFlights and finishedFlights
	
		
		/* Calendar.SUNDAY = 1  (...) Calendar.SATURDAY = 7 */
		if(regularFlights.size()==0){
			for (int i = 1; i < 8; i++){
			 
				Vector<RFlight> regularFlightsList = new Vector<RFlight>();
				regularFlights.put(i, regularFlightsList);
			}
		}
		flightsCleaner = new FlightsCleaner(this);
		

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
		feedBackManager.sendNotificationUser(booking.getClient().getEmail(), "NEW BOOKING",
				"Reservation under the name of: "+booking.getClient().getName()+
				"\nSecret Boarding Code: "+booking.getHashBoardingTicket());
		
	}
	
	public void putRegularFlight(int id, Vector<RFlight> rfs){
		
		prevaylerRegular.execute(new putRegularFlight(id, rfs));
		
	}
	//TODO:
	public void addRegularFlight(int id, RFlight rf){
			
			prevaylerRegular.execute(new addRegularFlight(id, rf));
			
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
				RFlight rflight = new RFlight(plane, origin, destination, date.get(Calendar.DAY_OF_WEEK), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), plane.getId(), flight.getId());

				Vector<RFlight> aux = regularFlights.get(date.get(Calendar.DAY_OF_WEEK));
				for(i=0; i<aux.size();i++){
					if(aux.get(i).getOrigin() == origin && aux.get(i).getDestination() == destination)
						return null;
				}
				this.addRegularFlight(date.get(Calendar.DAY_OF_WEEK), rflight);
				
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
	
	/* Schedules a new flight. */
	public Flight reScheduleRFlight(Airplane plane, int idRFlight, GregorianCalendar date, String origin, String destination, boolean isCharter){
		Flight flight = new Flight(plane, date, origin, destination, true, isCharter);
		int i;
		boolean completed;
		
		flight.setId(idRFlight);
		/* First, we check if we can insert in this specific plane. */
		completed = plane.associateFlight(flight);
		
		
		if (completed){
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
			feedBackManager.sendNotificationUser(r.getClient().getEmail(), "Notification", 
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
				feedBackManager.sendNotificationUser(r.getClient().getEmail(), "Notification", 
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
					+ flight.getOrigin() + "/" + flight.getDestination() + "\t"+ flight.getData().toString() + "   " + flight.getOccupiedSeats() + "/" + flight.getAirplane().getNoSeats() + "\n";
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
	
	public String findFlights(int year, int month, int day, String origin, String destination){
		String text = "";
		GregorianCalendar data = new GregorianCalendar(year, month - 1, day);
		int weekDay = data.get(Calendar.DAY_OF_WEEK);
		int i = 0;
		
		data.set(Calendar.HOUR_OF_DAY, 23);
		data.set(Calendar.MINUTE, 59);
		data.set(Calendar.SECOND, 59);
		

		while (i < flightsList.size() && flightsList.get(i).getDate().before(data)){
			Flight flight = flightsList.get(i);
			GregorianCalendar flightDate = flight.getDate();
			
			/* First, gets the normal flights. */
			if (flightDate.get(Calendar.YEAR) == year &&
					flightDate.get(Calendar.MONTH) + 1 == month &&
						flightDate.get(Calendar.DAY_OF_MONTH) == day){

				/* If the flight is regular, it will be joined to the list later.*/
				if (flight.getOrigin().equals(origin) &&
						flight.getDestination().equals(destination) &&
							!flight.isFull() &&
								!flight.isRegular()){
					/* This flight matches all the criteria and consequently
					 * can be added to the list.
					 */
					text += "FLIGHT ID: " + flight.getId() + "\n";
				}
			}
			i++;
		}
		
		/* Then, the regular ones. */
		Vector<RFlight> rFlights = regularFlights.get(weekDay);
		
		for (i = 0; i < rFlights.size(); i++){
			text += "FLIGHT ID: " + rFlights.get(i).getIdFlight() + "\n";
		}
		
		if (text.equals("")){
			text += "There are no flights for this pair origin/destination in this date.";
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
	
	/* Search a normal flight by ID*/
	public Flight searchFlightById(int id){		
		for(int i=0; i<flightsList.size(); i++){
			if(flightsList.get(i).getId() == id)
				return flightsList.get(i);
		}
		return null;
	}
	
	/* Search a regular flight by ID*/
	public RFlight searchRFlightById(int id){				
		for (int i = 1; i < 8; i++){
			Vector<RFlight> aux = regularFlights.get(i);
			for (int z = 0; z < aux.size() ;z++){
				RFlight rFlight = aux.get(z);
				if(rFlight.getIdFlight() == id)
					return rFlight;
			}
		}
		
		return null;
	}
	

	public Vector<Flight> getFlightsList() {
		return flightsList;
	}
	
	public Vector<Flight> getFinishedFlights() {
		return finishedFlights;
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

	public void addFinished(Flight flight) {
		// TODO Auto-generated method stub
		prevaylerFinished.execute(new addFinished(flight));
		return;
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

class addFinished implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Flight flight;
	


	public addFinished(Flight flight){
		this.flight=flight;
		
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		((Vector<Flight>)arg0).add(flight);
	}
	
	
}




class putRegularFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Vector<RFlight> rfs;
	private int id;


	public putRegularFlight(int id, Vector<RFlight> rfs){
		this.id=id;
		this.rfs=rfs;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		((Hashtable<Integer, Vector<RFlight>>)arg0).put((Integer)id, rfs);
	}
	
	
}




class addRegularFlight implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private RFlight rfs;
	private int id;


	public addRegularFlight(int id, RFlight rfs){
		this.id=id;
		this.rfs=rfs;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		((Hashtable<Integer, Vector<RFlight>>)arg0).get((Integer)id).add(rfs);
	}
	
	
}
