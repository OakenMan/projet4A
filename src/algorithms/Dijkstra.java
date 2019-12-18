package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;

public class Dijkstra extends AbstractShortestPath {

	private ArrayList<Vertex> subGraph; 			// Liste des sommets présents dans le sous graphe
	private HashMap<Vertex, Vertex> predecessors;	// Predecesseurs des sommets pour afficher le chemin à la fin

	public Dijkstra(Graph graph) {
		super(graph);
	}

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

	public void updateDistances(Vertex vertex1, Vertex vertex2)
	{
		try 
		{
			//			System.out.println("Le potentiel de " + vertex2 + " c'est " + vertex2.getPotential());
			//			System.out.println("Le potentiel de " + vertex1 + " c'est " + vertex1.getPotential());
			//			System.out.println("Le nouveau potentiel de " + vertex2 + " ce serait " + vertex1.getPotential()+getDistanceBetween(vertex1, vertex2));
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

	@Override
	public void findShortestPath() 
	{
		subGraph = new ArrayList<Vertex>();
		predecessors = new HashMap<Vertex, Vertex>();

		Vertex startCell = getBeginning();
		Vertex endCell = getEnd();

		// On ajoute l'�tape 0 (juste le d�part en gras)
		steps.add(new Step(copy(graph)));

		initialization(startCell);

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		ArrayList<Vertex> leGraph = new ArrayList<Vertex>();
		for (Object o : vertices)
		{
			Vertex cell = (Vertex) o;
			leGraph.add(cell);
		}

		Vertex cell = new Vertex();
		boolean the_end = false;

		//		System.out.println(subGraph + " et " + leGraph);
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

			if (cell.equals(endCell))										// Si on a atteint la fin on arr�te
			{
				the_end = true;
				graph.getModel().setStyle(cell, "BOLD_END");
			}
			else
			{
				ArrayList<Vertex> neighboors = new ArrayList<Vertex>();		// On cr�e la liste de voisin du sommet actuel
				for (Object o : graph.getOutgoingEdges(cell))
				{
					Edge vertex = (Edge) o;
					Vertex neighboor = new Vertex();
					neighboor = (Vertex)(vertex.getTarget());
					neighboors.add(neighboor);
				}
				for (Vertex c : neighboors)									// On met � jour la liste des potentiels
				{
					updateDistances(cell, c);
				}
			}
			steps.add(new Step(copy(graph)));	
			if (!(cell.equals(endCell)))									// Apr�s avoir explor� le sommet on le remet normal
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
