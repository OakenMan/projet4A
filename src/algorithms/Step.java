package algorithms;

import model.Graph;

/**
 * Représente une étape lors de l'affichage de nos algorithmes et de leurs résolutions.
 * @author Aymeric Le Moal
 * @author Tom Suchel
 */
public class Step {

	/*===== ATTRIBUTES =====*/
	private Graph graph;
	private String info;
	
	/*===== BUILDER =====*/
	public Step(Graph graph) {
		this.graph = graph;
		this.info = "";
	}
	
	public Step(Graph graph, String info) {
		this.graph = graph;
		this.info = info;
	}

	/*===== GETTERS AND SETTERS =====*/
	public Graph getGraph() {
		return graph;
	}

	public String getInfo() {
		return info;
	}
}
