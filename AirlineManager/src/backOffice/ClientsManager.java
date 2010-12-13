package backOffice;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Map.Entry;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Transaction;

import bookings.Booking;

import common.Client;
import common.Flight;
import common.RFlight;

public class ClientsManager {

	private Hashtable <String, Client> clientsHash;
	
	private Prevayler prevayler;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	
	/* The main constructor. */
	public ClientsManager(){
		//TODO: Change to prevayler.
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Hashtable<String, Client>(),"ClientsList");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		clientsHash = (Hashtable<String, Client>) (prevayler.prevalentSystem());;
	}
	
	
	public void putClient(Client client, Booking booking){
		
		/*Client c = clientsHash.get(client.getEmail());
		
		if (c != null){
			c.increaseKilometers(booking.getPrice() * 10);
		}
		else{
			client.increaseKilometers(booking.getPrice()* 10);
			clientsHash.put(client.getEmail(), client);
		}
		*/
		prevayler.execute(new putClient(client,booking));
		
	}
	
	
	
	public String listClients(){
		String text = "";
		
		for (Entry<String, Client> entry: clientsHash.entrySet()){
			text += entry.getValue().getName() + "\t" + entry.getValue().getEmail()+ "\t" + entry.getValue().getPhoneContact() + "\t"+ entry.getValue().getKilometers() + "km\n";
		}
		
		return text;
	}
	
	public Client searchClient(String email){
		return clientsHash.get(email);
	}
	
	
}

class putClient implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Client client;
	private Booking booking;


	public putClient(Client client, Booking booking){
		this.client=client;
		this.booking=booking;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		Client c = ((Hashtable<String, Client>)arg0).get(client.getEmail());
		
		if (c != null){
			c.increaseKilometers(booking.getPrice() * 10);
		}
		else{
			client.increaseKilometers(booking.getPrice()* 10);
			((Hashtable<String, Client>)arg0).put(client.getEmail(), client);
		}
	}
	
	
}
