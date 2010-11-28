package common;

import java.rmi.*;
import java.util.GregorianCalendar;
import java.util.Vector;

import bookings.Booking;

import messages.Feedback;

public interface BackOfficeRemoteInterface extends Remote{
	
	abstract void sendPositiveFeedback(Feedback feedback) throws RemoteException;
	
	abstract void sendNegativeFeedback(Feedback feedback) throws RemoteException;
	
	abstract String registerOperator(String comp, String name, String addr, String phone, String mail,String password) throws RemoteException;
	
	abstract String loginOperator(String user, String pass) throws RemoteException;
	
	abstract Vector<String> getDestinations() throws RemoteException;
	
	abstract double getPrice(String orig, String dest) throws RemoteException;
	
	abstract String scheduleCharter(GregorianCalendar date, String origin, String destination,int seats) throws RemoteException;
	
	abstract String scheduleBooking(int idFlight, String name, String address, String phone, String mail, int seats, boolean isOperator, int bookingNumber) throws RemoteException;
	
	abstract String modifyBooking(int idFlight, int idBooking, int idNewFlight, boolean isOperator, int bookingNumber) throws RemoteException;
	
	abstract String cancelBooking(int idFlight, int idBooking) throws RemoteException;

	abstract String listFlights() throws RemoteException;
	
	abstract Booking getBookingInfo(int idFlight, int idBooking) throws RemoteException;
}
