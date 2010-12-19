package frontOffice;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import messages.Message;
import backOffice.BackOfficeRemoteInterface;
import common.*;

import com.toedter.calendar.JCalendar;

;

/**
 * 
 * FrontOffice is responsible for interaction with the users that use our
 * service( clients or operators). It communicates with the BackOffice using
 * JAVA RMI and is used to manage bookings, search flights, and gives the
 * operators opportunity to create charter flights.
 * 
 * @author Daniela Fontes
 * @author Ivo Correia
 * @author Miguel Penetra
 * @author Pedro Barbosa
 * @author Ricardo Bernardino
 * 
 * 
 */
public class FrontOffice extends UnicastRemoteObject {

	private static final long serialVersionUID = 1L;
	/* The main panel. */
	private JPanel panel = new JPanel();

	/* The windows used on the graphical interface. */
	private Menu menu;
	private BookingsMenu bookingsMenu;
	private SendFeedBackMenu sendFeedBackMenu;
	private SearchMenu searchMenu;
	private BackOfficeRemoteInterface backOffice;
	private boolean loggedIn = false;
	private JFrame f;

	private Vector<String> destinations;

	private static int bookingNumber;

	/**
	 * The main constructor.
	 */
	public FrontOffice() throws RemoteException {

		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.exit(-1);
		}

		try {

			backOffice = (BackOfficeRemoteInterface) Naming
					.lookup("rmi://localhost:2000/AirlineManager");

		} catch (Exception e) {
			System.exit(-1);
		}
		destinations = backOffice.getDestinations();

