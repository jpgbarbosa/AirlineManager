package common;

import java.rmi.*;
import java.util.Vector;

import messages.Feedback;

public interface BackOfficeRemoteInterface extends Remote{
	
	abstract void sendPositiveFeedback(Feedback feedback) throws RemoteException;
	
	abstract void sendNegativeFeedback(Feedback feedback) throws RemoteException;
	
	abstract String registerOperator(String comp, String name, String addr, String phone, String mail,String password) throws RemoteException;
	
	abstract String loginOperator(String user, String pass) throws RemoteException;
	
	abstract Vector<String> getDestinations() throws RemoteException;
	
	abstract double getPrice(String orig, String dest) throws RemoteException;
	
	abstract String scheduleRegularFlight(int idFlight, String name, String address, String phone, String mail, int seats) throws RemoteException;
	
}
