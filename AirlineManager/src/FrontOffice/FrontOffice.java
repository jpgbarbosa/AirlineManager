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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import messages.Feedback;

import common.Flight;


public class FrontOffice {
	private BookingManager bookingManager;
	
	/* Window dimensions for the graphical interface. */
	private int dimH = 1000;
	private int dimV = 600;
	/* The windows used on the graphical interface. */
	private Menu menu;
	private BookingsMenu bookingsMenu;
	private SendFeedBackMenu sendFeedBackMenu;
	private SearchMenu searchMenu;
	private PrintTicketsMenu printTicketsMenu;
	private ModifyBookingMenu modifyBookingMenu;
	
	/* The main constructor. */
	public FrontOffice(){
		menu = new Menu();
		bookingsMenu = new BookingsMenu();
		sendFeedBackMenu = new SendFeedBackMenu();
		searchMenu = new SearchMenu();
		printTicketsMenu = new PrintTicketsMenu();
		modifyBookingMenu = new ModifyBookingMenu();
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
		f.setSize(dimH,dimV);
		f.setTitle("Móveis PIB");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);
		panel.setVisible(true);
		
		panel.add(menu);
		panel.add(bookingsMenu);
		panel.add(sendFeedBackMenu);
		panel.add(searchMenu);
		panel.add(printTicketsMenu);
		panel.add(modifyBookingMenu);
		
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
		printTicketsMenu.setVisible(false);
		modifyBookingMenu.setVisible(false);
		
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
		
		protected void CreateButton(String name, Color cor,String toolTip, int size, int x, int y, int x1,int y1){
			JButton botao = new JButton(name);
			botao.setName(name);
			botao.setFont(new Font("sansserif",Font.PLAIN,size));
			botao.setBounds(new Rectangle(x, y, x1, y1));
			botao.setBackground(cor);
			botao.addMouseListener(this);
			botao.setToolTipText(toolTip);
			add(botao);
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
			CreateTitle("Bem-vindo ao Simulador da Empresa Móveis PIB",Color.orange,30,180,50,800,30);
			CreateButton("Iniciar",Color.white, "Iniciar a simulação",15,60,500,100,30);
			CreateButton("Opções",Color.white, "Especificar as condições inicias", 15,180,500,100,30);
			CreateButton("Sair",Color.white,"Sair do simulador" ,15,820,500,100,30);
		}
		
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){
				/*menu.setVisible(false);
				beginSimulation();
				start.resetJanelaReplica();
				start.update();
				start.setVisible(true);*/
			}
			else if(e.getComponent().getName().equals("Opções")){
				/*menu.setVisible(false);
				setup.setVisible(true);*/
			}
			else if (e.getComponent().getName().equals("Sair")){
				/*JOptionPane jp= new JOptionPane("Os Móveis PIB desejam-lhe um bom-dia!",JOptionPane.INFORMATION_MESSAGE);
				JDialog jd = jp.createDialog("Adeus!");
				jd.setBounds(new Rectangle(340,200,320,120));
				jd.setVisible(true);
				System.exit( 0 );*/
			}
		}
	}
	
	@SuppressWarnings("serial")
	private class BookingsMenu extends Window{
		/* The constructor. */
		public BookingsMenu(){
			
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){

			}
			else if(e.getComponent().getName().equals("Opções")){

			}
			else if (e.getComponent().getName().equals("Sair")){

			}
		}
	}
	
	@SuppressWarnings("serial")
	private class SendFeedBackMenu extends Window{
		/* The constructor. */
		public SendFeedBackMenu(){
			
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){

			}
			else if(e.getComponent().getName().equals("Opções")){

			}
			else if (e.getComponent().getName().equals("Sair")){

			}
		}
	}
	
	@SuppressWarnings("serial")
	private class SearchMenu extends Window{
		/* The constructor. */
		public SearchMenu(){
			
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){

			}
			else if(e.getComponent().getName().equals("Opções")){

			}
			else if (e.getComponent().getName().equals("Sair")){

			}
		}
	}
	
	@SuppressWarnings("serial")
	private class PrintTicketsMenu extends Window{
		/* The constructor. */
		public PrintTicketsMenu(){
			
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){

			}
			else if(e.getComponent().getName().equals("Opções")){

			}
			else if (e.getComponent().getName().equals("Sair")){

			}
		}
	}
	
	@SuppressWarnings("serial")
	private class ModifyBookingMenu extends Window{
		/* The constructor. */
		public ModifyBookingMenu(){
			
		}
		
		/* The option buttons that can be selected by the user. */
		public void mouseReleased(MouseEvent e){
			if(e.getComponent().getName().equals("Iniciar")){

			}
			else if(e.getComponent().getName().equals("Opções")){

			}
			else if (e.getComponent().getName().equals("Sair")){

			}
		}
	}

}
