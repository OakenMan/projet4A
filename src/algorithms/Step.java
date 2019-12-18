package algorithms;

import model.Graph;

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
