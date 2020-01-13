package algorithms;

import java.util.HashMap;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe est la classe permettant de gérer l'affichage et la résolution de l'algorithme de Bellman-Ford 
 */
public class BellmanFord extends AbstractAlgorithm {

	/*===== ATTRIBUTES =====*/
	/** HashMap permettant de récupérer le chemin du sommet de départ au sommet d'arrivée **/
	private HashMap<Vertex, Vertex> predecessors;

	/*===== BUILDER =====*/
	public BellmanFord(Graph graph) 
	{
		super(graph);
	}

	/*===== METHODS =====*/
	
	/**
	 * Permet de mettre tous les potentiels à + l'infini, sauf le sommet de départ pour lequel son potentiel est mis à 0
	 * @param startVertex : le sommet de départ
	 */
	public void initialization(Vertex startVertex)
	{																		
		startVertex.setPotential(0);

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());			// "vertices" contient tous les sommets du graphe	
		for (Object o : vertices) 														// On remplit le tableau des distances 
		{
			Vertex vertex = (Vertex) o;
			if (!(vertex.equals(startVertex)))
			{
				vertex.setPotential(INFINITE);
				predecessors.put(vertex, vertex);	
			}
		}
	}

	/**
	 * Permet de mettre à jour les distances d'une itération de l'algorithme à l'autre
	 * @param vertex1 : sommet de départ de l'arc
	 * @param vertex2 : sommet d'arrivée de l'arc
	 */
	public void updateDistances(Vertex vertex1, Vertex vertex2)
	{
		try 
		{
			if (vertex2.getPotential() == INFINITE && vertex1.getPotential() != INFINITE)
			{
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				predecessors.put(vertex2, vertex1);
			}
			else if (vertex2.getPotential() > vertex1.getPotential() + getDistanceBetween(vertex1, vertex2))
			{
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				predecessors.put(vertex2, vertex1);
			}
		}
		catch(Exception e) { System.out.println(e.getMessage());}
	}

	/**
	 * Fonction qui execute l'algorithme de Bellman-Ford. Elle fait appel aux fonctions de la classe, et est appelée dans le constructeur
	 */
	@Override
	public void executeAlgorithm()
	{
		predecessors = new HashMap<Vertex, Vertex>();

		Vertex startVertex = getStartVertex();
		Vertex endVertex = getEndVertex();

		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(new Step(copy(graph)));

		initialization(startVertex);

		steps.add(new Step(copy(graph)));

		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		int nbVertices = vertices.length;

		for (int k = 1; k < nbVertices; k++)
		{
			for (Object o : edges)
			{
				Edge edge = (Edge) o;
				updateDistances((Vertex)edge.getSource(), (Vertex)edge.getTarget());
				if(k==1) {
					steps.add(new Step(copy(graph)));
				}
			}
			steps.add(new Step(copy(graph)));

		}

		graph.getModel().setStyle(endVertex, "BOLD_END");
		steps.add(new Step(copy(graph)));

		for (Object o : edges)																// On regarde s'il y a un cycle négatif
		{
			Edge edge = (Edge) o;
			try 
			{
				// Détection d'un cycle négatif
				if ((((Vertex)edge.getSource())).getPotential() + getDistanceBetween((Vertex)edge.getSource(), (Vertex)edge.getTarget()) < ((Vertex)edge.getTarget()).getPotential())
				{
					steps.add(new Step(copy(graph), "Erreur : détection d'un cycle négatif"));
					MainWindowController.interruptAlgorithm("Presence d'un cycle négatif. Impossible de calculer le plus court chemin.");
					return ;
				}
			}
			catch(Exception e) { System.out.println(e.getMessage());}
		}

		Vertex vertex = endVertex;
		Edge edge = null;
		while (!(vertex.equals(startVertex)))
		{
			for (Object o : graph.getEdgesBetween(predecessors.get(vertex), vertex))
			{
				edge = (Edge)o;
			}

			vertex = predecessors.get(vertex);
			
			if(edge == null) {
				int start = MainWindowController.getStart();
				int end = MainWindowController.getEnd();
				steps.add(new Step(copy(graph), "Erreur : il n'existe pas de chemin entre " + start + " et " + end));
				MainWindowController.interruptAlgorithm("Il n'existe pas de plus court chemin entre " + start + " et " + end);
				return ;
			}
			
			graph.getModel().setStyle(edge, "BOLD_EDGE");
			
			if(vertex.equals(startVertex)) {
				graph.getModel().setStyle(vertex, "BOLD_START");
			}
			else {
				graph.getModel().setStyle(vertex, "BOLD_VERTEX");
			}
			
			steps.add(new Step(copy(graph)));
			
			
		}	
	}
}