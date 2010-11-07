package common;

import java.rmi.*;

import messages.Feedback;

public interface BackOfficeRemoteInterface extends Remote{
	
	abstract void sendPositiveFeedback(Feedback feedback) throws RemoteException;
	
	abstract void sendNegativeFeedback(Feedback feedback) throws RemoteException;
	
	abstract String registerOperator(String comp, String name, String addr, String phone, String mail,String password) throws RemoteException;
	
	abstract String loginOperator(String user, String pass) throws RemoteException;
	
	
}
