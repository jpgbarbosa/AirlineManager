package messages;

import java.util.GregorianCalendar;

/**
 * This class extends Message. Clients can give feedback sending this kind of messages.
 * Feedback can be positive or negative.
 * 
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author João Penetra
 * @author João Barbosa
 * @author Ricardo Bernardino
 * @author Barbosa
 *
 */
@SuppressWarnings("serial")
public class Feedback extends Message{
	/* The type of feedback: it can be positive or negative. */
	private String feedBackType;
	
	/**
	 * Creates a new Feedback according to its type, content and system date
	 * 
	 * @param type
	 * @param contents
	 */
	public Feedback(String type, String contents){
		super(contents, new GregorianCalendar());
		feedBackType = type;
	}

	public String getFeedBackType() {
		return feedBackType;
	}

	public void setFeedBackType(String feedBackType) {
		this.feedBackType = feedBackType;
	}
}
