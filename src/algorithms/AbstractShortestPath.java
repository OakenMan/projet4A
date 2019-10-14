package algorithms;

import java.util.ArrayList;

import com.mxgraph.view.mxGraph;

public abstract class AbstractShortestPath implements ShortestPath {

	private mxGraph graph;
	private ArrayList<mxGraph> steps;
	private int nbSteps;
	private int currentStep;
	
	public AbstractShortestPath(mxGraph graph) {
		steps = new ArrayList<mxGraph>();
		this.graph = graph;
		findShortestPath();
		currentStep = 0;
	}
	
	/*===== METHODS =====*/
	public abstract void findShortestPath();
	
	public mxGraph getFirstStep() {
		currentStep = 0;
		return steps.get(currentStep);
	}
	
	public mxGraph getLastStep() {
		currentStep = nbSteps-1;
		return steps.get(currentStep);
	}
	
	public mxGraph getPreviousStep() throws Exception {
		if(currentStep > 0) {
			currentStep--;
			return steps.get(currentStep);
		}
		else {
			throw new Exception("Can't go to previous step");
		}
	}
	
	public mxGraph getNextStep() throws Exception {
		if(currentStep < nbSteps-1) {
			currentStep++;
			return steps.get(currentStep);
		}
		else {
			throw new Exception("Can't go to next step");
		}
	}
}


/**
 * lors d'un changement d'algo :
 * AbstractShortestPath sp = new Dijkstra(graph); ---> nouveau calcul
 * 
 * bouton << 
 * graph = sp.getFirstStep();
 * view.setGraph(graph);
 * 
 **/
