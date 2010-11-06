package backOffice;


import java.util.GregorianCalendar;
import java.util.Vector;

import common.Airplane;
import common.FileManager;

public class PlanesManager {
	/* The list of registered planes. */
	private Vector <Airplane> planesList;
	
	/* The constructor. */
	public PlanesManager(){
		if(FileManager.loadObjectFromFile("planesList", planesList) == null)
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
