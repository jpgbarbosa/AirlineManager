package common;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import bookings.Booking;

public class Flight {
	/* The list of bookings registered for this flight. */
	private Vector <Booking> seats;
	private int occupied;
	/* The list of waiting clients. */
	//private Vector <Booking> waitingList;
	/* The airplane associated to this flight. */
	private Airplane airplane;
	private GregorianCalendar date;
	private String origin;
	private String destination;
	private boolean isRegular;
	private boolean wasCancelled;
	private boolean isClosed;
	private int id;
	public Object lock;
	
	//TODO: This is temporary!
	public static int idCreator = 0;
	
	/* The constructor. */
	public Flight(Airplane plane, GregorianCalendar data, String origin, String dest, boolean isRegular){
		airplane = plane;
		this.origin = origin;
		destination = dest;
		seats = new Vector <Booking>();
		date = data;
		this.isRegular = isRegular;
		wasCancelled = false;
		isClosed = false;
		id = idCreator++;
	}
	
	/*Override .equals()*/
	public boolean equals(Object obj){
		Flight f=(Flight) obj;
		if(airplane.getId()==f.getAirplane().getId()&&destination.equals(f.getDestiny())){
			if(date.get(Calendar.DAY_OF_YEAR)==f.getDate().get(Calendar.DAY_OF_YEAR)&&
					date.get(Calendar.YEAR)==f.getDate().get(Calendar.YEAR)&&
					date.get(Calendar.HOUR)==f.getDate().get(Calendar.HOUR))
				return true;
		}
		
		return false;
	}
	
	/* Adds a new booking to the flight. */
	public void newBooking (Booking booking){
		seats.add(booking);
	}
	
	/* Removes a booking from the flight. */
	public boolean removeBooking (Booking booking){
		return seats.remove(booking);
	}

	/* Getters and setters. */
	public Vector <Booking> getSeats() {
		return seats;
	}

	public Airplane getAirplane() {
		return airplane;
	}

	public GregorianCalendar getDate() {
		return date;
	}
	
	public Date getData() {
		return date.getTime();
	}

	public int getId() {
		return id;
	}
	
	public String getDestiny() {
		return destination;
	}

	public void setSeats(Vector <Booking> seats) {
		this.seats = seats;
	}

	public void setAirplane(Airplane airplane) {
		this.airplane = airplane;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setDestiny(String destiny) {
		this.destination= destiny;
	}
	
	/* Checks whether is flight is full or not. */
	public boolean isFull(){
		/* We have no more seats on this flight. */
		if (airplane.getNoSeats() - seats.size() == 0){
			isClosed = true;
			return true;
		}
		
		/* We still have at least one empty seat. */
		return false;
	}

	public int getOccupiedSeats() {
		return seats.size();
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public boolean isRegular() {
		return isRegular;
	}

	public void setRegular(boolean isRegular) {
		this.isRegular = isRegular;
	}

	public boolean isWasCancelled() {
		return wasCancelled;
	}

	public void setWasCancelled(boolean wasCancelled) {
		this.wasCancelled = wasCancelled;
	}

	public boolean isClosed() {
		return isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}

	public static int getIdCreator() {
		return idCreator;
	}
	
	public String toString(){
		return "ID: "+ id + "\n";
	}
	
	public void increaseOccupied(){
		occupied++;
	}
	
	public void increaseOccupied(int no){
		occupied += no;
	}
	
	public void decreaseOccupied(){
		occupied--;
	}
	
	public void decreaseOccupied(int no){
		occupied -= no;
	}
	
	
}
