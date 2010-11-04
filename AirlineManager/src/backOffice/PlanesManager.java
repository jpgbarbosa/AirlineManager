package backOffice;

import java.util.LinkedList;

import common.Airplane;

public class PlanesManager {
	/* The list of registered planes. */
	private LinkedList <Airplane> planesList;
	
	/* The constructor. */
	public PlanesManager(){
		planesList = new LinkedList<Airplane>();
	}
	
	/* Adds a new plane to the system. */
	public void addPlane(Airplane airplane){
		planesList.add(airplane);
	}
	
	/* Removes a plane from the system. */
	public void removePlane(Airplane airplane){
		planesList.remove(airplane);
	}
	
}
