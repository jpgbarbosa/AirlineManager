package backOffice;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Transaction;

import messages.Feedback;

import common.Client;
import common.FileManager;
import common.Flight;


public class FeedBackManager {
	private FeedBackStorage msgStorage;
	private Prevayler prevayler;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	
	
	/* The constructor. */
	public FeedBackManager(){
		try {
			prevayler = PrevaylerFactory.createPrevayler(new FeedBackStorage(), "MessageStorage");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went really bad!");
			System.exit(0);
		} 
		msgStorage=(FeedBackStorage) (prevayler.prevalentSystem());
	}
	
	/* Sends a notification to a specific client. */
	public static boolean sendNotificationUser (Client client, String type, String content){
		//TODO: send e-mail
		
		try {
			SendEmail.send("smtp.sapo.pt", 25, "airlinemanager@fakemail.com", client.getEmail(), "Notification", content);
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
	public static void sendNotificationAll(Vector <Client> listaClientes, String type, String content){
		
		
		for(Client c : listaClientes){
			try {
				SendEmail.send("smtp.sapo.pt", 25, "airlinemanager@fakemail.com", c.getEmail(), "Notification", content);
			} catch (AddressException e) {
			} catch (MessagingException e) {}
			
		}
		
	
	}
	
	/* Inserts a new message in the positive feed back list. */
	public void insertPositiveFeedback(Feedback feedBack){
		prevayler.execute(new insertPositiveFeedback(feedBack));
	}
	
	/* Inserts a new message in the negative feed back list. */
	public void insertNegativeFeedback(Feedback feedBack) {
		prevayler.execute(new insertNegativeFeedback(feedBack));
	}
	
	/* Reads the positive feed back provided by the clients. */
	public Vector <Feedback> getPositiveFeedBackList(){
		return msgStorage.getPositiveFeedBackList();
	}
	
	/* Reads the negative feed back provided by the clients. */
	public Vector <Feedback> getNegativeFeedBackList(){
		return msgStorage.getNegativeFeedBackList();
	}
	
	public int getNumPositive(){
		return msgStorage.getNumPositive();
	}
	
	public int getNumPositive(GregorianCalendar beginning, GregorianCalendar end){
		return msgStorage.getNumPositive(beginning, end);
	}
	
	public int getNumNegative(){
		return msgStorage.getNumNegative();
	}
	
	public int getNumNegative(GregorianCalendar beginning, GregorianCalendar end){
		
		return msgStorage.getNumNegative(beginning, end);
	}
	
}

class insertPositiveFeedback implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Feedback msg;



	public insertPositiveFeedback(Feedback msg){
		this.msg=msg;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((FeedBackStorage)arg0).insertPositiveFeedback(msg);
	}
	
	
}

class insertNegativeFeedback implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Feedback msg;



	public insertNegativeFeedback(Feedback msg){
		this.msg=msg;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((FeedBackStorage)arg0).insertNegativeFeedback(msg);
	}
	
	
}
