package algorithms;

import model.Graph;

/**
 * Représente une étape lors de l'affichage de nos algorithmes et de leurs résolutions.
 */
public class Step {

	/*===== ATTRIBUTES =====*/
	/** Le graph lié à cette étape **/
	private Graph graph;
	/** Le texte à afficher en plus du graphe **/
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
