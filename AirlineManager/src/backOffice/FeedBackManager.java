package backOffice;

import java.util.LinkedList;

import messages.Feedback;

import common.Client;


public class FeedBackManager {
	/* The list messages (feed back) sent by the clients.
	 * The feed back can be either positive or negative, so we use two lists
	 * to keep them separated.
	 */
	private LinkedList <Feedback> positiveFeedBackList;
	private LinkedList <Feedback> negativeFeedBackList;
	
	/* The constructor. */
	public FeedBackManager(){
		//TODO: Later, we have to read it from a file.
		positiveFeedBackList = new LinkedList <Feedback>();
		negativeFeedBackList = new LinkedList <Feedback>();
	}
	
	/* Sends a notification to a specific client. */
	public void sendNotificationUser (Client client){
		
	}
	
	/* Sends a notification to all the clients. */
	public void sendNotificationAll(){
		
	}
	
	/* Inserts a new message in the positive feed back list. */
	public void insertPositiveFeedback(Feedback feedBack){
		positiveFeedBackList.add(feedBack);
	}
	
	/* Reads the positive feed back provided by the clients. */
	public LinkedList <Feedback> getPositiveFeedBackList(){
		return positiveFeedBackList;
	}
	
	/* Reads the negative feed back provided by the clients. */
	public LinkedList <Feedback> getNegativeFeedBackList(){
		return negativeFeedBackList;
	}
	
	
}
