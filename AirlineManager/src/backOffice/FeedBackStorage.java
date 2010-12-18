package backOffice;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.Vector;

import messages.Feedback;

/**
 * Class responsible for storing the Feedbacks received by the Company, either positive or negative.
 * @author Daniela Fontes, Ivo Correia, Jo‹o Penetra, Jo‹o Barbosa, Ricardo Bernardino
 *
 */
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
	
	/**
	 * Inserts a new message in the positive feed back list.
	 * @param feedBack Feedback object that will be inserted.
	 */
	public void insertPositiveFeedback(Feedback feedBack){
		positiveFeedBackList.add(feedBack);
	}
	
	/**
	 * Inserts a new message in the negative feed back list.
	 * @param feedBack Feedback object that will be inserted.
	 */
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
	
	/**
	 * Function responsible for calculating the number of positive feedbacks the company has received.
	 * @return Returns the number of positive feedbacks.
	 */
	public int getNumPositive(){
		return positiveFeedBackList.size();
	}
	
	/**
	 * Function responsible for calculating the number of positive feedbacks the company has received between the given dates.
	 * @param beginning Beginning Date
	 * @param end End Date
	 * @return Returns the number of positive feedbacks.
	 */
	public int getNumPositive(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Feedback f:positiveFeedBackList){
			if(f.getDate().after(beginning) && f.getDate().before(end))
				num++;
		}
		
		return num;
	}
	
	/**
	 * Function responsible for calculating the number of negative feedbacks the company has received.
	 * @return Returns the number of negative feedbacks.
	 */
	public int getNumNegative(){
		return negativeFeedBackList.size();
	}
	
	
	/**
	 * Function responsible for calculating the number of negative feedbacks the company has received between the given dates.
	 * @param beginning Beginning Date
	 * @param end End Date
	 * @return Returns the number of negative feedbacks.
	 */
	public int getNumNegative(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Feedback f:negativeFeedBackList){
			if(f.getDate().after(beginning) && f.getDate().before(end))
				num++;
		}
		
		return num;
	}
	

}
