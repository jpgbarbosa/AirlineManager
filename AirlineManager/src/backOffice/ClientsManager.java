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
		
		Client c = clientsHash.get(client.getEmail());
		
		if (c != null){
			c.increaseKilometers(booking.getPrice() * 10);
		}
		else{
			client.increaseKilometers(booking.getPrice()* 10);
			clientsHash.put(client.getEmail(), client);
		}
		
	}
	
	
	public String listClients(){
		String text = "";
		
		for (Entry<String, Client> entry: clientsHash.entrySet()){
			text += entry.getValue().getName() + "\t" + entry.getValue().getEmail()+ "\t" + entry.getValue().getPhoneContact() + "\t"+ entry.getValue().getKilometers() + "km\n";
		}
		
		return text;
	}
}
