package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.GregorianCalendar;

import javax.swing.JLabel;

public class ShowTime extends Thread {
	private JLabel time; 
	
	/* The main constructor. */
    public ShowTime(JLabel t) {
    	time = t;
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
