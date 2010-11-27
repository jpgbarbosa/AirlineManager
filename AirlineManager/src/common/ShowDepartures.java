package common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowDepartures extends Thread {
	private JLabel departure; 
	private String text = "Hi";
	private JPanel panel;
	public LockDepartures lock = new LockDepartures();
	
	
	/* The main constructor. */
    public ShowDepartures(JPanel j, JLabel t) {
    	panel = j;
    	
    	departure = t;
    	departure.setBounds(new Rectangle(10,10,500,20));
    	departure.setName("departures");
    	departure.setFont(new Font("sansserif",Font.PLAIN,15));
    	departure.setForeground(Color.white);
    	departure.validate();
    	
    	CreateImage("./src/images/plane15.gif","",10,10,115,86);
    	
    	this.start();
    } 
 
    public void run() { 
       try {
           while (true) { 
        	   synchronized(lock){
        		   lock.wait();
        		   departure.setText(text);
        		   panel.setVisible(true);
        		   Thread.sleep(5000); // Let the flight symbol goes for 5 seconds.
        		   panel.setVisible(false);
        		   lock.wait();
        	   }
               
           }
       } catch (InterruptedException e) { 
    	   System.exit(-1);
       }
    } 
    
    public void setText(String t){
    	text = t;
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

class LockDepartures{
	
}