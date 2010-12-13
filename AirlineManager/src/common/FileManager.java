package common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Constants;

public class FileManager {
	
	public static void saveObjectToFile(String dir, Object obj){
		ObjectOutputStream oS;
		
		try {
			oS = new ObjectOutputStream(new FileOutputStream(dir));
			oS.writeObject(obj);
		} catch (FileNotFoundException e) {
			if (Constants.DEBUGGING){
				System.out.println("The "+dir+" file couldn't be found...");
			}
		} catch (IOException e) {
			if (Constants.DEBUGGING){
				System.out.println("IO in saveObjectToFile (fileManager): " + e);
			}
		}
	}
	
	public static Object loadObjectFromFile(String dir, Object obj){
		ObjectInputStream iS;
		
		/* We now read the list of flights. */
		try {
			iS = new ObjectInputStream(new FileInputStream(dir));
			return iS.readObject();
		} catch (FileNotFoundException e) {
			if (Constants.DEBUGGING){
				System.out.println("The " + dir + " file couldn't be found...");
			}
			return null;
		} catch (ClassNotFoundException e) {
			if (Constants.DEBUGGING){
				System.out.println("ClassNotFound in loadObjectFromFile (fileManager): " + e);
			}
			return null;
		}catch (IOException e) {
			if (Constants.DEBUGGING){
				System.out.println("fileManager IO in loadObjectFromFile: " + e);
			}
			return null;
		}
	}
	
	private static BufferedReader fR;
	
	
	
	
	// - - - - - TEXTO - - -  - - //
	

	public static boolean  abreLeitura(String nomeDoFicheiro){
		try{
			fR = new BufferedReader(new FileReader(nomeDoFicheiro));
			return true;
		}catch(IOException i){
			return false;
		}
	}
	
	
	
	public static String lerLinha() throws IOException{
		return fR.readLine();
	}
	
	
	public static boolean fechaLeitura(){
		try{
			fR.close();
			return true;
		}catch(IOException i){
			return false;
		}
		
	}
}
