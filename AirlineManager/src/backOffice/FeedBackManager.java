package backOffice;

import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

import messages.Feedback;
import messages.Notification;

import common.Client;


public class FeedBackManager {
	/* The list messages (feed back) sent by the clients.
	 * The feed back can be either positive or negative, so we use two lists
	 * to keep them separated.
	 */
	private LinkedList <Feedback> positiveFeedBackList;
	private LinkedList <Feedback> negativeFeedBackList;
	
	/* The constructor. */
	public FeedBackManager(){
		//TODO: Later, we have to read it from a file.
		positiveFeedBackList = new LinkedList <Feedback>();
		negativeFeedBackList = new LinkedList <Feedback>();
	
	}
	
	/* Sends a notification to a specific client. */
	public void sendNotificationUser (Client client, String type, String content){
		//TODO: send e-mail
		String [] to= new String[1];
		Vector<String> v =new Vector<String>();
		StringTokenizer t=new StringTokenizer(client.getEmail());
		t.nextToken("@");
		SendEmail sender= new SendEmail(t.nextToken());
		v.add(content);
		to[0]=client.getEmail();
		//TODO: alterar a classe para aquilo que nos convém
		sender.sendMail(to, "airlinemanager@fakemail.com", null, null, "Notification", v);
		
	}
	
	/* Sends a notification to all the clients. */
	public void sendNotificationAll(LinkedList <Client> listaClientes, String type, String content){
		
		String [] to= new String[1];
		Vector<String> v =new Vector<String>();
		v.add(content);
		for(Client c : listaClientes){
			StringTokenizer t=new StringTokenizer(c.getEmail());
			t.nextToken("@");
			SendEmail sender= new SendEmail(t.nextToken());
			to[0]=c.getEmail();
			sender.sendMail(to, "airlinemanager@fakemail.com", null, null, "Notification", v);
			
		}
		
		
		//TODO: alterar a classe para aquilo que nos convém
		//TODO: send e-mail
	}
	
	/* Inserts a new message in the positive feed back list. */
	public void insertPositiveFeedback(Feedback feedBack){
		positiveFeedBackList.add(feedBack);
	}
	
	/* Inserts a new message in the negative feed back list. */
	public void insertNegativeFeedback(Feedback feedBack) {
		negativeFeedBackList.add(feedBack);
		
	}
	
	/* Reads the positive feed back provided by the clients. */
	public LinkedList <Feedback> getPositiveFeedBackList(){
		return positiveFeedBackList;
	}
	
	/* Reads the negative feed back provided by the clients. */
	public LinkedList <Feedback> getNegativeFeedBackList(){
		return negativeFeedBackList;
	}
	
	
}
