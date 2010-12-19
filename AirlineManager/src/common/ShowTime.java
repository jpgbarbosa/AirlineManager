package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

/**
 * This class is responsible for creating a clock, to be displayed while we navigate in the application.
 * 
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author João Penetra
 * @author João Barbosa
 * @author Ricardo Bernardino
 */
public class ShowTime extends Thread {
	private JLabel time; 
	private JLabel username = null;
	/* This method is used to know whether the client application is working with a operator
	 * or not. Not applicable to the administrator application.
	 */
	private String loggedIn = "";
	
	/**
	 * Creates an area to display the clock and start it.
	 * It will also show the operator's name in the client application
	 * if logged in.
	 * 
	 * @param label
	 */
    public ShowTime(JLabel label, JLabel label2) {
    	time = label;
    	time.setBounds(new Rectangle(0,10,300,20));
    	time.setName("time");
    	time.setFont(new Font("sansserif",Font.PLAIN,15));
    	time.setForeground(Color.white);
    	time.validate();
    	
    	if (label2 != null){
	    	username = label2;
	    	username.setBounds(new Rectangle(0,30,300,20));
	    	username.setName("username");
	    	username.setFont(new Font("sansserif",Font.PLAIN,15));
	    	username.setForeground(Color.white);
	    	username.validate();
    	}
    	
    	this.start();
    } 
 
    public void run() { 
       try {
           while (true) { 
        	   GregorianCalendar calendar = new GregorianCalendar();
        	   time.setText("<HTML><B>" + calendar.getTime().toString() + "</B></HTML>");
        	   if (username != null){
        		   username.setText("<HTML><B>" + loggedIn + "</B></HTML>");
        	   }
               Thread.sleep(1000); // Updates the time everysecond.
           }
       } catch (InterruptedException e) { 
    	   System.exit(-1);
       }
    } 
    
    public void setIsLoggedIn(String logged){
    	loggedIn = "LOGGED IN WITH: " + logged;
    }
 }
