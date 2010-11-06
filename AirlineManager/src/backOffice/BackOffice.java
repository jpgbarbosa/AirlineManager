package backOffice;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import common.Airplane;
import common.Constants;
import common.Flight;
import common.Search;
import common.Window;

public class BackOffice {
	/* The main panel. */
	private JPanel panel = new JPanel();
	
	/* The list of managers that the BackOffice will deal with. */
	private FeedBackManager feedBackManager;
	private FlightsManager flightsManager;
	private PlanesManager planesManager;
	private StatisticsManager statisticsManager;
	private Search search;
	
	private Menu menu;
	private FeedBackManagerMenu feedBackManagerMenu;
	private FlightsManagerMenu flightsManagerMenu;
	private PlanesManagerMenu planesManagerMenu;
	private StatisticsManagerMenu statisticsManagerMenu;
	private LoginMenu loginMenu;
	
	/* The main constructor. */
	public BackOffice(){
		feedBackManager = new FeedBackManager();
		flightsManager = new FlightsManager();
		planesManager = new PlanesManager();
		statisticsManager = new StatisticsManager(feedBackManager, flightsManager, planesManager);
		search = new Search(flightsManager, planesManager);
		
		menu = new Menu();
		feedBackManagerMenu = new FeedBackManagerMenu();
		flightsManagerMenu = new FlightsManagerMenu();
		planesManagerMenu = new PlanesManagerMenu();
		statisticsManagerMenu = new StatisticsManagerMenu();
		loginMenu = new LoginMenu();
		
	}
	
	public static void main(String[] args) {

		
		BackOffice backOffice = new BackOffice();
		
		backOffice.executeGraphics();

	}

	/* The method to authenticate the administrator. */
	public boolean loginAdmin(String username, String password){
		//TODO: Maybe remove this static definition of the password or not.
		String user = "admin";
		String pass = "fixe";
		if (username.equals(user) && password.equals(pass)){
			return true;
		}
		return false;
	}
	
	//TODO: Make sure this function works correctly.
	public GregorianCalendar checkDate(int year, int month, int day, int hour, int minute){
		
		/* A non-positive year. */
		if (year < 0){
			return null;
		}
		/* An invalid month. */
		else if (month < 1 || month > 12){
			return null;
		}
		/* An invalid day. */
		else if (day < 1 ||
			(day > 31 && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) ||
			(day > 30 && (month == 4 || month == 6 || month == 9 || month == 11))){
			return null;
		}
		/* Invalid hour. */
		else if (hour < 0 || hour > 23){
			return null;
		}
		/* Invalid minute. */
		else if (minute < 0 || minute > 59){
			return null;
		}
		
		/* It's February, so we ought to check if we are in a leap year or not. */
		if (month == 2){
			boolean leapYear;
			
			/* Verifires whether we are in a leap year or not. */
			if (year % 400 == 0){
				leapYear = true;
			}
			else if (year % 100 == 0){
				leapYear = false;
			}
			else if (year % 4 == 0){
				leapYear = true;
			}
			else{
				leapYear = false;
			}
			
			/* Acts accordingly to the result collected above. */
			if (leapYear){
				if (day > 29){
					return null;
				}
			}
			else{
				if (day > 28){
					return null;
				}
			}
		}
		
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar date = new GregorianCalendar(year,month - 1,day,hour,minute);
		
		/* This is an old date. */
		if (now.after(date)){
			//TODO: remove this
			System.out.println("OLD DATA!!!");
			return null;
		}
		
		return date;
	}
	
	
    public void executeGraphics(){
		
		JFrame f = new JFrame();
		f.setSize(Constants.DIM_H,Constants.DIM_V);
		f.setTitle("Airplane Agency");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);
		panel.setVisible(true);
		
		panel.add(loginMenu);
		panel.add(menu);
		panel.add(feedBackManagerMenu);
		panel.add(flightsManagerMenu);
		panel.add(planesManagerMenu);
		panel.add(statisticsManagerMenu);
		
