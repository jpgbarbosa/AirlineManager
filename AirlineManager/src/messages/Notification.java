package messages;

import java.util.*;


@SuppressWarnings("serial")
public class Notification extends Message{
	/* The type of notification. */
	//TODO: Define the types of notifications.
	private String notificationType;
	
	/* The constructor. */
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
