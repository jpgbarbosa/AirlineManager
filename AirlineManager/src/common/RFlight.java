package common;

import java.io.Serializable;
import java.util.Calendar;

@SuppressWarnings("serial")
public class RFlight implements Serializable{
	private Airplane airplane;
	private String origin;
	private String destination;
	private String day;
	private int idPlane, idFlight, hour, minute;
	
	public RFlight(Airplane airplane, String origin, String destination, int weekDay, int hour, int minute, int idPlane, int idFlight){
		this.origin = origin;
		this.destination = destination;
		this.idPlane = idPlane;
		this.idFlight = idFlight;
		this.hour = hour;
		this.minute = minute;
		this.airplane = airplane;
		
		if (weekDay == Calendar.SUNDAY)
			day = "Sunday";
		else if (weekDay == Calendar.MONDAY)
			day = "Monday";
		else if (weekDay == Calendar.TUESDAY)
			day = "Tuesday";
		else if (weekDay == Calendar.WEDNESDAY)
			day = "Wednesday";
		else if (weekDay == Calendar.THURSDAY)
			day = "Thursday";
		else if (weekDay == Calendar.FRIDAY)
			day = "Friday";
		else if (weekDay == Calendar.SATURDAY)
			day = "Saturday";
}
	
	public String getOrigin(){
		return origin;
	}
	
	public String getDestination(){
		return destination;
	}
	
	public int getIdPlane(){
		return idPlane;
	}
	
	public int getIdFlight(){
		return idFlight;
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public String getDay(){
		return day;
	}
	
	public String getData(){
		return day + ", " + hour + "h" + minute;
	}
	
	public Airplane getPlane(){
		return airplane;
	}
}