		menu = new Menu();
		bookingsMenu = new BookingsMenu();
		sendFeedBackMenu = new SendFeedBackMenu(this);
		searchMenu = new SearchMenu();

	}

	public static void main(String[] args) throws RemoteException {
		FrontOffice frontOffice;

		frontOffice = new FrontOffice();
		frontOffice.executeGraphics();

	}

	/**
	 * Sends positive feedback to the BackOffice.
	 */
	public String sendPositiveFeedBack(Message message) {
		try {
			backOffice.sendPositiveFeedback(message);
		} catch (RemoteException e) {
			return "It was not possible to reach the system.";
		}
		return "Message sent.";
	}

	/**
	 * Sends negative feedback to the BackOffice.
	 */
	public String sendNegativeFeedBack(Message message) {
		try {
			backOffice.sendNegativeFeedback(message);
		} catch (RemoteException e) {
			return "It was not possible to reach the system.";
		}
		return "Message sent.";
	}

	// / / / / / / / / / / / / / / / / / / / / / GRAPHIC INTERFACE / / / / / / /
	// / / / / / / / / / / /
	public void executeGraphics() {

		f = new JFrame();
		f.setSize(Constants.DIM_H, Constants.DIM_V);
		f.setTitle("AirlineManager");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);
		panel.setBackground(Color.lightGray);
		panel.setVisible(true);

		/* Sets the panel that will hold the time display. */
		JPanel timeDisplay = new JPanel();
		timeDisplay.setLayout(null);
		timeDisplay.setBounds(new Rectangle(765, 11, 250, 50));
		timeDisplay.setOpaque(false);
		JLabel time = new JLabel();
		timeDisplay.add(time);
		timeDisplay.setVisible(true);
		panel.add(timeDisplay);
		new ShowTime(time);

		panel.add(menu);
		panel.add(bookingsMenu);
		panel.add(sendFeedBackMenu);
		panel.add(searchMenu);

		System.out.println("LOADING THE IMAGES...");
		menu.CreateImage("./src/images/takeoff.jpg", "", 0, 0, 990, 570);
		bookingsMenu
				.CreateImage("./src/images/takeoff.jpg", "", 0, 0, 990, 570);
		sendFeedBackMenu.CreateImage("./src/images/takeoff.jpg", "", 0, 0, 990,
				570);
		searchMenu.CreateImage("./src/images/takeoff.jpg", "", 0, 0, 990, 570);

		/* Sets all the windows invisible, except, naturally, the main menu. */
		menu.setVisible(true);
		bookingsMenu.setVisible(false);
		sendFeedBackMenu.setVisible(false);
		searchMenu.setVisible(false);

		f.setContentPane(panel);
		f.setVisible(true);

	}

	@SuppressWarnings("serial")
	private class Menu extends Window {
		public Menu() {
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Bookings", Color.white, "Check Your Bookings", 15,
					60, 200, 150, 30);
			CreateButton("Find Flights", Color.white,
					"Find all the Flights Available", 15, 60, 250, 150, 30);
			CreateButton("Feedback", Color.white, "Send Feedback", 15, 60, 300,
					150, 30);
			CreateButton("Operator", Color.white, "Login and Register", 15, 60,
					350, 150, 30);
			CreateButton("Exit", Color.white, "Leave the application", 15, 60,
					500, 150, 30);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getComponent().getName().equals("Bookings")) {
				menu.setVisible(false);
				bookingsMenu.entry();
			} else if (e.getComponent().getName().equals("Find Flights")) {
				menu.setVisible(false);
				searchMenu.entry();
			} else if (e.getComponent().getName().equals("Feedback")) {
				menu.setVisible(false);
				sendFeedBackMenu.entry();
			} else if (e.getComponent().getName().equals("Operator")) {
				operator();
			} else if (e.getComponent().getName().equals("Exit")) {
				/* The user is leaving the application. */
				JOptionPane jp = new JOptionPane("Have a nice day!",
						JOptionPane.INFORMATION_MESSAGE);
				JDialog jd = jp.createDialog("Thank you!");
				jd.setBounds(new Rectangle(340, 200, 320, 120));
				jd.setVisible(true);
				System.exit(0);
			}
		}

		public void operator() {
			/* Initialize variables */
			final String[] possibilities = { "Login", "Register" };
			int op, status;
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
			if (!loggedIn) {
				op = JOptionPane.showOptionDialog(null, "Options", "Operator",
						JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, possibilities, "");
				if (op == 0) {
					title = new JLabel("<html><h3> Login </h3></html>");
					Object[] jop = { title, username, user, passcode, pwd };

					/* User has 3 opportunities to finish login process */
					while (!loggedIn && count++ != 3) {

						/* Pop-up to ask for the login information */
						status = JOptionPane.showConfirmDialog(null, jop,
								"Please enter your information",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE);

						/* If "X" was clicked the login process terminates */
						if (status == -1)
							return;
						try {

							/*
							 * Get the information from the TextField and
							 * PasswordField
							 */
							String userName = user.getText();
							String passWord = new String(pwd.getPassword());

							/* The fields are empty. */
							if (userName.equals("") || passWord.equals("")) {
								JOptionPane.showMessageDialog(null,
										"Empty fields!");
								continue;
							} else {
								answer = backOffice.loginOperator(userName,
										passWord);
							}

						} catch (RemoteException e) {
							JOptionPane
									.showMessageDialog(null,
											"The server is down, please try again later");
							return;
						}

						/*
						 * Login was successful, loggedIn is now true so
						 * operator can book charters
						 */
						if (answer.equals("Login successful")) {
							loggedIn = true;
						}
						JOptionPane.showMessageDialog(null, answer);
						pwd.setText("");
					}
					user.setText("");
				} else if (op == 1) {
					title = new JLabel("<html><h3> Register </h3></html>");
					Object[] jop = { title, username, user, passcode, pwd,
							confirmPassword, confirmPwd, email, emailText,
							telephone, telephoneText, company, companyText,
							address, addressText };
					/* User has 3 opportunities to finish register process */
					while (!loggedIn && count++ != 3) {
						status = JOptionPane.showConfirmDialog(null, jop,
								"Please enter your information",
								JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						/* If "X" was clicked the register process terminates */
						if (status == -1)
							return;

						/* Get the information from the PasswordFields */
						pass = new String(pwd.getPassword());
						conf = new String(confirmPwd.getPassword());

						/* Check if the fields are empty. */
						if (!user.getText().equals("")
								&& !emailText.getText().equals("")
								&& !telephoneText.getText().equals("")
								&& !companyText.getText().equals("")
								&& !addressText.getText().equals("")) {
							if (pass.equals(conf)) {
								try {
									/* The fields are empty. */
									if (user.getText().equals("")
											|| pass.equals("")) {
										JOptionPane.showMessageDialog(null,
												"Empty fields!");
										continue;
									} else {
										answer = backOffice.registerOperator(
												companyText.getText(),
												user.getText(),
												addressText.getText(),
												telephoneText.getText(),
												emailText.getText(), pass);
									}

								} catch (RemoteException e) {
									JOptionPane
											.showMessageDialog(null,
													"The server is down, please try again later");
									return;
								}
								/*
								 * Registration was successful, loggedIn is now
								 * true so operator can book charters
								 */
								if (answer
										.equals("Registration was successful")) {
									loggedIn = true;
									JOptionPane.showMessageDialog(null, answer);
								}
							} else {
								JOptionPane
										.showMessageDialog(null,
												"The passwords don't match, please try again");
							}
						} else {
							JOptionPane
									.showMessageDialog(null,
											"You have to fill all the information in order to register");
						}
						pwd.setText("");
						confirmPwd.setText("");
					}
					user.setText("");
				}
			} else {
				JOptionPane
						.showMessageDialog(null, "You are already logged in");
			}
		}
	}

	@SuppressWarnings("serial")
	private class BookingsMenu extends Window implements PropertyChangeListener {

		/* ALL PANELS */
		private JPanel newPanel;
		private JPanel checkPanel;
		private JPanel cancelPanel;
		private JPanel modifyPanel;
		private JPanel charterPanel;
		private JButton charterButton;

		/* NEW BOOKING PANEL */
		private JTextField newFlightID;
		private JTextField seatsNew;
		private JTextField nameNew;
		private JTextField phoneNew;
		private JTextField addressNew;
		private JTextField emailNew;
		private JTextField ccNoNew;
		private JTextArea confirmActionNew;
		private JScrollPane jpNew;

		/* CHECK BOOKING PANEL */
		private JTextField checkFlightID;
		private JTextField checkBookingID;
		private JTextArea confirmActionCheck;

		/* CANCEL BOOKING PANEL */
		private JTextField cancelFlightID;
		private JTextField cancelBookingID;
		private JTextArea confirmActionCancel;
		private JScrollPane jpCancel;

		/* MODIFY BOOKING PANEL */
		private JTextField modifyFlightID;
		private JTextField modifyBookingID;
		private JTextField modifyNewFlightID;
		private JTextArea confirmActionModify;
		private JScrollPane jpModify;

		/* CHARTER BOOKING PANEL */
		private JCalendar jCalendar;
		private JTextField dateCharter;
		private JTextField seatsCharter;
		private JComboBox originCharter;
		private JComboBox destinationCharter;
		private JTextArea confirmActionCharter;
		private JScrollPane jpCharter;

		public BookingsMenu() {

			/* Creates the buttons that redirect to each manager window. */
			CreateButton("New Booking", Color.white, "Books a given flight",
					15, 60, 200, 200, 30);
			CreateButton("Check Booking", Color.white, "Confirm booked flight",
					15, 60, 250, 200, 30);
			CreateButton("Cancel Booking", Color.white, "Cancels a booking",
					15, 60, 300, 200, 30);
			CreateButton("Modify Booking", Color.white,
					"Changes a booking to another flight", 15, 60, 350, 200, 30);
			charterButton = CreateButton("Book Charter", Color.white,
					"Book a Charter Flight", 15, 60, 400, 200, 30);
			charterButton.setVisible(false);

			CreateButton("Return", Color.white, "Go back to the main menu", 15,
					60, 500, 100, 30);

			/*
			 * Creates the sub panels that are displayed accordingly to the
			 * user's choice.
			 */
			newPanel = new JPanel();
			checkPanel = new JPanel();
			cancelPanel = new JPanel();
			modifyPanel = new JPanel();
			charterPanel = new JPanel();

			/* Defines the sub panels. */
			newPanel.setLayout(null);
			newPanel.setBounds(new Rectangle(500, 40, 500, 500));

			newPanel.add(CreateTitle("Flight ID:", Color.black, 15, 60, 20, 70,
					20));
			newPanel.add(newFlightID = CreateBoxInt(20, 170, 20, 80, 20, 0));
			newPanel.add(CreateTitle("Name:", Color.black, 15, 60, 50, 100, 20));
			newPanel.add(nameNew = CreateBoxText(20, 170, 50, 300, 20));
			newPanel.add(CreateTitle("Phone:", Color.black, 15, 60, 80, 100, 20));
			newPanel.add(phoneNew = CreateBoxText(20, 170, 80, 150, 20));
			newPanel.add(CreateTitle("Address:", Color.black, 15, 60, 110, 100,
					20));
			newPanel.add(addressNew = CreateBoxText(20, 170, 110, 300, 20));
			newPanel.add(CreateTitle("E-mail:", Color.black, 15, 60, 140, 100,
					20));
			newPanel.add(emailNew = CreateBoxText(20, 170, 140, 300, 20));
			newPanel.add(CreateTitle("Seats:", Color.black, 15, 60, 170, 100,
					20));
			newPanel.add(seatsNew = CreateBoxInt(20, 170, 170, 80, 20, 0));
			newPanel.add(CreateTitle("CC Number:", Color.black, 15, 60, 200,
					100, 20));
			newPanel.add(ccNoNew = CreateBoxInt(20, 170, 200, 150, 20, 0));
			newPanel.add(confirmActionNew = CreateText(100, 100, 60, 210, 100,
					60));
			jpNew = new JScrollPane(confirmActionNew);
			newPanel.add(jpNew);
			jpNew.setBounds(60, 240, 300, 180);
			newPanel.add(CreateButton("Schedule", Color.white,
					"Schedule your flight", 15, 60, 460, 120, 20));

			checkPanel.setLayout(null);
			checkPanel.setBounds(new Rectangle(500, 40, 500, 500));

			checkPanel.add(CreateTitle("Flight ID:", Color.black, 15, 60, 20,
					70, 20));
			checkPanel
					.add(checkFlightID = CreateBoxInt(20, 150, 20, 80, 20, 0));
			checkPanel.add(CreateTitle("Booking ID:", Color.black, 15, 60, 50,
					90, 20));
			checkPanel
					.add(checkBookingID = CreateBoxInt(20, 150, 50, 80, 20, 0));
			checkPanel.add(confirmActionCheck = CreateText(300, 150, 60, 90,
					400, 150));
			checkPanel.add(CreateButton("Check", Color.white, "Check Booking",
					15, 60, 260, 200, 30));

			cancelPanel.setLayout(null);
			cancelPanel.setBounds(new Rectangle(500, 40, 400, 500));
			cancelPanel.add(CreateTitle("Flight ID:", Color.black, 15, 60, 20,
					70, 20));
			cancelPanel.add(cancelFlightID = CreateBoxInt(20, 140, 20, 80, 20,
					0));
			cancelPanel.add(CreateTitle("Booking Number:", Color.black, 15, 60,
					50, 150, 20));
			cancelPanel.add(cancelBookingID = CreateBoxInt(20, 190, 50, 80, 20,
					0));
			cancelPanel.add(confirmActionCancel = CreateText(300, 150, 60, 90,
					400, 150));
			jpCancel = new JScrollPane(confirmActionCancel);
			cancelPanel.add(jpCancel);
			jpCancel.setBounds(60, 90, 400, 150);
			cancelPanel.add(CreateButton("Cancel", Color.white,
					"Cancel Booking", 15, 60, 260, 200, 30));

			modifyPanel.setLayout(null);
			modifyPanel.setBounds(new Rectangle(500, 40, 400, 500));
			modifyPanel.add(CreateTitle("Flight ID:", Color.black, 15, 60, 20,
					70, 20));
			modifyPanel.add(modifyFlightID = CreateBoxInt(20, 165, 20, 80, 20,
					0));
			modifyPanel.add(CreateTitle("Booking ID:", Color.black, 15, 60, 50,
					100, 20));
			modifyPanel.add(modifyBookingID = CreateBoxInt(20, 165, 50, 80, 20,
					0));
			modifyPanel.add(CreateTitle("New Flight ID:", Color.black, 15, 60,
					80, 120, 20));
			modifyPanel.add(modifyNewFlightID = CreateBoxInt(20, 165, 80, 80,
					20, 0));
			modifyPanel.add(confirmActionModify = CreateText(300, 150, 60, 120,
					300, 150));
			jpModify = new JScrollPane(confirmActionModify);
			modifyPanel.add(jpModify);
			jpModify.setBounds(60, 120, 300, 150);
			modifyPanel.add(CreateButton("Confirm modification", Color.white,
					"Modify a booking", 15, 60, 290, 200, 30));

			charterPanel.setLayout(null);
			charterPanel.setBounds(new Rectangle(500, 40, 400, 500));
			charterPanel.add(CreateTitle("Date:", Color.black, 15, 60, 20, 70,
					20));
			charterPanel.add(dateCharter = CreateBoxText(20, 100, 20, 80, 20));
			charterPanel.add(CreateButton("Charter Date", Color.white,
					"Choose flight date", 15, 60, 50, 120, 30));
			charterPanel.add(CreateTitle("Origin:", Color.black, 15, 60, 90,
					70, 20));
			charterPanel.add(originCharter = CreateComboBox(120, 90, 120, 20,
					destinations));
			charterPanel.add(CreateTitle("Destination:", Color.black, 15, 60,
					120, 100, 20));
			charterPanel.add(destinationCharter = CreateComboBox(150, 120, 120,
					20, destinations));
			charterPanel.add(CreateTitle("Seats:", Color.black, 15, 60, 150,
					50, 20));
			charterPanel.add(seatsCharter = CreateBoxInt(20, 120, 150, 50, 20,
					0));
			charterPanel.add(confirmActionCharter = CreateText(300, 150, 60,
					180, 300, 150));
			jpCharter = new JScrollPane(confirmActionCharter);
			charterPanel.add(jpCharter);
			jpCharter.setBounds(60, 180, 300, 150);
			charterPanel.add(CreateButton("Book Flight", Color.white,
					"Search for a flight", 15, 60, 350, 120, 20));

			/* Adds the sub panels to the main panel. */
			panel.add(newPanel);
			panel.add(checkPanel);
			panel.add(cancelPanel);
			panel.add(modifyPanel);
			panel.add(charterPanel);

			newPanel.setVisible(false);
			newPanel.setOpaque(false);
			checkPanel.setVisible(false);
			checkPanel.setOpaque(false);
			cancelPanel.setVisible(false);
			cancelPanel.setOpaque(false);
			modifyPanel.setVisible(false);
			modifyPanel.setOpaque(false);
			charterPanel.setVisible(false);
			charterPanel.setOpaque(false);

		}

		/*
		 * This function is used when the user enters this menu. We need to set
		 * true the right menu and one of its sub panels.
		 */
		public void entry() {
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			newPanel.setVisible(true);
			/* If the operator is logged in, we set the Charter button visible */
			if (loggedIn) {
				charterButton.setVisible(true);
			}
		}

		public void mouseReleased(MouseEvent e) {
			/*
			 * Every time the user releases a mouse button, the event must be
			 * handled
			 */
			if (e.getComponent().getName().equals("New Booking")) {
				newPanel.setVisible(true);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
				charterPanel.setVisible(false);
				checkPanel.setVisible(false);
			} else if (e.getComponent().getName().equals("Check Booking")) {
				newPanel.setVisible(false);
				checkPanel.setVisible(true);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
				charterPanel.setVisible(false);
			} else if (e.getComponent().getName().equals("Cancel Booking")) {
				newPanel.setVisible(false);
				checkPanel.setVisible(false);
				cancelPanel.setVisible(true);
				modifyPanel.setVisible(false);
				charterPanel.setVisible(false);
			} else if (e.getComponent().getName().equals("Modify Booking")) {
				newPanel.setVisible(false);
				checkPanel.setVisible(false);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(true);
				charterPanel.setVisible(false);
			} else if (e.getComponent().getName().equals("Book Charter")) {
				newPanel.setVisible(false);
				checkPanel.setVisible(false);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
				charterPanel.setVisible(true);
			} else if (e.getComponent().getName().equals("Schedule")) {
				try {
					int id = Integer.parseInt(newFlightID.getText());
					int seats = Integer.parseInt(seatsNew.getText());
					String name = nameNew.getText();
					String address = addressNew.getText();
					String phone = phoneNew.getText();
					String mail = emailNew.getText();
					String cc = ccNoNew.getText();

					boolean validPhone = true, validCC = true;

					// It can't contain only numbers if it's null or empty...
					if (phone == null || phone.length() == 0)
						validPhone = false;
					else {
						if (!(phone.charAt(0) == '+' || Character.isDigit(phone
								.charAt(0)))) {
							validPhone = false;
						}
						for (int i = 1; i < phone.length() && validPhone; i++) {

							// If we find a non-digit character we return false.
							if (!Character.isDigit(phone.charAt(i)))
								validPhone = false;
						}
					}

					// It can't contain only numbers if it's null or empty...
					if (cc == null || cc.length() == 0)
						validCC = false;
					else {
						for (int i = 0; i < cc.length() && validCC; i++) {

							// If we find a non-digit character we return false.
							if (!Character.isDigit(cc.charAt(i)))
								validCC = false;
						}
					}
					if (validPhone && validCC) {
						if (!name.equals("") && !address.equals("")
								&& !mail.equals("")) {
							
							String answer = backOffice.scheduleBooking(id,
									name, address, phone, mail, seats,
									loggedIn, bookingNumber);
							String [] splitted = answer.split(" ");
							String scheduleAnswer = splitted[0];
							if (answer.equals("Innexistent flight")) {
								confirmActionNew
										.setText("There's no such flight.");
								confirmActionNew.setCaretPosition(0);
							} else if (scheduleAnswer.equals("Scheduled")) {
								Double[] bookingInfo = backOffice.bookingPrice(
										id, mail);

								Double price = bookingInfo[0] * seats;
								Double miles = bookingInfo[1] - (price * 10);

								if (miles > 0) {
									int option = JOptionPane
											.showConfirmDialog(
													f,
													"The price of the flight is "
															+ price
															+ "€ and you have travelled "
															+ miles
															+ " miles. Do you want to reduce the final price?",
													"Price Reduction",
													JOptionPane.YES_NO_OPTION);
									// If the user wants to reduce price
									if (option == 0) {
										if (miles * 0.01 < price) {
											price -= (miles * 0.01);
											miles = 0.0;
										} else {
											miles -= price * 100;
											price = 0.0;
										}
										// Update user miles
										backOffice.updateMiles(miles, mail);

									}
								}
								confirmActionNew
										.setText("Booking scheduled, with booking number "
												+ splitted[1]
												+ " flight number "
												+ id
												+ " and price is "
												+ price
												+ "€.");
								confirmActionNew.setCaretPosition(0);
								//bookingNumber++;
							} else if (answer.equals("Over")) {
								confirmActionNew
										.setText("This flight is over, please choose another.");
								confirmActionNew.setCaretPosition(0);
							} else if (answer.equals("Cancelled")) {
								confirmActionNew
										.setText("This flight was cancelled, please choose another.");
								confirmActionNew.setCaretPosition(0);
							} else if (answer.equals("Charter")) {
								confirmActionNew
										.setText("Sorry, but only operators can book charter flights.");
								confirmActionNew.setCaretPosition(0);
							} else {
								int number = Integer
										.parseInt(answer.split(" ")[1]);
								if (number == 0) {
									confirmActionNew
											.setText("This flight is closed.");
									confirmActionNew.setCaretPosition(0);
								} else {
									confirmActionNew.setText("There are only "
											+ number + " empty seats.");
									confirmActionNew.setCaretPosition(0);
								}

							}
						} else {
							confirmActionNew.setText("Empty field(s).");
							confirmActionNew.setCaretPosition(0);
						}
					} else {
						confirmActionNew
								.setText("Invalid phone number or\ncredit card number.");
						confirmActionNew.setCaretPosition(0);
					}

				} catch (Exception e1) {
					confirmActionNew.setText("Invalid field(s).\n");
					e1.printStackTrace();
				}
			} else if (e.getComponent().getName().equals("Check")) {
				try {
					int idFlight = Integer.parseInt(checkFlightID.getText());
					int idBooking = Integer.parseInt(checkBookingID.getText());

					confirmActionCheck.setText(backOffice.getBookingInfo(
							idFlight, idBooking));
				} catch (NumberFormatException e1) {
					confirmActionCheck.setText("Invalid flight or booking ID");
				} catch (RemoteException e1) {
					confirmActionCheck
							.setText("The system is not availabe, please try again later");
				}
			} else if (e.getComponent().getName()
					.equals("Confirm modification")) {
				try {
					int idFlight = Integer.parseInt(modifyFlightID.getText());
					int idBooking = Integer.parseInt(modifyBookingID.getText());
					int newIdFlight = Integer.parseInt(modifyNewFlightID
							.getText());

					confirmActionModify.setText(backOffice.modifyBooking(
							idFlight, idBooking, newIdFlight, loggedIn,
							bookingNumber));

				} catch (NumberFormatException e1) {
					confirmActionModify.setText("Invalid flight or booking ID");
				} catch (RemoteException e1) {
				}

			} else if (e.getComponent().getName().equals("Cancel")) {
				try {
					int idFlight = Integer.parseInt(cancelFlightID.getText());
					int idBooking = Integer.parseInt(cancelBookingID.getText());

					String answer = backOffice.cancelBooking(idFlight,
							idBooking);
					if (answer.equals("Innexistent flight")) {
						confirmActionCancel.setText("There's no such flight.");
					} else if (answer.equals("Innexistent booking")) {
						confirmActionCancel
								.setText("There's no such booking in this flight.");
					} else if (answer.equals("Cancelled")) {
						confirmActionCancel.setText("Booking cancelled.");
					}

				} catch (NumberFormatException e2) {
					confirmActionCancel.setText("Invalid field(s).\n");
				} catch (RemoteException e3) {
					confirmActionCancel
							.setText("The system is not availabe at the moment");
				}
			} else if (e.getComponent().getName().equals("Book Flight")) {
				int day, month, year;
				String[] dateFields = dateCharter.getText().split("/");
				try {
					day = Integer.parseInt(dateFields[0]);
					month = Integer.parseInt(dateFields[1]);
					year = Integer.parseInt(dateFields[2]);

					try {
						confirmActionCharter.setText(backOffice
								.scheduleCharter(new GregorianCalendar(year,
										month - 1, day), originCharter
										.getSelectedItem().toString(),
										destinationCharter.getSelectedItem()
												.toString(), Integer
												.parseInt(seatsCharter
														.getText())));
					} catch (RemoteException e1) {
						confirmActionCharter
								.setText("The server is not available, please try again later");
					}

				} catch (NumberFormatException e1) {
					confirmActionCharter.setText("Invalid Date");
				}

			} else if (e.getComponent().getName().equals("Charter Date")) {
				JFrame date = new JFrame("Booking");
				jCalendar = new JCalendar();

				date.getContentPane().add(jCalendar);
				date.pack();
				date.setVisible(true);
				jCalendar.addPropertyChangeListener(this);

			} else if (e.getComponent().getName().equals("Return")) {
				newPanel.setVisible(false);
				cancelPanel.setVisible(false);
				modifyPanel.setVisible(false);
				charterPanel.setVisible(false);
				checkPanel.setVisible(false);

				bookingsMenu.setVisible(false);
				menu.setVisible(true);
			}
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Calendar cal = jCalendar.getCalendar();
			GregorianCalendar calendar = new GregorianCalendar(
					cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			dateCharter.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ calendar.get(Calendar.MONTH) + "/"
					+ calendar.get(Calendar.YEAR));

		}
	}

	@SuppressWarnings("serial")
	private class SendFeedBackMenu extends Window {
		FrontOffice frontOffice;
		JPanel positivePanel;
		JPanel negativePanel;
		/* Positive Panel Message Area */
		private JTextArea posMsgArea;
		/* Negative Panel Message Area */
		private JTextArea negMsgArea;
		private JTextArea displayN;
		private JTextArea displayP;

		public SendFeedBackMenu(FrontOffice f) {
			frontOffice = f;
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("Positive Feedback", Color.white,
					"Send a positive Sugestion", 15, 60, 200, 200, 30);
			CreateButton("Negative Feedback", Color.white,
					"Send a reclamation", 15, 60, 250, 200, 30);
			CreateButton("Return", Color.white, "Go back to the main menu", 15,
					60, 500, 100, 30);

			/*
			 * Creates the subpanels that are displayed accordingly to the
			 * user's choice.
			 */
			positivePanel = new JPanel();
			negativePanel = new JPanel();

			/* Defines the subpanels. */
			positivePanel.setLayout(null);
			positivePanel.setBounds(new Rectangle(500, 40, 500, 400));
			positivePanel.add(CreateButton("Send", Color.white,
					"Send your message", 15, 280, 20, 200, 30));
			positivePanel.add(CreateTitle("Message (Positive Comment):",
					Color.black, 15, 20, 20, 250, 20));
			positivePanel
					.add(posMsgArea = CreateText(10, 50, 40, 60, 350, 300));
			displayP = CreateText(10, 50, 40, 375, 150, 20);
			positivePanel.add(displayP);

			negativePanel.setLayout(null);
			negativePanel.setBounds(new Rectangle(500, 40, 500, 400));
			negativePanel.add(CreateButton("Send ", Color.white,
					"Send your message", 15, 280, 20, 200, 30));
			negativePanel.add(CreateTitle("Message (Negative Comment):",
					Color.black, 15, 20, 20, 250, 20));
			negativePanel
					.add(negMsgArea = CreateText(10, 50, 40, 60, 350, 300));
			displayN = CreateText(10, 50, 40, 375, 150, 20);
			negativePanel.add(displayN);

			/* Adds the subpanels to the main panel. */
			panel.add(positivePanel);
			panel.add(negativePanel);

			negativePanel.setVisible(false);
			negativePanel.setOpaque(false);
			positivePanel.setVisible(false);
			positivePanel.setOpaque(false);

		}

		public void entry() {
			setVisible(true);
			/* As default, we have the Buy Plane Menu. */
			positivePanel.setVisible(true);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getComponent().getName().equals("Positive Feedback")) {
				negativePanel.setVisible(false);
				positivePanel.setVisible(true);
			} else if (e.getComponent().getName().equals("Negative Feedback")) {
				negativePanel.setVisible(true);
				positivePanel.setVisible(false);
			} else if (e.getComponent().getName().equals("Return")) {
				negativePanel.setVisible(false);
				positivePanel.setVisible(false);

				sendFeedBackMenu.setVisible(false);
				menu.setVisible(true);
			} else if (e.getComponent().getName().equals("Send")) {
				System.out.println(posMsgArea.getText());
				displayP.setText(frontOffice.sendPositiveFeedBack(new Message(posMsgArea.getText(), new GregorianCalendar())));
			} else if (e.getComponent().getName().equals("Send ")) {
				displayN.setText(frontOffice.sendNegativeFeedBack(new Message(negMsgArea.getText(), new GregorianCalendar())));
			}

		}
	}

	@SuppressWarnings("serial")
	private class SearchMenu extends Window implements PropertyChangeListener {

		private JPanel listPanel;
		/* NEWPANEL */
		private JPanel newPanel;
		private JCalendar jCalendar;
		private GregorianCalendar calendar;

		private JTextField dateNew;
		private JComboBox originNew;
		private JComboBox destinationNew;
		private JTextArea confirmActionNew;
		private JLabel idText;
		private JTextField newID;
		private JButton bookNew;

		/* LISTPANEL */
		private JTextArea listArea;

		public SearchMenu() {
			/* Creates the buttons that redirect to each manager window. */
			CreateButton("List Flights", Color.white,
					"Lists all the upcoming flights", 15, 60, 200, 150, 30);
			CreateButton("Find Flight", Color.white, "Find your flight", 15,
					60, 250, 150, 30);

			CreateButton("Return", Color.white, "Go back to the main menu", 15,
					60, 500, 100, 30);

			newPanel = new JPanel();
			listPanel = new JPanel();

			/* Defines the sub panels. */
			newPanel.setLayout(null);
			newPanel.setBounds(new Rectangle(500, 40, 500, 500));
			newPanel.add(CreateTitle("Date:", Color.black, 15, 60, 20, 70, 20));
			newPanel.add(dateNew = CreateBoxText(20, 100, 20, 80, 20));
			calendar = new GregorianCalendar();
			dateNew.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.YEAR));
			newPanel.add(CreateButton("Booking Date", Color.white,
					"Choose flight date", 15, 60, 50, 150, 30));
			newPanel.add(CreateTitle("Origin:", Color.black, 15, 60, 90, 70, 20));
			newPanel.add(CreateButton("Check Price", Color.white,
					"Check the flight's price", 15, 60, 340, 150, 30));
			newPanel.add(CreateButton("Find", Color.white, "Find your flight",
					15, 60, 380, 150, 30));

			newPanel.add(originNew = CreateComboBox(120, 90, 120, 20,
					destinations));
			newPanel.add(CreateTitle("Destination:", Color.black, 15, 60, 120,
					100, 20));
			newPanel.add(destinationNew = CreateComboBox(150, 120, 120, 20,
					destinations));
			newPanel.add(confirmActionNew = CreateText(300, 150, 60, 180, 300,
					150));
			newPanel.add(idText = CreateTitle("Flight ID: ", Color.black, 15,
					220, 345, 100, 20));
			idText.setVisible(false);
			newPanel.add(newID = CreateBoxInt(20, 290, 345, 70, 20, 0));
			newID.setVisible(false);
			newPanel.add(bookNew = CreateButton("Book", Color.white,
					"Book a Flight", 15, 220, 380, 140, 30));
			bookNew.setVisible(false);

			listPanel.setLayout(null);
			listPanel.setBounds(new Rectangle(500, 40, 500, 400));
			listPanel.add(CreateTitle("LIST OF FLIGHTS:", Color.black, 15, 20,
					20, 150, 20));
			listPanel
					.add(CreateTitle(
							"     ID      PLANE        DESTINATION                  TIME",
							Color.black, 15, 20, 40, 400, 20));
			listPanel.add(listArea = CreateText(10, 50, 40, 60, 450, 320));

			/* Adds the sub panels to the main panel. */
			panel.add(newPanel);
			panel.add(listPanel);

			newPanel.setVisible(false);
			newPanel.setOpaque(false);
			listPanel.setVisible(false);
			listPanel.setOpaque(false);
		}

		/*
		 * This function is used when the user enters this menu. We need to set
		 * true the right menu and one of its subpanels.
		 */
		public void entry() {
			setVisible(true);
			/* As default, we have the List Panel Menu. */
			listPanel.setVisible(true);
		}

		public void mouseReleased(MouseEvent e) {
			if (e.getComponent().getName().equals("Find Flight")) {
				listPanel.setVisible(false);
				newPanel.setVisible(true);
			} else if (e.getComponent().getName().equals("Find")) {
				String[] dateFields = dateNew.getText().split("/");
				int day = Integer.parseInt(dateFields[0]);
				int month = Integer.parseInt(dateFields[1]);
				int year = Integer.parseInt(dateFields[2]);

				String answer;
				try {
					answer = backOffice.findFlights(year, month, day,
							(String) originNew.getSelectedItem(),
							(String) destinationNew.getSelectedItem());
					idText.setVisible(true);
					newID.setVisible(true);
					bookNew.setVisible(true);

				} catch (RemoteException e1) {
					answer = "The system is down.";
				}
				confirmActionNew.setText(answer);

			} else if (e.getComponent().getName().equals("Book")) {
				idText.setVisible(false);
				newID.setVisible(false);
				bookNew.setVisible(false);
				newPanel.setVisible(false);
				searchMenu.setVisible(false);
				bookingsMenu.newFlightID.setText(newID.getText());
				bookingsMenu.entry();
			} else if (e.getComponent().getName().equals("List Flights")) {
				newPanel.setVisible(false);
				listPanel.setVisible(true);

				try {
					listArea.setText(backOffice.listFlights());
				} catch (RemoteException e1) {
					listArea.setText("The system is not available, please try again later");
				}
			} else if (e.getComponent().getName().equals("Booking Date")) {
				JFrame date = new JFrame("Booking");
				jCalendar = new JCalendar();

				date.getContentPane().add(jCalendar);
				date.pack();
				date.setVisible(true);
				jCalendar.addPropertyChangeListener(this);
			} else if (e.getComponent().getName().equals("Check Price")) {
				double price = 0.0;
				String orig = (String) originNew.getSelectedItem();
				String dest = (String) destinationNew.getSelectedItem();

				try {
					price = backOffice.getPrice(orig, dest);
				} catch (RemoteException e1) {
					confirmActionNew
							.setText("The system is not available, please try again later.");
				}
				confirmActionNew.setText("The price is " + price + "€.");
			} else if (e.getComponent().getName().equals("Return")) {
				searchMenu.setVisible(false);
				newPanel.setVisible(false);
				listPanel.setVisible(false);
				menu.setVisible(true);
			}
		}

		/* Every time the user selects a new date, an event is generated */
		public void propertyChange(PropertyChangeEvent evt) {
			Calendar cal = jCalendar.getCalendar();
			calendar = new GregorianCalendar(cal.get(Calendar.YEAR),
					cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			dateNew.setText(calendar.get(Calendar.DAY_OF_MONTH) + "/"
					+ (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.YEAR));

		}
	}
}
