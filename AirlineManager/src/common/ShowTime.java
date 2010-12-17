package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

/**
 * This class is responsible for creating a clock, to be displayed while we navigate in the application.
 * 
 * 
 */
public class ShowTime extends Thread {
	private JLabel time; 
	
	/**
	 * Creates an area to display the clock and start it.
	 * 
	 * @param label
	 */
    public ShowTime(JLabel label) {
    	time = label;
    	time.setBounds(new Rectangle(0,10,300,20));
    	time.setName("time");
    	time.setFont(new Font("sansserif",Font.PLAIN,15));
    	time.setForeground(Color.white);
    	time.validate();
    	
    	this.start();
    } 
 
    public void run() { 
       try {
           while (true) { 
        	   GregorianCalendar calendar = new GregorianCalendar();
        	   time.setText("<HTML><B>" + calendar.getTime().toString() + "</B></HTML>");
               Thread.sleep(1000); // Updates the time everysecond.
           }
       } catch (InterruptedException e) { 
    	   System.exit(-1);
       }
    } 
 }