		/*menu.CreateImage("./src/imagens/furniture.jpg","Visite as nossas exposições!",250,100,500,340);
		menu.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		
		start.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		setup.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);
		seeds.CreateImage("./src/imagens/finalBackground.jpg","",0,0,990,570);*/
		
		/* Sets all the windows invisible, except, naturally, the main menu. */
		loginMenu.setVisible(true);
		menu.setVisible(false);
		feedBackManagerMenu.setVisible(false);
		flightsManagerMenu.setVisible(false);
		planesManagerMenu.setVisible(false);
		statisticsManagerMenu.setVisible(false);
		
		f.setContentPane(panel);
		f.setVisible(true);
		
	}
	
	@SuppressWarnings("serial")
	private class Menu extends Window{
		public Menu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Airplanes",Color.white,"Manage Airplanes",15,60,200,100,30);
			CreateButton("Flights",Color.white,"Manage Flights",15,60,250,100,30);
			CreateButton("Feedback",Color.white,"Handle Feedback",15,60,300,100,30);
			CreateButton("Statistics",Color.white,"Check Statistics",15,60,350,100,30);
			CreateButton("Exit",Color.white,"Leave the application",15,60,500,100,30);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Airplanes")){
				menu.setVisible(false);
				planesManagerMenu.entry();
			}
			else if(e.getComponent().getName().equals("Flights")){
				menu.setVisible(false);
				flightsManagerMenu.entry();
			}
			else if(e.getComponent().getName().equals("Feedback")){
				menu.setVisible(false);
				feedBackManagerMenu.entry();
			}
			else if(e.getComponent().getName().equals("Statistics")){
				menu.setVisible(false);
				statisticsManagerMenu.entry();
				
			}
			else if (e.getComponent().getName().equals("Exit")){
				/* The user is leaving the application. */
				JOptionPane jp= new JOptionPane("Have a nice day!",JOptionPane.INFORMATION_MESSAGE);
				JDialog jd = jp.createDialog("Thank you!");
				jd.setBounds(new Rectangle(340,200,320,120));
				jd.setVisible(true);
				System.exit( 0 );
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class FeedBackManagerMenu extends Window{
		JPanel positivePanel;
		JPanel negativePanel;
		JPanel sendPanel;
		
		public FeedBackManagerMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Positive Feedback",Color.white,"Read positive critics sent by clients",15,60,200,200,30);
			CreateButton("Negative Feedback",Color.white,"Read negative messages sent by clients",15,60,250,200,30);
			CreateButton("Send Notifications",Color.white,"Notificate clients about a special event",15,60,300,200,30);
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			positivePanel = new JPanel();
			negativePanel = new JPanel();
			sendPanel = new JPanel();
			
			/* Defines the subpanels. */
			positivePanel.setLayout(null);
			positivePanel.setBounds(new Rectangle(400, 40, 400, 400));
			positivePanel.add(CreateButton("Schedule",Color.white,"Search for a flight",15,60,100,200,30));
			
			negativePanel.setLayout(null);
			negativePanel.setBounds(new Rectangle(400, 40, 400, 400));
			negativePanel.add(CreateButton("re",Color.white,"Search for a flight",15,60,100,200,30));
			
			sendPanel.setLayout(null);
			sendPanel.setBounds(new Rectangle(400, 40, 400, 400));
			sendPanel.add(CreateButton("cancel",Color.white,"Search for a flight",15,60,100,200,30));
			
			/* Adds the subpanels to the main panel. */
			panel.add(positivePanel);
			panel.add(negativePanel);
			panel.add(sendPanel);
			
			negativePanel.setVisible(false);
			sendPanel.setVisible(false);
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
				sendPanel.setVisible(false);
				positivePanel.setVisible(true);
			}
			else if(e.getComponent().getName().equals("Negative Feedback")){
				negativePanel.setVisible(true);
				sendPanel.setVisible(false);
				positivePanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Send Notifications")){
				negativePanel.setVisible(false);
				sendPanel.setVisible(true);
				positivePanel.setVisible(false);
			}
			else if (e.getComponent().getName().equals("Return")){
				negativePanel.setVisible(false);
				sendPanel.setVisible(false);
				positivePanel.setVisible(false);
				
				feedBackManagerMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class FlightsManagerMenu extends Window{
		private JPanel schedulePanel;
		private JPanel reschedulePanel;
		private JPanel cancelPanel;
		private JPanel findPanel;
		
		/* SCHEDULEPANEL */
		private JTextField dayField;
		private JTextField monthField;
		private JTextField yearField;
		private JTextField idPlaneScheduleField;
		private JTextField hourField;
		private JTextField minuteField;
		private JTextField destinyField;
		
		private JTextArea logInfo;
		private String menuIdentifier;
		
		/* RESCHEDULEPANEL */
		
		/* CANCELPANEL */
		private JTextField idFlightCancelPanel;
		/* FINDPANEL */
		
		
		
		public FlightsManagerMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Schedule Flight",Color.white,"Schedule a new flight",15,60,200,200,30);
			CreateButton("Reschedule Flight",Color.white,"Reshedule a given flight",15,60,250,200,30);
			CreateButton("Cancel Flight",Color.white,"Cancels an already scheduled flight",15,60,300,200,30);
			CreateButton("Find Flight",Color.white,"Search for a flight",15,60,350,200,30);
			
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			 
			logInfo = CreateText(10,10,400,500,275,50);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			schedulePanel = new JPanel();
			reschedulePanel = new JPanel();
			cancelPanel = new JPanel();
			findPanel = new JPanel();
			
			/* Defines the subpanels. */
			schedulePanel.setLayout(null);
			schedulePanel.setBounds(new Rectangle(400, 40, 400, 400));
			schedulePanel.add(CreateTitle("DD:",Color.black,15,20,100,70,20));
			schedulePanel.add(dayField = CreateBoxInt(20,50,100,20,20, 0));
			schedulePanel.add(CreateTitle("MM:",Color.black,15,75,100,70,20));
			schedulePanel.add(monthField = CreateBoxInt(20,105,100,20,20, 0));
			schedulePanel.add(CreateTitle("YYYY:",Color.black,15,130,100,70,20));
			schedulePanel.add(yearField = CreateBoxInt(20,175,100,35,20, 0));
			schedulePanel.add(CreateTitle("TIME:",Color.black,15,230,100,70,20));
			schedulePanel.add(hourField = CreateBoxInt(20,270,100,20,20, 0));
			schedulePanel.add(CreateTitle("h",Color.black,15,292,100,70,20));
			schedulePanel.add(minuteField = CreateBoxInt(20,305,100,20,20, 0));
			schedulePanel.add(CreateTitle("ID Plane",Color.black,15,100,130,70,20));
			schedulePanel.add(idPlaneScheduleField = CreateBoxInt(20,175,130,50,20, 0));
			schedulePanel.add(CreateTitle("Destiny:",Color.black,15,100,160,70,20));
			schedulePanel.add(destinyField = CreateBoxText(20,175,160,110,20));
			schedulePanel.add(CreateButton("Submit",Color.white,"Submit the form",15,250,330,100,30));
			
			reschedulePanel.setLayout(null);
			reschedulePanel.setBounds(new Rectangle(400, 40, 400, 400));
			reschedulePanel.add(CreateButton("re",Color.white,"Search for a flight",15,60,100,200,30));
			
			cancelPanel.setLayout(null);
			cancelPanel.setBounds(new Rectangle(400, 40, 400, 400));
			cancelPanel.add(CreateTitle("Flight's ID:",Color.black,15,100,100,70,20));
			cancelPanel.add(idFlightCancelPanel = CreateBoxInt(20,175,100,50,20, 0));
			cancelPanel.add(CreateButton("Submit",Color.white,"Submit the form",15,250,330,100,30));
			
			findPanel.setLayout(null);
			findPanel.setBounds(new Rectangle(400, 40, 400, 400));
			findPanel.add(CreateButton("Go",Color.white,"Search for a flight",15,60,100,200,30));
			
			/* Adds the subpanels to the main panel. */
			panel.add(schedulePanel);
			panel.add(reschedulePanel);
			panel.add(cancelPanel);
			panel.add(findPanel);
			
			reschedulePanel.setVisible(false);
			cancelPanel.setVisible(false);
			findPanel.setVisible(false);
			schedulePanel.setVisible(false);
		}
		
		/* This function is used when the user enters this menu.
		 * We need to set true the right menu and one of its subpanels.
		 */
		public void entry(){
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			schedulePanel.setVisible(true);
			
			menuIdentifier = "schedulePanel";
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Schedule Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(false);
				findPanel.setVisible(false);
				schedulePanel.setVisible(true);
				
				menuIdentifier = "schedulePanel";
			}
			else if(e.getComponent().getName().equals("Reschedule Flight")){
				reschedulePanel.setVisible(true);
				cancelPanel.setVisible(false);
				findPanel.setVisible(false);
				schedulePanel.setVisible(false);
				
				menuIdentifier = "reschedulePanel";
			}
			else if(e.getComponent().getName().equals("Cancel Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(true);
				findPanel.setVisible(false);
				schedulePanel.setVisible(false);
				
				menuIdentifier = "cancelPanel";
			}
			else if(e.getComponent().getName().equals("Find Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(false);
				findPanel.setVisible(true);
				schedulePanel.setVisible(false);
				
				menuIdentifier = "findPanel";
			}
			else if(e.getComponent().getName().equals("Submit")){
				/* We are inside one of the filling forms. */
				if (menuIdentifier.equals("schedulePanel")){
					/*TODO: Naturalmente, temos de fazer isto melhor.
					 * Não podemos deixar introduzir o voo simplesmente, temos de ver
					 * se há concordância no avião, se não há outro voo para o mesmo
					 * avião demasiado ao pé e coisas do género.
					 */
					
					try{
						int day = Integer.parseInt(dayField.getText());
						int month = Integer.parseInt(monthField.getText());
						int year = Integer.parseInt(yearField.getText());
						int hour = Integer.parseInt(hourField.getText());
						int minute = Integer.parseInt(minuteField.getText());
						int idPlane = Integer.parseInt(idPlaneScheduleField.getText());
						String destiny = destinyField.getText();
						
						Airplane airplane = search.searchPlane(idPlane);
						
						if (airplane != null){
							GregorianCalendar date;
							if (!destinyField.equals("") && (date = checkDate(year, month, day, hour, minute)) != null){
								Flight flight = flightsManager.scheduleFlight(airplane, date, destiny);
								
								logInfo.setText("Flight schedule with ID " + flight.getId() + "!");
							}
							else{
								logInfo.setText("Invalid data.");
							}
							
						}
						else{
							logInfo.setText("Plane not found.");
						}
					} catch (Exception e1){
						logInfo.setText("Invalid data.");
					}
					
					
				}
				else if (menuIdentifier.equals("reschedulePanel")){
					
				}
				else if (menuIdentifier.equals("cancelPanel")){
					//TODO: Eventualmente protecções.
					Flight flight = null;
					int id = -1;
					try{
						id = Integer.parseInt(idFlightCancelPanel.getText());
						
						flight = flightsManager.searchFlightById(id);
						
					} catch (Exception e1){
						logInfo.setText("Invalid data.");
					}
					
					if (flight != null && id != -1){
						flightsManager.cancelFlight(flight);
						logInfo.setText("Flight successfully canceled!");
					}
					else if (flight == null){
						logInfo.setText("Flight not found.");
					}
				}
				else if (menuIdentifier.equals("findPanel")){
					
				}
			}
			else if (e.getComponent().getName().equals("Return")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(false);
				findPanel.setVisible(false);
				schedulePanel.setVisible(false);
				
				flightsManagerMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class PlanesManagerMenu extends Window{
		private JPanel buyPanel;
		private JPanel sellPanel;
		private JPanel listPanel;
		private JPanel findPanel;
		
		private JTextArea logInfo;
		
		/* So we can know in which the user is at the moment. */
		private String menuIdentifier;
		
		/* BUYPANEL VARIABLES */
		private JTextField companyField;
		private JTextField modelField;
		private JTextField noSeatsField;
		
		/* SELLPANEL VARIABLES */
		private JTextField idSellField;
		
		/* LISTPANEL VARIABLES */
		private JTextArea listArea;
		
		/* FINDPANEL VARIABLES */
		
		public PlanesManagerMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Buy Plane",Color.white,"Adds a new plane to the fleet",15,60,200,150,30);
			CreateButton("Sell Plane",Color.white,"Removes a plane from the fleet",15,60,250,150,30);
			CreateButton("List Planes",Color.white,"Removes a plane from the fleet",15,60,300,150,30);
			CreateButton("Find Plane",Color.white,"List all the planes from the fleet",15,60,350,150,30);
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			
			logInfo = CreateText(10,10,400,500,275,50);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			buyPanel = new JPanel();
			sellPanel = new JPanel();
			listPanel = new JPanel();
			findPanel = new JPanel();
			
			/* Defines the subpanels. */
			buyPanel.setLayout(null);
			buyPanel.setBounds(new Rectangle(400, 40, 400, 400));
			buyPanel.add(CreateTitle("No. Seats:",Color.black,15,100,100,70,20));
			buyPanel.add(noSeatsField = CreateBoxInt(20,175,100,50,20, 0));
			buyPanel.add(CreateTitle("Company:",Color.black,15,100,130,70,20));
			buyPanel.add(companyField = CreateBoxText(20,175,130,110,20));
			buyPanel.add(CreateTitle("Model:",Color.black,15,100,160,70,20));
			buyPanel.add(modelField = CreateBoxText(20,175,160,110,20));
			buyPanel.add(CreateButton("Submit",Color.white,"Submit the form",15,250,330,100,30));
			
			sellPanel.setLayout(null);
			sellPanel.setBounds(new Rectangle(400, 40, 400, 400));
			sellPanel.add(CreateTitle("Plane's ID:",Color.black,15,100,100,70,20));
			sellPanel.add(idSellField = CreateBoxInt(20,175,100,50,20, 0));
			sellPanel.add(CreateButton("Submit",Color.white,"Submit the form",15,250,330,100,30));
			
			listPanel.setLayout(null);
			listPanel.setBounds(new Rectangle(400, 40, 400, 400));
			listPanel.add(CreateTitle("LIST OF FLIGHTS:",Color.black,15,20,20,150,20));
			listPanel.add(CreateTitle("     ID    SEATS        COMPANY            MODEL",Color.black,15,20,40,400,20));
			listPanel.add(listArea = CreateText(10,50,40,60,350,320));
			
			findPanel.setLayout(null);
			findPanel.setBounds(new Rectangle(400, 40, 400, 400));
			findPanel.add(CreateButton("Go",Color.white,"Search for a flight",15,60,100,200,30));
			
			/* Adds the subpanels to the main panel. */
			panel.add(buyPanel);
			panel.add(sellPanel);
			panel.add(listPanel);
			panel.add(findPanel);
			
			buyPanel.setVisible(false);
			sellPanel.setVisible(false);
			listPanel.setVisible(false);
			findPanel.setVisible(false);
			
		}
		
		/* This function is used when the user enters this menu.
		 * We need to set true the right menu and one of its subpanels.
		 */
		public void entry(){
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			buyPanel.setVisible(true);
			menuIdentifier = "buyPanel";
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Buy Plane")){
				buyPanel.setVisible(true);
				sellPanel.setVisible(false);
				listPanel.setVisible(false);
				findPanel.setVisible(false);
				
				logInfo.setText("");
				menuIdentifier = "buyPanel";
			}
			else if(e.getComponent().getName().equals("Sell Plane")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(true);
				listPanel.setVisible(false);
				findPanel.setVisible(false);
				
				logInfo.setText("");
				menuIdentifier = "sellPanel";
			}
			else if(e.getComponent().getName().equals("List Planes")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				listPanel.setVisible(true);
				findPanel.setVisible(false);
				
				logInfo.setText("");
				listArea.setText("");
				Vector <Airplane> list = planesManager.getPlanesList();
				
				for (int i = 0; i < list.size(); i++){
					Airplane airplane = list.get(i);
					listArea.append(airplane.getId() + "              " + airplane.getNoSeats() + "\t         " + airplane.getCompany() + "\t                   " + airplane.getModel() + "\n");
				}
				
				menuIdentifier = "listPanel";
			}
			else if(e.getComponent().getName().equals("Find Plane")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				listPanel.setVisible(false);
				findPanel.setVisible(true);
				
				logInfo.setText("");
				menuIdentifier = "findPanel";
			}
			else if ((e.getComponent().getName().equals("Submit"))){
				/* We are inside one of the filling forms. */
				if (menuIdentifier.equals("buyPanel")){
					//TODO: Eventualmente protecções.
					Airplane airplane = null;
					try{
						int noSeats = Integer.parseInt(noSeatsField.getText());
						String company = companyField.getText();
						String model = modelField.getText();
						
						if (!company.equals("") && !model.equals("") && noSeats > 0){
							airplane = new Airplane(noSeats , company, model);
						}
						else{
							logInfo.setText("Invalid data.");
						}
					} catch (Exception e1){
						logInfo.setText("Invalid data.");
					}
					
					if (airplane != null){
						planesManager.addPlane(airplane);
						logInfo.setText("Plane successfully added to the fleet, with ID " + airplane.getId()+ "!");
					}
				}
				else if (menuIdentifier.equals("sellPanel")){
					//TODO: Eventualmente protecções.
					Airplane airplane = null;
					int id = -1;
					try{
						id = Integer.parseInt(idSellField.getText());
						
						airplane = search.searchPlane(id);
						
					} catch (Exception e1){
						logInfo.setText("Invalid data.");
					}
					
					if (airplane != null && id != -1){
						planesManager.removePlane(airplane);
						logInfo.setText("Plane successfully remove from the fleet!");
					}
					else if (airplane == null){
						logInfo.setText("Plane not found.");
					}
				}
				else if (menuIdentifier.equals("findPanel")){
					
				}
			}
			else if (e.getComponent().getName().equals("Return")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				listPanel.setVisible(false);
				findPanel.setVisible(false);
				
				planesManagerMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class StatisticsManagerMenu extends Window{
		public StatisticsManagerMenu(){
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
				statisticsManagerMenu.setVisible(false);
				menu.setVisible(true);
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class LoginMenu extends Window{
		private JTextField usernameField, passwordField;
		private JTextArea logInfo;
		/* The constructor. */
		public LoginMenu(){
			
			/* The fields to insert the username and password. */
			CreateTitle("Username: ",Color.black,15,90,200,90,20);
			usernameField = CreateBoxText(20,175,200,90,20);
			CreateTitle("Password: ",Color.black,15,90,230,90,20);
			passwordField = CreateBoxPassword(20,175,230,90,20);
			
			logInfo = CreateText(10,10,500,110,275,150);
			
			/* The buttons. */
			CreateButton("Login",Color.white,"Login in the system",15,60,400,100,30);
			CreateButton("Exit",Color.white,"Leave the application",15,60,450,100,30);
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Login")){
				/* The login has been completed successfully. */
				if (loginAdmin(usernameField.getText(), passwordField.getText())){
					loginMenu.setVisible(false);
					menu.setVisible(true);
				}
				/* The user failed the authentication. */
				else{
					logInfo.setText("Sorry, but the login is incorrect.\n");
				}
			}
			else if(e.getComponent().getName().equals("Exit")){
				/* The user is leaving the application. */
				JOptionPane jp= new JOptionPane("Have a nice day!",JOptionPane.INFORMATION_MESSAGE);
				JDialog jd = jp.createDialog("Thank you!");
				jd.setBounds(new Rectangle(340,200,320,120));
				jd.setVisible(true);
				System.exit( 0 );
			}
		}
	}
	
}
