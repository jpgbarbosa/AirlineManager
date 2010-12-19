//COMPLETELY CHECKED

package clients;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map.Entry;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Transaction;

import bookings.Booking;


/**
 * Class that manages clients.
 * Possesses an Hashtable with the list of the clients that have used our system.
 * This list is updated when a new client makes a new booking.
 */
public class ClientsManager {

	private Hashtable<String, Client> clientsHash;

	private Prevayler prevayler;

	/**
	 * Creates a new prevayler to store clientsHash.
	 */
	@SuppressWarnings("unchecked")
	public ClientsManager() {
		try {
			prevayler = PrevaylerFactory.createPrevayler(
					new Hashtable<String, Client>(), "ClientsList");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		clientsHash = (Hashtable<String, Client>) (prevayler.prevalentSystem());
		;
	}

	public void putClient(Client client, Booking booking) {
		prevayler.execute(new putClient(client, booking));

	}

	/**
	 * get clients list
	 * 
	 * @return String with clients list
	 */
	public String listClients() {
		String text = "";

		for (Entry<String, Client> entry : clientsHash.entrySet()) {
			text += entry.getValue().getName() + "\t"
					+ entry.getValue().getEmail() + "\t"
					+ entry.getValue().getPhoneContact() + "\t"
					+ entry.getValue().getKilometers() + "km\n";
		}

		return text;
	}

	/**
	 * Searches a client by his email
	 * 
	 * @param String email
	 * @return Client
	 */
	public Client searchClient(String email) {
		return clientsHash.get(email);
	}

	public Prevayler getPrevayler() {
		return prevayler;
	}

}

class putClient implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Client client;
	private Booking booking;

	public putClient(Client client, Booking booking) {
		this.client = client;
		this.booking = booking;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		Client c = ((Hashtable<String, Client>) arg0).get(client.getEmail());

		if (c != null) {
			c.increaseKilometers(booking.getPrice() * 10);
		} else {
			client.increaseKilometers(booking.getPrice() * 10);
			((Hashtable<String, Client>) arg0).put(client.getEmail(), client);
		}
	}

}
