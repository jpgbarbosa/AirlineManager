package messages;

public abstract class Message {
	/* The contents of the message. */
	protected String messageContents;
	
	/* The constructor. */
	public Message(String contents){
		messageContents = contents;
		
	}
}
