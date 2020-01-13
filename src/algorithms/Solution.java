package algorithms;

import java.util.ArrayList;

import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe est utilisée dans le cadre de la résolution du problème du plus court chemin. Elle représente une solution : la liste des sommets et son poids.
 * @see TravellingSalesman
 */
public class Solution 
{
	/*===== ATTRIBUTES =====*/
	/** Poids total de la solution **/
	private int weight;
	/** Ordre des sommets dans la solution **/
	private ArrayList<Vertex> solution = new ArrayList<Vertex>();
	/** Le graphe utilisé par la solution **/
	private Graph graph;
	
	/*===== BUILDER =====*/
	/**
	 * Construit la solution et calcule automatiquement son poids.
	 * @param solution un tableau de sommet représentant le circuit.
	 * @param graph le graphe utilisé par la solution
	 */
	public Solution(ArrayList<Vertex> solution, Graph graph) {
		this.solution = solution;
		this.graph = graph;
		calculateWeight();
	}
	
	/*===== METHODS =====*/
	/**
	 * Permet de calculer le poids de la solution à partir de son ArrayList de sommets.
	 */
	public void calculateWeight() {
		weight = 0;
		Edge edge = null;
		Vertex vertex1 = null;
		Vertex vertex2 = null;
		
		for (int i = 0; i<solution.size()-1; i++) {
			vertex1 = solution.get(i);
			vertex2 = solution.get(i+1);
			Object[] edges = graph.getEdgesBetween(vertex1, vertex2);
			edge = (Edge) edges[0];
			weight += edge.getIntValue();
		}
		
		vertex1 = solution.get(0);
		Object[] edges = graph.getEdgesBetween(vertex2, vertex1);
		edge = (Edge) edges[0];
		weight += edge.getIntValue();
	}
	
	/**
	 * Permet de copier une solution dans une autre
	 * @return Solution : une copie de la solution 
	 */
	public Solution copy() {
		@SuppressWarnings("unchecked")
		Solution copy = new Solution((ArrayList<Vertex>)solution.clone(), graph);
		copy.setWeight(weight);
		return copy;
	}
	
	/*===== GETTERS AND SETTERS =====*/
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public ArrayList<Vertex> getSolution() {
		return solution;
	}
}
