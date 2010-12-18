package backOffice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Flight;

/**
 * Thread responsible for removing the flights that have already finished from the Flights List. Also, it warns the Back Office when a Flight is departing.
 * @author Daniela Fontes, Ivo Correia, Jo‹o Penetra, Jo‹o Barbosa, Ricardo Bernardino
 *
 */
public class FlightsCleaner extends Thread{
	
	private FlightsManager flightsManager;
	private JLabel departure; 
	private String text = "Hi";
	private JPanel panel;
	
	public FlightsCleaner(FlightsManager manager){
		flightsManager = manager;
		
		this.start();
		
	}
	
	public void run(){
		while(true){
			Flight flight;
			GregorianCalendar date = new GregorianCalendar();
			
			for (int i = 0; i < flightsManager.getFlightsList().size() && flightsManager.getFlightsList().get(i).getDate().before(date); i++){
				flight = flightsManager.removeFlight(i);
				//TODO: Change this line to prevayler.
				flightsManager.addFinished(flight);
				
				if (departure != null && panel != null){
					/* Shows the warning in the main menu for 5 seconds. */
					setText(flight.getAirplane().getId(), flight.getId(), flight.getOrigin(), flight.getDestination());
					departure.setText(text);
	     		   	panel.setVisible(true);
	     		   	try {
						sleep(10000);
					} catch (InterruptedException e) {
						System.exit(-1);
					}
	     		   	panel.setVisible(false);
				}
				
				i--;
			}

			/* Checks the list every 10 seconds. */
			try {
				sleep(10000);
			} catch (InterruptedException e) {
				System.exit(-1);
			}
			
		}
	}
	
	public void setComponents(JPanel j, JLabel t){
    	panel = j;
    	
    	departure = t;
    	departure.setBounds(new Rectangle(10,5,360,40));
    	departure.setName("departures");
    	departure.setFont(new Font("sansserif",Font.PLAIN,15));
    	departure.setBackground(Color.black);
    	departure.setForeground(Color.white);
    	departure.setOpaque(true);
    	departure.validate();

		CreateImage("./src/images/departure2.jpg","",240,-140,350,320);
	}
	
    public void setText(int idPlane, int idFlight, String origin, String destination){
    	text = "<HTML><B><font color='#FF0000'> LAST CALL! </font color></B> Airplane " + idPlane + 
    			" is ready to take off! <BR> Flight " + idFlight + " from <B>" + origin +"</B> to <B>" +
    			destination +"</B></HTML>";
		
    }
    
    public JLabel CreateImage(String path, String toolTip,int x,int y,int x1,int y1){
    	
    	BufferedImage img = null;
		Icon icon;
		JLabel label;
		
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e){
			System.out.println("Could not open the image located in " + path + "!");
		}
		icon = new ImageIcon(img);
        label = new JLabel(icon);
        label.setBounds(new Rectangle(x,y,x1,y1));
        if (!toolTip.equals(""))
        	label.setToolTipText(toolTip);
        panel.add(label);
        
        return label;
	}
}
