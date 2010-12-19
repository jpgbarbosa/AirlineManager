package messages;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;


import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;
import org.prevayler.Transaction;

import clients.Operator;

import backOffice.SendEmail;

import frontOffice.FeedBackStorage;

/**
 * Class responsible for managing the Feedbacks received by the Company, either positive or negative.
 * @author Daniela Fontes, Ivo Correia, Jo‹o Penetra, Jo‹o Barbosa, Ricardo Bernardino
 *
 */
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
			System.out.println("Prevayler error, exiting.");
			System.exit(-1);
		} 
		msgStorage=(FeedBackStorage) (prevayler.prevalentSystem());
	}
	
	/**
	 * Sends a notification to a specific client.
	 * @param email Destination's Email
	 * @param type 
	 * @param content Content of the Email
	 * @return Returns True if the email was sent. False otherwise.
	 */
	public boolean sendNotificationUser (String email, String type, String content){
		try {
			SendEmail.send("smtp.sapo.pt", 25, "airlinemanager@fakemail.com", email, "Notification", content);
		} catch (AddressException e) {
			return false;
		} catch (MessagingException e) {
			return false;
		}
		return true;
		
	}
	
	/**
	 * Sends a notification to all the clients.
	 * @param listOps List of Operators
	 * @param type
	 * @param content Content of the Email
	 */
	public void sendNotificationAllOperators(Vector <Operator> listOps, String type, String content){
		for(Operator op : listOps){
			try {
				SendEmail.send("smtp.sapo.pt", 25, "airlinemanager@fakemail.com", op.getEmail(), "Notification", content);
			} catch (AddressException e) {
			} catch (MessagingException e) {}
			
		}
	}
	
	/**
	 * Inserts a new message in the positive feed back list.
	 * @param feedBack Feedback object to be inserted.
	 */
	public void insertPositiveFeedback(Message feedBack){
		prevayler.execute(new insertPositiveFeedback(feedBack));
	}
	
	/**
	 * Inserts a new message in the negative feed back list.
	 * @param feedBack Feedback object to be inserted.
	 */
	public void insertNegativeFeedback(Message feedBack) {
		prevayler.execute(new insertNegativeFeedback(feedBack));
	}
	
	/* Reads the positive feed back provided by the clients. */
	public Vector <Message> getPositiveFeedBackList(){
		return msgStorage.getPositiveFeedBackList();
	}
	
	/* Reads the negative feed back provided by the clients. */
	public Vector <Message> getNegativeFeedBackList(){
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
	private Message msg;



	public insertPositiveFeedback(Message msg){
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
	private Message msg;



	public insertNegativeFeedback(Message msg){
		this.msg=msg;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((FeedBackStorage)arg0).insertNegativeFeedback(msg);
	}
	
	
}

