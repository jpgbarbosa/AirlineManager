package backOffice;


import java.util.Vector;

import common.Airplane;

public class PlanesManager {
	/* The list of registered planes. */
	private Vector <Airplane> planesList;
	
	/* The constructor. */
	public PlanesManager(){
		planesList = new Vector<Airplane>();
	}
	
	/* Adds a new plane to the system. */
	public void addPlane(Airplane airplane){
		planesList.add(airplane);
	}
	
	/* Removes a plane from the system. */
	public void removePlane(Airplane airplane){
		planesList.remove(airplane);
	}

	/* Getters and setters. */
	public Vector<Airplane> getPlanesList() {
		return planesList;
	}

	public void setPlanesList(Vector<Airplane> planesList) {
		this.planesList = planesList;
	}
	
}
