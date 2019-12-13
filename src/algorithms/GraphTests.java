package algorithms;

import model.Edge;
import model.Graph;

public class GraphTests {

	/**
	 * Methode utile pour savoir si un graphe contient des valeurs négatives
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
}
