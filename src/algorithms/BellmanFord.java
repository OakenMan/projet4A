package algorithms;

import java.util.HashMap;

import model.Edge;
import model.Graph;
import model.Vertex;

public class BellmanFord extends AbstractShortestPath {

	private HashMap<Vertex, Vertex> predecessors;	// Predecesseurs des sommets pour afficher le chemin � la fin

	public BellmanFord(Graph graph) 
	{
		super(graph);
	}

	public void Initialization(Vertex startVertex)
	{
		//		System.out.println("le sommet de départ c'est " + startVertex.getIntValue());																			

		startVertex.setPotential(0);

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());			// Vertices contient tous les sommets du graphe	
		for (Object o : vertices) 														//On remplit le tableau des distances 
		{
			Vertex vertex = (Vertex) o;
			if (!(vertex.equals(startVertex)))
			{
				vertex.setPotential(INFINITE);
				//				System.out.println("cell c'est " + (int)vertex.getValue());
				predecessors.put(vertex, vertex);	
			}
		}
	}

	public void updateDistances(Vertex vertex1, Vertex vertex2)
	{
		try 
		{
			//			System.out.println("Le potentiel de " + vertex2 + " c'est " + vertex2.getPotential());
			//			System.out.println("Le potentiel de " + vertex1 + " c'est " + vertex1.getPotential());
			//			System.out.println("Le nouveau potentiel de " + vertex2 + " ce serait " + vertex1.getPotential()+getDistanceBetween(vertex1, vertex2));
			if (vertex2.getPotential() == INFINITE && vertex1.getPotential() != INFINITE)
			{
				//				System.out.println("Les potentiels avant l'update : " + potentials);
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				//				System.out.println("Les potentiels apr�s l'update : " + potentials);
				predecessors.put(vertex2, vertex1);
			}
			else if (vertex2.getPotential() > vertex1.getPotential() + getDistanceBetween(vertex1, vertex2))
			{
				//				System.out.println("Les potentiels avant l'update : " + potentials);
				vertex2.setPotential(vertex1.getPotential() + getDistanceBetween(vertex1, vertex2));
				//				System.out.println("Les potentiels apr�s l'update : " + potentials);
				predecessors.put(vertex2, vertex1);
			}
		}
		catch(Exception e) { System.out.println(e.getMessage());}
	}

	@Override
	public void findShortestPath()
	{
		predecessors = new HashMap<Vertex, Vertex>();

		Vertex startVertex = getBeginning();
		Vertex endVertex = getEnd();

		// On ajoute l'�tape 0 (juste le d�part en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");

		currentStep++;

		Initialization(startVertex);

		steps.add(copy(graph));

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
					steps.add(copy(graph));
				}
			}
			steps.add(copy(graph));

		}

		graph.getModel().setStyle(endVertex, "BOLD_END");
		steps.add(copy(graph));

		for (Object o : edges)																// On regarde s'il y a un cycle n�gatif
		{
			Edge edge = (Edge) o;
			try 
			{
				if ((((Vertex)edge.getSource())).getPotential() + getDistanceBetween((Vertex)edge.getSource(), (Vertex)edge.getTarget()) < ((Vertex)edge.getTarget()).getPotential())
				{
					System.out.println("OHLALA Y'A UN CYCLE NEGATIF IL VA FALLOIR CREER UNE EXCEPTION ICI");
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
			
			graph.getModel().setStyle(edge, "BOLD_EDGE");
			
			if(vertex.equals(startVertex)) {
				graph.getModel().setStyle(vertex, "BOLD_START");
			}
			else {
				graph.getModel().setStyle(vertex, "BOLD_VERTEX");
			}
			
			steps.add(copy(graph));
			
			
		}	
	}
}