package frontOffice;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.Feedback;

import common.BackOfficeRemoteInterface;
import common.Constants;
import common.Flight;
import common.Search;
import common.Window;


public class FrontOffice extends UnicastRemoteObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private BackOfficeRemoteInterface backOffice;
	private boolean loggedIn = false;
	
	/* The main constructor. */
	public FrontOffice() throws RemoteException{
		
		try {
			
			backOffice = (BackOfficeRemoteInterface) Naming.lookup("rmi://localhost:2000/AirlineManager");

		} catch (Exception e) {
			//TODO: Quem foi o engraçadinho? :P Foi a daniela lol
			System.out.println("Deu bode!");
			System.exit(0);
		}
		menu = new Menu();
		bookingsMenu = new BookingsMenu();
		sendFeedBackMenu = new SendFeedBackMenu(this);
		searchMenu = new SearchMenu();
	}
	
	public static void main(String[] args) throws RemoteException{
		FrontOffice frontOffice;
		
		frontOffice = new FrontOffice();
		frontOffice.executeGraphics();

	}
	
	/* Sends some feedback to the BackOffice, about a specific flight. */
	public void sendFeedBack(Flight flight, Feedback message){
		
	}
	
	/* Sends positive feedback to the BackOffice.*/
	public String sendPositiveFeedBack(Feedback message){
		try {
			backOffice.sendPositiveFeedback(message);
		} catch (RemoteException e) {
			return "It was not possible to reach the system.";
		}
		return "Message sent.";
	}
	
	/* Sends negative feedback to the BackOffice.*/
	public String sendNegativeFeedBack(Feedback message){
		try {
			backOffice.sendNegativeFeedback(message);
		} catch (RemoteException e) {
			return "It was not possible to reach the system.";
		}
		return "Message sent.";
	}
	
	// / / / / / / / / / / / / / / / / / / / / / GRAPHIC INTERFACE  / / / / / / / / / / / / / / / / / /
	public void executeGraphics(){
		
		JFrame f = new JFrame();
		f.setSize(Constants.DIM_H,Constants.DIM_V);
		f.setTitle("AirlineManager");
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
				/*TODO: Precisamos de definir como é comprado o charter*/
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
				operator();
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
		public void operator(){
			final String [] possibilities = {"Login","Register"};
			int op;
			String pass, conf, answer = "";
		    JPasswordField pwd = new JPasswordField(20);
		    JPasswordField confirmPwd = new JPasswordField(20);
		    JLabel title;
    		final JLabel username = new JLabel("Enter username: ");
    		JTextField user = new JTextField();
    		final JLabel passcode = new JLabel("Enter password: ");
    		final JLabel confirmPassword = new JLabel("Confirm password: ");
    		final JLabel company = new JLabel("Company:");
    		final JLabel address = new JLabel("Address:");
    		final JLabel telephone = new JLabel("Telephone:");
    		final JLabel email = new JLabel("Email:");
    		
    		JTextField companyText = new JTextField();
    		JTextField addressText = new JTextField();
    		JTextField telephoneText = new JTextField();
    		JTextField emailText = new JTextField();
    		
    		int count = 0;   		
    		if(!loggedIn){
	    		op = JOptionPane.showOptionDialog(null,"Options","Operator",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,possibilities,"");
				if(op == 0){
					title = new JLabel("<html><h3> Login </h3></html>");
		    		Object [] jop = {title,username,user,passcode,pwd};
				    	while(count++!=3 && !loggedIn){
				    		JOptionPane.showConfirmDialog(null, jop,"Please enter your information",
				    				JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE);
				    		try {
				    			
				    			String userName= user.getText();
								String passWord = new String(pwd.getPassword());
								
								/* The fields are empty. */
								if (userName.equals("") || passWord.equals("")){
									JOptionPane.showMessageDialog(null,"Empty fields!");
									continue;
								}
								else{
									answer = backOffice.loginOperator(userName, passWord);
								}
				    			
							} catch (RemoteException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(answer.equals("Login successful")){
								loggedIn = true;
							}
							JOptionPane.showMessageDialog(null,answer);
				    		pwd.setText("");
				    	}
				    	count=0;
			    		user.setText("");
				}
				else if(op == 1){
					title = new JLabel("<html><h3> Register </h3></html>");
					Object [] jop = {title,username,user,passcode,pwd,confirmPassword,confirmPwd,
							email,emailText,telephone,telephoneText,company,companyText,
							address,addressText};
					
					while(!loggedIn){
						JOptionPane.showConfirmDialog(null, jop,"Please enter your information",
				    				JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE);
						 
						pass = new String(pwd.getPassword());
						conf = new String(confirmPwd.getPassword());
						if(!user.getText().equals("") && !emailText.getText().equals("") && 
								!telephoneText.getText().equals("") && !companyText.getText().equals("")
								&& !addressText.getText().equals("")){
							if(pass.equals(conf)){
								try {
									/* The fields are empty. */
									if (user.getText().equals("") || pass.equals("")){
										JOptionPane.showMessageDialog(null,"Empty fields!");
										continue;
									}
									else{
										answer = backOffice.registerOperator(companyText.getText(), user.getText(), addressText.getText(), telephoneText.getText(), emailText.getText(), pass);
									}
									
								} catch (RemoteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								if(answer.equals("Registration was successful")){
									loggedIn = true;
									JOptionPane.showMessageDialog(null,answer);
								}
							}
							else{
								JOptionPane.showMessageDialog(null,"The passwords don't match, please try again");
								//break;
							}
						}
						else{
							JOptionPane.showMessageDialog(null,"You have to fill all the information in order to register");
						}
						pwd.setText("");
						confirmPwd.setText("");
					}
					user.setText("");
				}
    		}
    		else{
    			JOptionPane.showMessageDialog(null,"You are already logged in");
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
			if(loggedIn){
				/*TODO: SABER COMO SE RESERVA UM CHARTER */
			}
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
		FrontOffice frontOffice;
		JPanel positivePanel;
		JPanel negativePanel;
		/* Positive Pannel Message Area */
		private	JTextArea posMsgArea;
		/* Negative Pannel Message Area */
		private	JTextArea negMsgArea;
		private JTextArea displayN;
		private JTextArea displayP;
		public SendFeedBackMenu(FrontOffice f){
			frontOffice=f;
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Positive Feedback",Color.white,"Send a positive Sugestion",15,60,200,200,30);
			CreateButton("Negative Feedback",Color.white,"Send a reclamation",15,60,250,200,30);
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			positivePanel = new JPanel();
			negativePanel = new JPanel();
			
			
			
			/* Defines the subpanels. */
			positivePanel.setLayout(null);
			positivePanel.setBounds(new Rectangle(400, 40, 500, 400));
			positivePanel.add(CreateButton("Send",Color.white,"Search for a flight",15,275,20,200,30));
			positivePanel.add(CreateTitle("Message:",Color.black,15,20,20,200,20));
			positivePanel.add(posMsgArea = CreateText(10,50,40,60,350,300));
			displayP=CreateText(10,50,40,375,150,20);
			positivePanel.add(displayP);
			
			negativePanel.setLayout(null);
			negativePanel.setBounds(new Rectangle(400, 40, 500, 400));
			negativePanel.add(CreateButton("Send ",Color.white,"Search for a flight",15,275,20,200,30));
			negativePanel.add(CreateTitle("Message:",Color.black,15,20,20,200,20));
			negativePanel.add(negMsgArea = CreateText(10,50,40,60,350,300));
			displayN=CreateText(10,50,40,375,150,20);
			negativePanel.add(displayN);
			
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
			else if (e.getComponent().getName().equals("Send")){
				System.out.println(posMsgArea.getText());
				displayP.setText(frontOffice.sendPositiveFeedBack(new Feedback("Positive",posMsgArea.getText())));	
			}
			else if (e.getComponent().getName().equals("Send ")){
				displayN.setText(frontOffice.sendNegativeFeedBack(new Feedback("Negative",posMsgArea.getText())));
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
