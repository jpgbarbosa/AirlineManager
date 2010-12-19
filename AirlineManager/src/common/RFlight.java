package common;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class that represents a regular flight.
 */
@SuppressWarnings("serial")
public class RFlight implements Serializable {
	private Airplane airplane;
	private String origin;
	private String destination;
	private String day;
	private int idPlane, idFlight, hour, minute, weekDay;

	/**
	 * The Main Constructor.
	 * 
	 * @param airplane
	 * @param origin
	 * @param destination
	 * @param weekDay
	 * @param hour
	 * @param minute
	 * @param idPlane
	 * @param idFlight
	 */
	public RFlight(Airplane airplane, String origin, String destination,
			int weekDay, int hour, int minute, int idPlane, int idFlight) {
		this.origin = origin;
		this.destination = destination;
		this.idPlane = idPlane;
		this.idFlight = idFlight;
		this.hour = hour;
		this.minute = minute;
		this.airplane = airplane;
		this.weekDay = weekDay;
		translateWeekDayToString(weekDay);
		
	}

	/**
	 * Given a week day in number format, saves its correspondent string
	 * in the variable day.
	 * 
	 * @param int weekDay
	 */
	public void translateWeekDayToString(int weekDay){
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
	
	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public int getIdPlane() {
		return idPlane;
	}

	public int getIdFlight() {
		return idFlight;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public String getDay() {
		return day;
	}

	public String getData() {
		return day + ", " + hour + "h" + minute;
	}

	public Airplane getPlane() {
		return airplane;
	}

	public int getWeekDay() {
		return weekDay;
	}
	
	public void setDate(GregorianCalendar date){
		hour = date.get(Calendar.HOUR_OF_DAY);
		minute = date.get(Calendar.MINUTE);
		translateWeekDayToString(date.get(Calendar.DAY_OF_WEEK));
	}
}
