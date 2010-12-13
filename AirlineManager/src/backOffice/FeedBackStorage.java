package backOffice;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import messages.Feedback;

public class FeedBackStorage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The list messages (feed back) sent by the clients.
	 * The feed back can be either positive or negative, so we use two lists
	 * to keep them separated.
	 */
	private Vector <Feedback> positiveFeedBackList=new Vector <Feedback>();
	private Vector <Feedback> negativeFeedBackList=new Vector <Feedback>();
	
	
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
