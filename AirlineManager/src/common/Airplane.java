package common;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Airplane implements Serializable{
	/* The total amount of seats and the number of occupied seats.
	 * This last parameter may be replaced by finding the size of
	 * the list of clients in this flight.
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The flight associated with this airplane. */
	private int noSeats;
	private Vector <Flight> flights;
	private String company, model;
	private int id;
	private GregorianCalendar date;
	
	
	
	
	/* The constructor. */
	public Airplane(int seatsNumber, String company, String model){
		this.noSeats = seatsNumber;
		this.company = company;
		this.model = model;
		this.flights = new Vector <Flight>();
		id = 0;
	}
	
	/*
	 * Override
	 * */
	public boolean equals(Object a){
		if(((Airplane) a).getId()==this.id){
			return true;
		}
		return false;
	}

	public boolean associateFlight(Flight flight){
		int i;
		for (i = 0; i < flights.size(); i++){
			if (flights.get(i).getDate().after(flight.getDate())){
				flights.add(i, flight);
			}
		}
		/* We insert it in the last position. */
		if (i == flights.size()){
			flights.add(i,flight);
		}
		
		//TODO: Later, we have to confirm that we can associate this flight to this plane.
		//		A reason for failing is that we have two flights too close to each other.
		return true;
	}
	
	public void removeFlight (Flight flight){
		flights.remove(flight);
	}
	
	/* Getters and setters. */
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}

	
	public Vector <Flight> getFlights() {
		return flights;
	}

	public int getId() {
		return id;
	}
	
	public String getCompany() {
		return company;
	}

	public String getModel() {
		return model;
	}
	
	public int getNoSeats() {
		return noSeats;
	}
	
	public void setNoSeats(int noSeats) {
		this.noSeats = noSeats;
	}

	public void setFlights(Vector <Flight> flights) {
		this.flights = flights;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public GregorianCalendar getDate(){
		return date;
	}
	
	public String toString(){
		return "ID: "+ id +"\nNumber of seats: "+ noSeats+"\nCompany: "+ company + "\nModel: "+model+ "\n\n";
	}
	
}
