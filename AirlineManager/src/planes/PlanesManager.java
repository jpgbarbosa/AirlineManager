//COMPLETELY CHECKED

package planes;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import org.prevayler.*;

/**
 * This class is responsible for managing all the airplanes. It stores information about the current planes
 * and provides methods to manage them.
 * 
 * @author Daniela Fontes, Ivo Correia, Jo‹o Penetra, Jo‹o Barbosa, Ricardo Bernardino
 *
 */
public class PlanesManager {
	/* The list of registered planes. */
	private Vector<Airplane> planesList;
	private Prevayler prevayler;
	private static int idCreator = 0;

	/**
	 * Creates a new prevayler to store planesList and updates the global ID of each plane to the
	 * available
	 */
	@SuppressWarnings("unchecked")
	public PlanesManager() {
		super();

		try {
			prevayler = PrevaylerFactory.createPrevayler(
					new Vector<Airplane>(), "PlanesList");

		} catch (Exception e) {
			System.out.println("Prevayler error, exiting.");
			System.exit(-1);
		}
		planesList = (Vector<Airplane>) (prevayler.prevalentSystem());

		if (planesList.size() > 0) {
			idCreator = getLastID();
		}
	}
	
	/**
	 * Adds a new plane to the system.
	 * 
	 * @param airplane
	 */
	public void addPlane(Airplane airplane) {
		airplane.setId(idCreator);
		prevayler.execute(new addPlane(airplane));
		idCreator++;
	}

	/**
	 * Removes a plane from the system.
	 * 
	 * @param airplane
	 */
	public void removePlane(Airplane airplane) {

		prevayler.execute(new removePlane(airplane));
	}

	/* Getters and Setters */
	public Prevayler getPrevayler() {
		return prevayler;
	}
	
	public Vector<Airplane> getPlanesList() {
		return planesList;
	}

	
	/**
	 * Function used to obtain the number of Plains available at the company.
	 * @return Number of planes.
	 */
	public int getNumPlanes(){
		return planesList.size();
	}
	
	/**
	 * Function used to obtain the number of planes available at the company between the given dates.
	 * @param beginning Beginning date.
	 * @param end End date.
	 * @return Number of planes.
	 */
	public int getNumPlanes(GregorianCalendar beginning, GregorianCalendar end){
		int num = 0;

		for (Airplane a : planesList) {
			if (a.getDate().after(beginning) && a.getDate().before(end))
				num++;
		}
		return num;
	}

	private int getLastID() {
		int id = 0;
		for (Airplane a : planesList)
			if (a.getId() > id) {
				id = a.getId();
			}
		id++;
		return id;
	}

}

class addPlane implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Airplane airplane;

	public addPlane(Airplane airplane) {
		this.airplane = airplane;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {

		((Vector<Airplane>) arg0).add(airplane);
	}

}

class removePlane implements Transaction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Airplane airplane;

	public removePlane(Airplane airplane) {
		this.airplane = airplane;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeOn(Object arg0, Date arg1) {

		((Vector<Airplane>) arg0).remove(airplane);
	}

}