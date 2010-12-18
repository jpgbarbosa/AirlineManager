package common;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

/**
 *	This class is represents each airplane. An airplane has a list of schedule flights, 
 *	the respective company, airplane model, maximum number of seats and an ID. 
 *
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author João Penetra
 * @author João Barbosa
 * @author Ricardo Bernardino
 *
 */
public class Airplane implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Number of Seats
	 */
	private int noSeats;
	/**
	 * flights associated to this plane
	 */
	private Vector <Flight> flights;
	
	private String company, model;
	private int id;
	
	private GregorianCalendar date;
	
	/**
	 * The Airplane constructor is responsible for creating a new airplane with a certain number of seats,
	 * a company and its model.
	 * 
	 * @param seatsNumber
	 * @param company
	 * @param model
	 */
	public Airplane(int seatsNumber, String company, String model){
		this.noSeats = seatsNumber;
		this.company = company;
		this.model = model;
		this.flights = new Vector <Flight>();
		id = 0;
	}
	
	/**
	 * This is an override of method equals. It compares two airplanes by their id.
	 * 
	 * @param Object a - Airplane;
	 */
	@Override
	public boolean equals(Object a){
		if(((Airplane) a).getId()==this.id){
			return true;
		}
		return false;
	}

	/**
	 * Inserts the flight in the flights' list ordered by date.
	 * 
	 * @param flight
	 */
	public void associateFlight(Flight flight){
		int i;
		for (i = 0; i < flights.size(); i++){
			if (flights.get(i).getDate().after(flight.getDate())){
				flights.add(i, flight);
				break;
			}
		}
		/* We insert it in the last position. */
		if (i == flights.size()){
			flights.add(flight);
		}
	}
	
	/**
	 * 
	 * @param flight
	 */
	public void removeFlight (Flight flight){
		flights.remove(flight);
	}
	
	/* Getters and setters. */
	
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
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
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
