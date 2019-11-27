package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Dijkstra extends AbstractShortestPath {

	final int INFINITE = 9999999;				// Valeur utilis�e pour simuler l'infini
	
	private ArrayList<Vertex> subGraph; 			//Liste des sommets pr�sents dans le sous graphe
	private HashMap<Vertex, Vertex> predecessors;	// Predecesseurs des sommets pour afficher le chemin � la fin
	
	public Dijkstra(Graph graph) {
		super(graph);
	}

	public void Initialization(Vertex startVertex)
	{
		System.out.println("le sommet de départ c'est " + (int)startVertex.getValue());
		subGraph.add(startVertex);	
		startVertex.setPotential(0);
		
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());								// Vertices contient tous les sommets du graphe
		for (Object o : vertices) 																			//On remplit le tableau des distances 
		{
			Vertex vertex = (Vertex) o;
			if (!(vertex.equals(startVertex)))
			{
				vertex.setPotential(INFINITE);
				
				System.out.println("cell c'est " + vertex.getIntValue());
				predecessors.put(vertex, vertex);	
			}
		}

		ArrayList<Vertex> neighboors = new ArrayList<Vertex>();		// On cr�e la liste de voisin du sommet de d�part
		for (Object o : graph.getOutgoingEdges(startVertex))
		{
			Vertex vertex = (Vertex) o;
			Vertex neighboor = new Vertex();
			neighboor = (Vertex)(vertex.getTarget());
			neighboors.add(neighboor);
		}
		for (Vertex c : neighboors)									// On met � jour la liste des potentiels
		{
			System.out.println("Je suis l� !");
			updateDistances(startVertex, c);
		}
		System.out.println("Les potentiels � la fin de l'initialisation : " + potentials);
	}

	public Vertex getMin()
	{
		int min = INFINITE;
		Vertex minCell = null;
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent()); 

		for (Object o : vertices)
		{
			System.out.println("La minCell c'est " + minCell);
			Vertex cell = (Vertex) o;
			if (!(subGraph.contains(cell)))						// Si le sommet n'est pas dans le sous graphe alors
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
			System.out.println("Le potentiel de " + vertex2 + " c'est " + vertex2.getPotential());
			System.out.println("Le potentiel de " + vertex1 + " c'est " + vertex1.getPotential());
			System.out.println("Le nouveau potentiel de " + vertex2 + " ce serait " + vertex1.getPotential()+getDistanceBetween(vertex1, vertex2));
			if (vertex2.getPotential() == INFINITE)
			{
				System.out.println("Les potentiels avant l'update : " + potentials);
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				System.out.println("Les potentiels apr�s l'update : " + potentials);
				predecessors.put(vertex2, vertex1);
			}
			else if (vertex2.getPotential() > vertex1.getPotential() + getDistanceBetween(vertex1, vertex2))
			{
				System.out.println("Les potentiels avant l'update : " + potentials);
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				System.out.println("Les potentiels apr�s l'update : " + potentials);
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
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");

		currentStep++;

		Initialization(startCell);

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		ArrayList<Vertex> leGraph = new ArrayList<Vertex>();
		for (Object o : vertices)
		{
			Vertex cell = (Vertex) o;
			leGraph.add(cell);
		}

		Vertex cell = new Vertex();
		boolean the_end = false;

		System.out.println(subGraph + " et " + leGraph);
		while (!(subGraph.equals(leGraph)) && the_end == false)
		{
			cell = getMin();
			System.out.println("Mon minimum c'est " + cell);
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
				for (Object o : graph.getOutgoingEdges((Object) cell))
				{
					Vertex vertex = (Vertex) o;
					Vertex neighboor = new Vertex();
					neighboor = (Vertex)(vertex.getTarget());
					neighboors.add(neighboor);
				}
				for (Vertex c : neighboors)									// On met � jour la liste des potentiels
				{
					updateDistances(cell, c);
				}
			}
			steps.add(copy(graph));	
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
				graph.getModel().setStyle(cell, "BOLD_VERTEX");
				steps.add(copy(graph));
			}
		}
	}



}
