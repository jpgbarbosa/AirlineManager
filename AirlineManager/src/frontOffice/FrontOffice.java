package frontOffice;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.Feedback;

import common.Constants;
import common.Flight;
import common.Search;
import common.Window;


public class FrontOffice {
	/* The main panel. */
	private JPanel panel = new JPanel();
	
	private BookingManager bookingManager;
	
	/* The windows used on the graphical interface. */
	private Menu menu;
	private BookingsMenu bookingsMenu;
	private SendFeedBackMenu sendFeedBackMenu;
	private SearchMenu searchMenu;
	//TODO: Think about this! The constructor accepts a flight and a plane manager, so
	// 		we will need to eventually invoke an RMI command when we want to access this class.
	private Search search;
	
	/* The main constructor. */
	public FrontOffice(){
		menu = new Menu();
		bookingsMenu = new BookingsMenu();
		sendFeedBackMenu = new SendFeedBackMenu();
		searchMenu = new SearchMenu();
	}
	
	public static void main(String[] args) {
		FrontOffice frontOffice;
		
		frontOffice = new FrontOffice();
		frontOffice.executeGraphics();

	}
	
	/* Sends some feedback to the BackOffice, about a specific flight. */
	public void sendFeedBack(Flight flight, Feedback message){
		
	}
	
	// / / / / / / / / / / / / / / / / / / / / / GRAPHIC INTERFACE  / / / / / / / / / / / / / / / / / /
	public void executeGraphics(){
		
		JFrame f = new JFrame();
		f.setSize(Constants.DIM_H,Constants.DIM_V);
		f.setTitle("Móveis PIB");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);
		panel.setVisible(true);
		
		panel.add(menu);
		panel.add(bookingsMenu);
		panel.add(sendFeedBackMenu);
		panel.add(searchMenu);
		
		/*menu.CreateImage("./src/imagens/furniture.jpg","Visite as nossas exposições!",250,100,500,340);
		menu.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		
		start.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		setup.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		seeds.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);*/
		
		/* Sets all the windows invisible, except, naturally, the main menu. */
		menu.setVisible(true);
		bookingsMenu.setVisible(false);
		sendFeedBackMenu.setVisible(false);
		searchMenu.setVisible(false);
		
