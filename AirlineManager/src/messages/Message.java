package messages;

import java.io.Serializable;
import java.util.GregorianCalendar;

public abstract class Message implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* The contents of the message. */
	protected String messageContents;
	protected GregorianCalendar date;
	
	/* The constructor. */
	public Message(String contents, GregorianCalendar date){
		messageContents = contents;
		this.date = date;
		
	}
	
	public GregorianCalendar getDate(){
		return date;
	}

	public String getMessageContents() {
		return messageContents;
	}

	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}

	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
}
