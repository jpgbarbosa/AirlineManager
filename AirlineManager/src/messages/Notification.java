package messages;

import java.util.*;

/**
 * Class that represents a Notification. 
 */
@SuppressWarnings("serial")
public class Notification extends Message{
	/* The type of notification. */
	//TODO: Define the types of notifications.
	private String notificationType;
	
	/**
	 * The Main Constructor.
	 * @param type
	 * @param contents
	 */
	public Notification(String type, String contents){
		super(contents, new GregorianCalendar());
		notificationType = type;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	
}
