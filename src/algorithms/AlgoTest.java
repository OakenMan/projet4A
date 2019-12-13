package algorithms;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * OBSOLETE, à supprimer avant la release finale
 * @author tom
 */
public class AlgoTest extends AbstractShortestPath {

	/*===== BUILDER =====*/
	public AlgoTest(Graph graph) {
		super(graph);	
	}

	/*===== METHODS =====*/
	@Override
	public void findShortestPath() {

		Vertex startVertex = null;
		Vertex endVertex = null;
		Vertex currentVertex;
		
		// On récupère le sommet de départ et d'arrivée
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		for (Object c : vertices)
		{
			Vertex vertex = (Vertex) c;
			int val = vertex.getIntValue();
			
			if(val == MainWindowController.getStart()) {
				startVertex = vertex;
				graph.getModel().setStyle(startVertex, "BOLD_START");
				System.out.println("Départ = "+startVertex.getIntValue());
			}
			else if(val == MainWindowController.getEnd()) {
				endVertex = vertex;
				System.out.println("Arrivée = "+endVertex.getIntValue());
			}
			
		}
		
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");
		
		currentStep++;
		
		currentVertex = startVertex;
		
		// Tant qu'on a pas atteind l'arrivée
		while(!currentVertex.equals(endVertex)) {
			
			Object[] edges = graph.getOutgoingEdges(currentVertex);			// On récupère la liste des arcs partant de currentCell
			
			Edge minPath = getShortestEdge(edges);							// On récupère l'arc au plus petit poids
			
			graph.getModel().setStyle(minPath, "BOLD_EDGE");				// On change son style
			
			currentVertex = (Vertex)minPath.getTarget();					// On récupère le sommet au bout de cet arc
			
			if(!currentVertex.equals(endVertex)) {							// Si ce sommet n'est pas l'arrivée
//				potentials.put(currentVertex, minPath.getValue().toString());
				currentVertex.setPotential(minPath.getIntValue());
				graph.getModel().setStyle(currentVertex, "BOLD_VERTEX");	// On change son style												// On augmente le nombre d'étapes
				currentStep++;												// et l'étape courante
			}
			else {															// Sinon (si c'est l'arrivée)
				currentVertex.setPotential(minPath.getIntValue());
				graph.getModel().setStyle(currentVertex, "BOLD_END");		// On change son style
			}
			
			steps.add(copy(graph));											// Enfin on ajoute cette nouvelle étape au tableau
		}
	}
	
	public static Edge getShortestEdge(Object[] edges) {
		Edge minEdge = (Edge)edges[0];
		for(Object c : edges) {
			Edge edge = (Edge) c;
			if(edge.getIntValue() < minEdge.getIntValue()){
				minEdge = edge;
			}
		}
		return minEdge;
	}

}
