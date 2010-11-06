package backOffice;

import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import messages.Feedback;
import messages.Notification;

import common.Client;


public class FeedBackManager {
	/* The list messages (feed back) sent by the clients.
	 * The feed back can be either positive or negative, so we use two lists
	 * to keep them separated.
	 */
	private Vector <Feedback> positiveFeedBackList;
	private Vector <Feedback> negativeFeedBackList;
	
	/* The constructor. */
	public FeedBackManager(){
		//TODO: Later, we have to read it from a file.

		positiveFeedBackList = new Vector <Feedback>();
		negativeFeedBackList = new Vector <Feedback>();
	}
	
	/* Sends a notification to a specific client. */
	public boolean sendNotificationUser (Client client, String type, String content){
		//TODO: send e-mail
		
		try {
			SendEmail.send("smtp@sapo.pt", 25, "airlinemanager@fakemail.com", client.getEmail(), "Notification", content);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
		
	}
	
	/* Sends a notification to all the clients. */
	public void sendNotificationAll(Vector <Client> listaClientes, String type, String content){
		
	
		for(Client c : listaClientes){
			try {
				SendEmail.send("smtp@sapo.pt", 25, "airlinemanager@fakemail.com", c.getEmail(), "Notification", content);
			} catch (AddressException e) {
			} catch (MessagingException e) {}
			
		}
		
	
	}
	
	/* Inserts a new message in the positive feed back list. */
	public void insertPositiveFeedback(Feedback feedBack){
		positiveFeedBackList.add(feedBack);
	}
	
	/* Inserts a new message in the negative feed back list. */
	public void insertNegativeFeedback(Feedback feedBack) {
		negativeFeedBackList.add(feedBack);
		
	}
	
	/* Reads the positive feed back provided by the clients. */
	public Vector <Feedback> getPositiveFeedBackList(){
		return positiveFeedBackList;
	}
	
	/* Reads the negative feed back provided by the clients. */
	public Vector <Feedback> getNegativeFeedBackList(){
		return negativeFeedBackList;
	}
	
	public int getNumPositive(){
		return positiveFeedBackList.size();
	}
	
	public int getNumPositive(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Feedback f:positiveFeedBackList){
			if(f.getDate().after(beginning) && f.getDate().before(end))
				num++;
		}
		
		return num;
	}
	
	public int getNumNegative(){
		return negativeFeedBackList.size();
	}
	
	public int getNumNegative(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Feedback f:negativeFeedBackList){
			if(f.getDate().after(beginning) && f.getDate().before(end))
				num++;
		}
		
		return num;
	}
	
}
