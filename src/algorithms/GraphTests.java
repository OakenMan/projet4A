package algorithms;

import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe contient des méthodes permettant d'effectuer des tests sur des graphes,
 * afin de connaître certaines de leurs propriétés.
 */
public class GraphTests {

	/**
	 * Méthode utilisée pour savoir si un graphe contient des valeurs négatives
	 * @param graph le graphe à analyser
	 * @return true si graph contient des longueurs négatives, false sinon
	 */
	public static boolean containsNegativeValues(Graph graph) {
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());

		for(Object o : edges) {
			Edge edge = (Edge) o;
			if(edge.getIntValue() < 0) {
				return true;
			}
		}
		
		return false;	
	}
	
	/**
	 * Méthode utilisée pour savoir si un graphe est complet ou non
	 * @param graph le graphe à analyser
	 * @return true si graph est complet, false sinon
	 */
	public static boolean isGraphComplete(Graph graph) {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		int nbVertices = vertices.length;
		
		boolean isComplete = true;
		
		for(Object o : vertices) {
			Vertex vertex = (Vertex) o;
			Object[] edges = graph.getOutgoingEdges(vertex);
			if(edges.length != nbVertices-1) {
				isComplete = false;
			}
		}
		
		return isComplete;
	}
}
