package messages;

import java.util.GregorianCalendar;

public class Feedback extends Message{
	/* The type of feedback: it can be positive or negative. */
	private String feedBackType;
	
	/* The constructor. */
	public Feedback(String type, String contents){
		super(contents, new GregorianCalendar());
		feedBackType = type;
	}
}
