package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe est la classe permettant de gérer l'affichage et la résolution de l'algorithme de Dijkstra
 */
public class Dijkstra extends AbstractAlgorithm {

	/*===== ATTRIBUTES =====*/
	/** ArrayList de sommets correspondant à la liste des sommets présents dans le sous graphe **/
	private ArrayList<Vertex> subGraph; 			
	
	/** HashMap permettant de récupérer le chemin du sommet de départ au sommet d'arrivée **/ 
	private HashMap<Vertex, Vertex> predecessors;	

	/*===== BUILDER =====*/
	public Dijkstra(Graph graph) {
		super(graph);
	}

	/*===== METHODS =====*/
	
	/**
	 * Permet de mettre tous les potentiels à + l'infini, sauf le sommet de départ pour lequel son potentiel est mis à 0
	 * De plus, on ajoute le sommet de départ au sous graphe et on initialise la liste des prédécesseurs
	 * @param startVertex : le sommet de départ
	 */
	public void initialization(Vertex startVertex)
	{
		// On ajoute le sommet de départ au sous-graphe et on met à jour son potentiel
		subGraph.add(startVertex);	
		startVertex.setPotential(0);

		// Pour chaque sommet (sauf le départ), on met à jour son potentiel et la liste des prédécesseurs
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());	
		for (Object o : vertices) 												
		{
			Vertex vertex = (Vertex) o;
			if (!(vertex.equals(startVertex)))
			{
				vertex.setPotential(INFINITE);
				predecessors.put(vertex, vertex);	
			}
		}
		
		// On crée la liste de voisin du sommet de départ
		ArrayList<Vertex> neighboors = new ArrayList<Vertex>();		
		for (Object o : graph.getOutgoingEdges(startVertex))
		{
			Edge edge = (Edge) o;
			Vertex neighboor = (Vertex)(edge.getTarget());
			neighboors.add(neighboor);
		}
		// On met à jour la liste des potentiels
		for (Vertex v : neighboors)									
		{
			updateDistances(startVertex, v);
		}
	}

	/**
	 * Permet de renvoyer le sommet au potentiel minimum qui n'est pas encore présent dans le sous graphe
	 * @return Vertex : sommet au potentiel minimum
	 */
	public Vertex getMin()
	{
		int min = INFINITE;
		Vertex minCell = null;
		
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent()); 
		for (Object o : vertices)
		{
			Vertex cell = (Vertex) o;
			if (!(subGraph.contains(cell)))		// Si le sommet n'est pas dans le sous graphe alors
			{
				try 
				{
					if (cell.getPotential() < min)
					{
						min = cell.getPotential();
						minCell = cell;				
					}
				}
				catch(Exception e) {System.out.println(e.getMessage());}
			}
		}
		return minCell;
	}

	/**
	 * Permet de mettre à jour les distances d'une itération de l'algorithme à l'autre
	 * @param vertex1 : sommet actuel
	 * @param vertex2 : sommet sommet voisin
	 */
	public void updateDistances(Vertex vertex1, Vertex vertex2)
	{
		try 
		{
			if (vertex2.getPotential() == INFINITE)
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
	 * Fonction qui execute l'algorithme de Dijkstra. Elle fait appel aux fonctions de la classe, et est appelée dans le constructeur
	 */
	@Override
	public void executeAlgorithm() 
	{
		subGraph = new ArrayList<Vertex>();
		predecessors = new HashMap<Vertex, Vertex>();

		Vertex startCell = getStartVertex();
		Vertex endCell = getEndVertex();

		initialization(startCell);
		
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(new Step(copy(graph)));

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		ArrayList<Vertex> leGraph = new ArrayList<Vertex>();
		for (Object o : vertices)
		{
			Vertex cell = (Vertex) o;
			leGraph.add(cell);
		}

		Vertex cell = new Vertex();
		boolean the_end = false;

		while (!(subGraph.equals(leGraph)) && the_end == false)
		{
			cell = getMin();
			
			if(cell == null) {
				int start = MainWindowController.getStart();
				int end = MainWindowController.getEnd();
				steps.add(new Step(copy(graph), "Erreur : il n'existe pas de chemin entre " + start + " et " + end));
				MainWindowController.interruptAlgorithm("Il n'existe pas de plus court chemin entre " + start + " et " + end);
				return ;
			}
			
			graph.getModel().setStyle(cell, "BOLD_VERTEX");
			if (!(cell.equals(startCell)))
			{
				subGraph.add(cell);
			}

			if (cell.equals(endCell))										// Si on a atteint la fin on arrête
			{
				the_end = true;
				graph.getModel().setStyle(cell, "BOLD_END");
			}
			else
			{
				ArrayList<Vertex> neighboors = new ArrayList<Vertex>();		// On crée la liste de voisin du sommet actuel
				for (Object o : graph.getOutgoingEdges(cell))
				{
					Edge vertex = (Edge) o;
					Vertex neighboor = new Vertex();
					neighboor = (Vertex)(vertex.getTarget());
					neighboors.add(neighboor);
				}
				for (Vertex c : neighboors)									// On met à jour la liste des potentiels
				{
					updateDistances(cell, c);
				}
			}
			steps.add(new Step(copy(graph)));	
			if (!(cell.equals(endCell)))									// Après avoir exploré le sommet on le remet normal
			{
				graph.getModel().setStyle(cell, "DEFAULT_VERTEX");
			}
		}
		if (the_end == true)
		{
			while (!(cell.equals(startCell)))
			{
				Edge edge = null;
				for (Object o : graph.getEdgesBetween(predecessors.get(cell), cell))
				{
					edge = (Edge) o;
				}
				graph.getModel().setStyle(edge, "BOLD_EDGE");
				cell = predecessors.get(cell);
				if(cell.equals(startCell)) {
					graph.getModel().setStyle(cell, "BOLD_START");
				}
				else {
					graph.getModel().setStyle(cell, "BOLD_VERTEX");
				}
				steps.add(new Step(copy(graph)));
			}
		}
	}



}
