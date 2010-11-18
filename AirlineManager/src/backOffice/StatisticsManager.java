package backOffice;

import java.util.GregorianCalendar;

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
	
	
	public String generate(GregorianCalendar beginning, GregorianCalendar end){
		StringBuilder output = new StringBuilder();
		
		if(beginning == null && end==null){
			output.append("Estatisticas:\n\nNumero de Avioes Disponiveis: "+planesManager.getNumPlanes());
			output.append("\nNumero de Voos Registados: "+flightsManager.getNumFlights()); 
			output.append("\nNumero de Voos Cancelados: "+flightsManager.getNumCancelled()); //falta implementar
			output.append("\nTaxa de Ocupacao dos Voos: "+flightsManager.getOccupation()+"%");
			output.append("\n\nNumero de Feedbacks Positivos: "+feedBackManager.getNumPositive());
			output.append("\nNumero de Feedbacks Negativos: "+feedBackManager.getNumNegative());
			output.append("\n");
			
			return output.toString();
		}
		
		output.append("Estatisticas:\n\nNumero de Avioes Disponiveis: "+planesManager.getNumPlanes(beginning, end));
		output.append("\nNumero de Voos Registados: "+flightsManager.getNumFlights(beginning, end));
		output.append("\nNumero de Voos Cancelados: "+flightsManager.getNumCancelled(beginning, end)); //falta implementar
		output.append("\nTaxa de Ocupacao dos Voos: "+flightsManager.getOccupation(beginning, end)+"%");
		output.append("\n\nNumero de Feedbacks Positivos: "+feedBackManager.getNumPositive(beginning, end));
		output.append("\nNumero de Feedbacks Negativos: "+feedBackManager.getNumNegative(beginning, end));
		output.append("\n");
		
		return output.toString();
	}
}
