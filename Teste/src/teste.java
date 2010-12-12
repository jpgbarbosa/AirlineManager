import java.util.Calendar;
import java.util.GregorianCalendar;


public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GregorianCalendar data = new GregorianCalendar();
		
		System.out.println("The data is: " + data.getTime().toString());
		data.add(Calendar.DAY_OF_MONTH, 7);
		System.out.println("The data is: " + data.getTime().toString());
		
		
		data = new GregorianCalendar();
		
		System.out.println("2.The data is: " + data.getTime().toString());
		data.set(Calendar.MINUTE, 1);
		System.out.println("2.The data is: " + data.getTime().toString());
		
		data = new GregorianCalendar(2010,10,9);
		data.set(Calendar.HOUR_OF_DAY, 23);
		data.set(Calendar.MINUTE, 59);
		data.set(Calendar.SECOND, 59);
		System.out.println("3.The data is: " + data.getTime().toString());
		
	}

}
