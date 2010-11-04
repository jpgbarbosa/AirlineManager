package backOffice;

public class StatisticsManager {
	private FeedBackManager feedBackManager;
	private FlightsManager flightsManager;
	private PlanesManager planesManager;

	/* The constructor. */
	public StatisticsManager(FeedBackManager feedBackManager, FlightsManager flightsManager, PlanesManager planesManager){
		this.feedBackManager = feedBackManager;
		this.flightsManager = flightsManager;
		this.planesManager = planesManager;
	}
	
	public String generate(){
		StringBuilder output = new StringBuilder();
		output.append("Estatisticas:\n\nNumero de Avioes Disponiveis: "+planesManager.getNumPlanes());
		output.append("\nNumero de Voos Registados: "+flightsManager.getNumFlights()); // falta implementar
		output.append("\nNumero de Voos Cancalados: "+flightsManager.getNumCancelled()); //falta implementar
		output.append("\nTaxa de Ocupacao dos Voos: "+flightsManager.getOccupation());
		output.append("\n\nNumero de Feedbacks Positivos: "+feedBackManager.getNumPositive());
		output.append("\nNumero de Feedbacks Negativos: "+feedBackManager.getNumNegative());
		output.append("\n");
		
		return output.toString();
	}
}
