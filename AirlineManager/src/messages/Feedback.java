package messages;

public class Feedback extends Message{
	/* The type of feedback: it can be positive or negative. */
	private String feedBackType;
	
	/* The constructor. */
	public Feedback(String type, String contents){
		super(contents);
		feedBackType = type;
	}
}
