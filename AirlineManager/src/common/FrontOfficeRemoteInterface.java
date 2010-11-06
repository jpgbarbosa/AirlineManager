package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FrontOfficeRemoteInterface extends Remote {
	
	abstract void sendMessage(String message) throws RemoteException;
}
