package backOffice;


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

import common.Search;

public class BackOffice {
	/* The main panel. */
	JPanel panel = new JPanel();
	
	/* The list of managers that the BackOffice will deal with. */
	private FeedBackManager feedBackManager;
	private FlightsManager flightsManager;
	private PlanesManager planesManager;
	private StatisticsManager statisticsManager;
	private Search search;
	
	/* Window dimensions for the graphical interface. */
	private int dimH = 1000;
	private int dimV = 600;
	/* The windows used on the graphical interface. */
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
		statisticsManager = new StatisticsManager();
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
	
	
    public void executeGraphics(){
		
		JFrame f = new JFrame();
		f.setSize(dimH,dimV);
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
	private abstract class Window extends JPanel implements MouseListener{
		Window(){
			setLayout(null);
		    setBounds(new Rectangle(0, -1, 1000, 600));
		    setBackground(Color.lightGray);
		}
		
		//Métodos impostos por MouseListener;
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		
		protected void DrawLine(int pos,Color cor, int x,int y,int x1,int y1){
			JSeparator line = new JSeparator(pos);
			line.setBackground(cor);
			line.setBounds(new Rectangle(x,y,x1,y1));
			
			add(line);
		}
		
		protected JButton CreateButton(String name, Color cor,String toolTip, int size, int x, int y, int x1,int y1){
			JButton botao = new JButton(name);
			botao.setName(name);
			botao.setFont(new Font("sansserif",Font.PLAIN,size));
			botao.setBounds(new Rectangle(x, y, x1, y1));
			botao.setBackground(cor);
			botao.addMouseListener(this);
			botao.setToolTipText(toolTip);
			add(botao);
			
			return botao;
		}
		
		protected JRadioButton CreateRadioButton(String name, Color cor, boolean selected, int size, int x, int y, int x1,int y1){
			JRadioButton botao = new JRadioButton(name);
			botao.setName(name);
			botao.setFont(new Font("sansserif",Font.PLAIN,size));
			botao.setBounds(new Rectangle(x, y, x1, y1));
			botao.setBackground(cor);
			botao.setSelected(selected);
			botao.addMouseListener(this);
			add(botao);
			
			return botao;
		}

		protected void CreateTitle(String name,Color cor, int size, int x, int y, int x1,int y1){
			JLabel title = new JLabel();
			title.setBounds(new Rectangle(x,y,x1,y1));
			title.setText(name);
			title.setName(name);
			title.setFont(new Font("sansserif",Font.PLAIN,size));
			title.setForeground(cor);
			title.validate();
			add(title);
		}
		
		public JTextField CreateBoxDouble(int size,int x,int y,int x1,int y1,double def){
			JTextField box = new JTextField(size);
			box.setBounds(new Rectangle(x,y,x1,y1));
			box.setText(Double.toString(def));
			add(box);
			
			return box;
		}
		
		public JTextField CreateBoxInt(int size,int x,int y,int x1,int y1,int def){
			JTextField box = new JTextField(size);
			box.setBounds(new Rectangle(x,y,x1,y1));
			box.setText(Integer.toString(def));
			add(box);
			
			return box;
		}
		
		public JTextField CreateBoxText(int size,int x,int y,int x1,int y1){
			JTextField box = new JTextField(size);
			box.setBounds(new Rectangle(x,y,x1,y1));
			box.setText("");
			add(box);
			
			return box;
		}
		
		public JPasswordField CreateBoxPassword(int size,int x,int y,int x1,int y1){
			JPasswordField box = new JPasswordField(size);
			box.setBounds(new Rectangle(x,y,x1,y1));
			box.setText("");
			add(box);
			
			return box;
		}
		
		public JTextArea CreateText(int size,int size1,int x,int y, int x1,int y1){
			JTextArea text = new JTextArea(size,size1);
			text.setBounds(new Rectangle(x,y,x1,y1));
			add(text);
			
			return text;
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
	        add(label);
	        
	        return label;
		}
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
		JPanel schedulePanel;
		JPanel reschedulePanel;
		JPanel cancelPanel;
		JPanel findPanel;
		
		public FlightsManagerMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Schedule Flight",Color.white,"Schedule a new flight",15,60,200,200,30);
			CreateButton("Reschedule Flight",Color.white,"Reshedule a given flight",15,60,250,200,30);
			CreateButton("Cancel Flight",Color.white,"Cancels an already scheduled flight",15,60,300,200,30);
			CreateButton("Find Flight",Color.white,"Search for a flight",15,60,350,200,30);
			
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			 
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			schedulePanel = new JPanel();
			reschedulePanel = new JPanel();
			cancelPanel = new JPanel();
			findPanel = new JPanel();
			
			/* Defines the subpanels. */
			schedulePanel.setLayout(null);
			schedulePanel.setBounds(new Rectangle(400, 40, 400, 400));
			schedulePanel.add(CreateButton("Schedule",Color.white,"Search for a flight",15,60,100,200,30));
			
			reschedulePanel.setLayout(null);
			reschedulePanel.setBounds(new Rectangle(400, 40, 400, 400));
			reschedulePanel.add(CreateButton("re",Color.white,"Search for a flight",15,60,100,200,30));
			
			cancelPanel.setLayout(null);
			cancelPanel.setBounds(new Rectangle(400, 40, 400, 400));
			cancelPanel.add(CreateButton("cancel",Color.white,"Search for a flight",15,60,100,200,30));
			
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
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Schedule Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(false);
				findPanel.setVisible(false);
				schedulePanel.setVisible(true);
			}
			else if(e.getComponent().getName().equals("Reschedule Flight")){
				reschedulePanel.setVisible(true);
				cancelPanel.setVisible(false);
				findPanel.setVisible(false);
				schedulePanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Cancel Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(true);
				findPanel.setVisible(false);
				schedulePanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Find Flight")){
				reschedulePanel.setVisible(false);
				cancelPanel.setVisible(false);
				findPanel.setVisible(true);
				schedulePanel.setVisible(false);
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
		JPanel buyPanel;
		JPanel sellPanel;
		JPanel listPanel;
		JPanel findPanel;
		
		public PlanesManagerMenu(){
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Buy Plane",Color.white,"Adds a new plane to the fleet",15,60,200,150,30);
			CreateButton("Sell Plane",Color.white,"Removes a plane from the fleet",15,60,250,150,30);
			CreateButton("List Planes",Color.white,"Removes a plane from the fleet",15,60,300,150,30);
			CreateButton("Find Plane",Color.white,"List all the planes from the fleet",15,60,350,150,30);
			CreateButton("Return",Color.white,"Go back to the main menu",15,60,500,100,30);
			
			/* Creates the subpanels that are displayed accordingly to the user's choice. */
			buyPanel = new JPanel();
			sellPanel = new JPanel();
			listPanel = new JPanel();
			findPanel = new JPanel();
			
			/* Defines the subpanels. */
			buyPanel.setLayout(null);
			buyPanel.setBounds(new Rectangle(400, 40, 400, 400));
			buyPanel.add(CreateButton("Schedule",Color.white,"Search for a flight",15,60,100,200,30));
			
			sellPanel.setLayout(null);
			sellPanel.setBounds(new Rectangle(400, 40, 400, 400));
			sellPanel.add(CreateButton("re",Color.white,"Search for a flight",15,60,100,200,30));
			
			listPanel.setLayout(null);
			listPanel.setBounds(new Rectangle(400, 40, 400, 400));
			listPanel.add(CreateButton("Cenas",Color.white,"Search for a flight",15,60,100,200,30));
			
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
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Buy Plane")){
				buyPanel.setVisible(true);
				sellPanel.setVisible(false);
				listPanel.setVisible(false);
				findPanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Sell Plane")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(true);
				listPanel.setVisible(false);
				findPanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("List Planes")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				listPanel.setVisible(true);
				findPanel.setVisible(false);
			}
			else if(e.getComponent().getName().equals("Find Plane")){
				buyPanel.setVisible(false);
				sellPanel.setVisible(false);
				listPanel.setVisible(false);
				findPanel.setVisible(true);
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
