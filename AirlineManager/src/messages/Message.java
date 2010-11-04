package messages;

import java.util.GregorianCalendar;

public abstract class Message {
	/* The contents of the message. */
	protected String messageContents;
	protected GregorianCalendar date;
	
	/* The constructor. */
	public Message(String contents, GregorianCalendar date){
		messageContents = contents;
		this.date = date;
		
	}
}
