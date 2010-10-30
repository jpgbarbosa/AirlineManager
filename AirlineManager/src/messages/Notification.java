package messages;

public class Notification extends Message{
	/* The type of notification. */
	//TODO: Define the types of notifications.
	private String notificationType;
	
	/* The constructor. */
	public Notification(String type, String contents){
		super(contents);
		notificationType = type;
	}
	
}
