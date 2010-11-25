package common;

public class RFlight {
	private String origin;
	private String destination;
	
	public RFlight(String origin, String destination){
		this.origin = origin;
		this.destination = destination;
	}
	
	public String getOrigin(){
		return origin;
	}
	
	public String getDestination(){
		return destination;
	}
}
