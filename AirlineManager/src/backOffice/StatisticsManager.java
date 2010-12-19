//COMPLETELY CHECKED

package backOffice;

import java.util.GregorianCalendar;

import planes.PlanesManager;

import messages.FeedBackManager;

import flights.FlightsManager;

/**
 * Class responsible for generating the Statistics of the Application.
 * 
 * @author Daniela Fontes, Ivo Correia, João Penetra, João Barbosa, Ricardo
 *         Bernardino
 * 
 */
public class StatisticsManager {
	private FeedBackManager feedBackManager;
	private FlightsManager flightsManager;
	private PlanesManager planesManager;

	/* The constructor. */
	public StatisticsManager(FeedBackManager feedBackManager,
			FlightsManager flightsManager, PlanesManager planesManager) {
		this.feedBackManager = feedBackManager;
		this.flightsManager = flightsManager;
		this.planesManager = planesManager;
	}

	/**
	 * Generates the statistics of the different managers.
	 * 
	 * @param beginning
	 *            Beginning Date.
	 * @param end
	 *            End Date.
	 * @return Returns a String with the statistics.
	 */
	public String generate(GregorianCalendar beginning, GregorianCalendar end) {
		StringBuilder output = new StringBuilder();
		int [] outcome;
		
		if (beginning == null && end == null) {
			outcome = flightsManager.getNumFlights();
			
			output.append("Estatisticas:\n\nNumero de Avioes Disponiveis: "
					+ planesManager.getNumPlanes());
			output.append("\nNumero de Voos Realizados: "
					+ outcome[0]);
			output.append("\nNumero de Voos Cancelados: "
					+ outcome[1]);
			output.append("\nTaxa de Ocupacao dos Voos: "
					+ flightsManager.getOccupation() + "%");
			output.append("\n\nNumero de Feedbacks Positivos: "
					+ feedBackManager.getNumPositive());
			output.append("\nNumero de Feedbacks Negativos: "
					+ feedBackManager.getNumNegative());
			output.append("\n");

			return output.toString();
		}

		outcome = flightsManager.getNumFlights(beginning, end);
		
		output.append("Estatisticas:\n\nNumero de Avioes Disponiveis: "
				+ planesManager.getNumPlanes(beginning, end));
		output.append("\nNumero de Voos Realizados: "
				+ outcome[0]);
		output.append("\nNumero de Voos Cancelados: "
				+ outcome[1]);
		output.append("\nTaxa de Ocupacao dos Voos: "
				+ flightsManager.getOccupation(beginning, end) + "%");
		output.append("\n\nNumero de Feedbacks Positivos: "
				+ feedBackManager.getNumPositive(beginning, end));
		output.append("\nNumero de Feedbacks Negativos: "
				+ feedBackManager.getNumNegative(beginning, end));
		output.append("\n");

		return output.toString();
	}
}
