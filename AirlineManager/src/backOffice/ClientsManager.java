package backOffice;

import java.util.Hashtable;
import java.util.Map.Entry;

import bookings.Booking;

import common.Client;

public class ClientsManager {

	private Hashtable <String, Client> clientsHash;
	
	/* The main constructor. */
	public ClientsManager(){
		//TODO: Change to prevayler.
		clientsHash = new Hashtable<String, Client>();
	}
	
	
	public void putClient(Client client, Booking booking){
		
		Client c = clientsHash.get(client.getName());
		
		if (c != null){
			c.increaseKilometers((int) (booking.getPrice() * 10));
		}
		else{
			clientsHash.put(client.getName(), client);
		}
		
	}
	
	
	public String listClients(){
		String text = "";
		
		for (Entry<String, Client> entry: clientsHash.entrySet()){
			text += entry.getValue().getName() + "\t" + entry.getValue().getEmail()+ "\t" + entry.getValue().getKilometers();
		}
		
		return text;
	}
}
