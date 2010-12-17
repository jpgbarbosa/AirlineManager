package backOffice;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import org.prevayler.*;
import common.Airplane;

public class PlanesManager {
	/* The list of registered planes. */
	private Vector <Airplane> planesList;
	private Prevayler prevayler;
	private static int idCreator=0;
	public Prevayler getPrevayler() {
		return prevayler;
	}
	/* The constructor. */
	@SuppressWarnings("unchecked")
	public PlanesManager(){
		super();
		
		try {
			prevayler = PrevaylerFactory.createPrevayler(new Vector<Airplane>(), "PlanesList");
			
		} catch (Exception e) {
			System.out.println("Prevayler error, exiting.");
			System.exit(-1);
		} 
		planesList=(Vector <Airplane>) (prevayler.prevalentSystem());
		
		if(planesList.size()>0){
			idCreator=getLastID();
		}
		
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
		airplane.setId(idCreator);
		prevayler.execute(new addPlane(airplane));
		idCreator++;
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
	
	/**
	 * Fun‹o utilizada para obter o nœmero de Avi›es dispon’veis na Companhia.
	 * @return Nœmero de avi›es
	 */
	public int getNumPlanes(){
		return planesList.size();
	}
	
	/**
	 * Fun‹o utilizada para obter o nœmero de Avi›es dispon’veis na Companhia entre as datas passadas como argumento da fun‹o.
	 * @param beginning Data de inicio
	 * @param end Data de fim
	 * @return Nœmero de avi›es
	 */
	public int getNumPlanes(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;
		
		for(Airplane a:planesList){
			if(a.getDate().after(beginning) && a.getDate().before(end))
				num++;
		}
		return num;
	}
	
	private int getLastID(){
		int id=0;
		for(Airplane a:planesList)
			if(a.getId()>id){
				id=a.getId();
			}
		id++;
		return id;
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
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {
		
		((Vector<Airplane>)arg0).remove(airplane);
	}
	
	
}