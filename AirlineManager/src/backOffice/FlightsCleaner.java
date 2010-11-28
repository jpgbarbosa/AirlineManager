package backOffice;

import java.util.GregorianCalendar;
import java.util.Vector;

import common.Flight;
import common.Lock;

public class FlightsCleaner extends Thread{
	private Vector<Flight> flightsList;
	private Vector<Flight> finishedFlights;
	public Lock lock = new Lock();
	
	public FlightsCleaner(Vector<Flight> list, Vector<Flight> finishedList){
		flightsList = list;
		finishedFlights = finishedList;
		
		this.start();
		
	}
	
	public void run(){
		while(true){
			Flight flight;
			GregorianCalendar date = new GregorianCalendar();
			
			int counter = 0;
			
			for (int i = 0; i < flightsList.size() && flightsList.get(i).getDate().before(date); i++){
				flight = flightsList.remove(i);
				finishedFlights.add(flight);
				i--;
				counter++;
			}
			
			//System.out.println("The counter is " + counter + " and the size is " + flightsList.size() + ".");
			
			if (counter > 0){
				//TODO: Activar a imagem.
				try {
					/* Shows the warning for 5 seconds. */
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			try {
				sleep(60000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//TODO: Clarify this.
			/*if (flightsList.size() != 0){
				System.out.println("Sleeping");
				try{
					sleep(flightsList.get(0).getDate().getTimeInMillis() - (new GregorianCalendar()).getTimeInMillis());
				}catch(InterruptedException e){
					// If we were interrupted, we have to evaluate if the new flight has
					// past the current time.
					System.out.println("Was interrupted");
				}
			}
			else{
				System.out.println("Waiting");
				synchronized(lock){
					try {
						lock.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			*/
		}
	}
}
