package backOffice;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import org.prevayler.*;
import common.Airplane;
import common.FileManager;

public class PlanesManager {
	/* The list of registered planes. */
	private Vector <Airplane> planesList;
	private Prevayler prevayler;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	/* The constructor. */
	public PlanesManager(){
		super();
		
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Vector<Airplane>(), "PlanesList");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Something went really bad!");
			System.exit(0);
		} 
		planesList=(Vector <Airplane>) (prevayler.prevalentSystem());
		
		
		/*if(FileManager.loadObjectFromFile("planesList", planesList) == null)
			planesList = new Vector<Airplane>();
		*/
	}
	
	
	/**
	 * 
	 * Write Transactions
	 */
	
	
	/* Adds a new plane to the system. */
	public void addPlane(Airplane airplane){
		prevayler.execute(new addPlane(airplane));
		
		//planesList.add(airplane);
	}
	
	/* Removes a plane from the system. */
	public void removePlane(Airplane airplane){
		
		prevayler.execute(new removePlane(airplane));
		
		//planesList.remove(airplane);
	}
	/**
	 * 
	 * Read Transactions
	 */
	
	/* Getters. */
	public Vector<Airplane> getPlanesList() {
		return planesList;
	}
	
	public int getNumPlanes(){
		return planesList.size();
	}
	
	public int getNumPlanes(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Airplane a:planesList)
			if(a.getDate().after(beginning) && a.getDate().before(end))
				num++;
		
		return num;
	}
	
	
	
}


class addPlane implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Airplane airplane;
	


	public addPlane(Airplane airplane){
		this.airplane=airplane;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Airplane>)arg0).add(airplane);
	}
	
	
}


class removePlane implements Transaction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Airplane airplane;
	


	public removePlane(Airplane airplane){
		this.airplane=airplane;
		
	}
	
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Airplane>)arg0).remove(airplane);
	}
	
	
}