		f.setContentPane(panel);
		f.setVisible(true);
		
	}
	
	@SuppressWarnings("serial")
	private class Menu extends Window{
		public Menu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Bookings",Color.white,"Check Your Bookings",15,60,200,150,30);
			CreateButton("Find Flights",Color.white,"Find all the Flights Available",15,60,250,150,30);
			CreateButton("Feedback",Color.white,"Send Feedback",15,60,300,150,30);
			CreateButton("Operator",Color.white,"Login and Register",15,60,350,150,30);
			CreateButton("Exit",Color.white,"Leave the application",15,60,500,150,30);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Bookings")){
				menu.setVisible(false);
				bookingsMenu.entry();
			}
			else if(e.getComponent().getName().equals("Find Flights")){
				menu.setVisible(false);
				searchMenu.entry();
			}
			else if(e.getComponent().getName().equals("Feedback")){
				menu.setVisible(false);
				sendFeedBackMenu.entry();
			}
			else if(e.getComponent().getName().equals("Operator")){
				String [] possibilities = {"Login","Register"};
				String user,pass,confPass;
				int count=0;
				int op =JOptionPane.showOptionDialog(null,"Options","Operator",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,possibilities,"");
				System.out.println(op);
			    JPasswordField pwd = new JPasswordField(20);
				if(op==0){
				    	while(count++!=3){
				    		user = (String)JOptionPane.showInputDialog(null,"Username:");
				    		JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.PLAIN_MESSAGE);
				    		pass = new String(pwd.getPassword());
				    		pwd.setText("");
				    		/*TODO: Create Operator list to login and make charter flights available*/
				    		break;
				    	}
				    	count=0;
				}
				else if(op==1){
					while(true){
						user = (String)JOptionPane.showInputDialog(null,"Username:");
						JOptionPane.showConfirmDialog(null, pwd,"Enter Password",JOptionPane.PLAIN_MESSAGE);
						pass = new String(pwd.getPassword());
						pwd.setText("");
			    		JOptionPane.showConfirmDialog(null, pwd,"Confirm Password",JOptionPane.PLAIN_MESSAGE);
			    		confPass = new String(pwd.getPassword());
			    		/*TODO: Check if Operator already exists and make charter flights available*/
			    		break;
			    		
					}
				}
			}
			else if (e.getComponent().getName().equals("Exit")){
				/* The user is leaving the application. */
				JOptionPane jp= new JOptionPane("Have a nice day!", JOptionPane.INFORMATION_MESSAGE);
				JDialog jd = jp.createDialog("Thank you!");
				jd.setBounds(new Rectangle(340,200,320,120));
				jd.setVisible(true);
				System.exit( 0 );
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class BookingsMenu extends Window{
		JPanel newPanel;
		JPanel cancelPanel;
		JPanel modifyPanel;
		
		public BookingsMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("New Booking",Color.white,"Books a given flight",15,60,200,200,30);
			CreateButton("Cancel Booking",Color.white,"Cancels a booking",15,60,250,200,30);
			CreateButton("Modify Booking",Color.white,"Changes a booking to another flight",15,60,300,200,30);
			
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			 
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			newPanel = new JPanel();
			cancelPanel = new JPanel();
			modifyPanel = new JPanel();
			
			/* Defines the subpanels. */
			newPanel.setLayout(null);
			newPanel.setBounds(new Rectangle(400, 40, 400, 400));
			newPanel.add(CreateButton("Schedule",Color.white,"Search for a flight",15,60,100,200,30));
			
			cancelPanel.setLayout(null);
			cancelPanel.setBounds(new Rectangle(400, 40, 400, 400));
			cancelPanel.add(CreateButton("cancel",Color.white,"Search for a flight",15,60,100,200,30));
			
			modifyPanel.setLayout(null);
			modifyPanel.setBounds(new Rectangle(400, 40, 400, 400));
			modifyPanel.add(CreateButton("Go",Color.white,"Search for a flight",15,60,100,200,30));
			
			/* Adds the subpanels to the main panel. */
			panel.add(newPanel);
			panel.add(cancelPanel);
			panel.add(modifyPanel);
			
			newPanel.setVisible(false);
			cancelPanel.setVisible(false);
			modifyPanel.setVisible(false);
		}
		
		/* This function is used when the user enters this menu.
		 * We need to set true the right menu and one of its subpanels.
		 */
		public void entry(){
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			newPanel.setVisible(true);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("New Booking")){
				newPanel.setVisible(true);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Cancel Booking")){
				newPanel.setVisible(false);
				cancelPanel.setVisible(true);
				modifyPanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Modify Booking")){
				newPanel.setVisible(false);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(true);
			}
			else if (e.getComponent().getName().equals("Return")){
				newPanel.setVisible(false);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
				
				bookingsMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class SendFeedBackMenu extends Window{
		JPanel positivePanel;
		JPanel negativePanel;
		
		public SendFeedBackMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Positive Feedback",Color.white,"Read positive critics sent by clients",15,60,200,200,30);
			CreateButton("Negative Feedback",Color.white,"Read negative messages sent by clients",15,60,250,200,30);
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			positivePanel = new JPanel();
			negativePanel = new JPanel();
			
			/* Defines the subpanels. */
			positivePanel.setLayout(null);
			positivePanel.setBounds(new Rectangle(400, 40, 400, 400));
			positivePanel.add(CreateButton("Schedule",Color.white,"Search for a flight",15,60,100,200,30));
			
			negativePanel.setLayout(null);
			negativePanel.setBounds(new Rectangle(400, 40, 400, 400));
			negativePanel.add(CreateButton("re",Color.white,"Search for a flight",15,60,100,200,30));
			
			/* Adds the subpanels to the main panel. */
			panel.add(positivePanel);
			panel.add(negativePanel);
			
			negativePanel.setVisible(false);
			positivePanel.setVisible(false);
			
		}
		
		public void entry(){
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			positivePanel.setVisible(true);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Positive Feedback")){
				negativePanel.setVisible(false);
				positivePanel.setVisible(true);
			}
			else if(e.getComponent().getName().equals("Negative Feedback")){
				negativePanel.setVisible(true);
				positivePanel.setVisible(false);
			}
			else if (e.getComponent().getName().equals("Return")){
				negativePanel.setVisible(false);
				positivePanel.setVisible(false);
				
				sendFeedBackMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class SearchMenu extends Window{
		public SearchMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Statistics 1",Color.white,"Manage Airplanes",15,60,200,150,30);
			CreateButton("Statistics 2",Color.white,"Manage Flights",15,60,250,150,30);

			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
		}
		
		/* This function is used when the user enters this menu.
		 * We need to set true the right menu and one of its subpanels.
		 */
		public void entry(){
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			//buyPanel.setVisible(true);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Statistics 1")){
				
			}
			else if(e.getComponent().getName().equals("Statistics 2")){
				
			}
			else if (e.getComponent().getName().equals("Return")){
				searchMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
}
