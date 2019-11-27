package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class BellmanFord extends AbstractShortestPath {

	private static final String INFINITE = "99999";	// Valeur utilisée pour simuler l'infini
	private HashMap<mxCell, mxCell> predecessors;	// Predecesseurs des sommets pour afficher le chemin à la fin
	
	public BellmanFord(mxGraph graph) 
	{
		super(graph);
	}
	
	public void Initialization(mxCell startVertex)
	{
		System.out.println("le sommet de départ c'est " + (int)startVertex.getValue());																			
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());								// Vertices contient tous les sommets du graphe
		potentials.put(startVertex, "0");
		for (Object o : vertices) 																			//On remplit le tableau des distances 
		{
			mxCell vertex = (mxCell) o;
			if (!(vertex.equals(startVertex)))
			{
				potentials.put(vertex, INFINITE);
				System.out.println("cell c'est " + (int)vertex.getValue());
				predecessors.put(vertex, vertex);	
			}
		}
	}
	
	public void updateDistances(mxCell vertex1, mxCell vertex2)
	{
		try 
		{
			System.out.println("Le potentiel de " + vertex2 + " c'est " + getPotential(vertex2));
			System.out.println("Le potentiel de " + vertex1 + " c'est " + getPotential(vertex1));
			System.out.println("Le nouveau potentiel de " + vertex2 + " ce serait " + getPotential(vertex1)+getDistanceBetween(vertex1, vertex2));
			if (getPotential(vertex2) == Integer.parseInt(INFINITE) && getPotential(vertex1) != Integer.parseInt(INFINITE))
			{
				System.out.println("Les potentiels avant l'update : " + potentials);
				potentials.put(vertex2, Integer.toString(getPotential(vertex1)+getDistanceBetween(vertex1, vertex2)));
				System.out.println("Les potentiels après l'update : " + potentials);
				predecessors.put(vertex2, vertex1);
			}
			else if (getPotential(vertex2) > getPotential(vertex1)+getDistanceBetween(vertex1, vertex2))
			{
				System.out.println("Les potentiels avant l'update : " + potentials);
				potentials.put(vertex2, Integer.toString(getPotential(vertex1)+getDistanceBetween(vertex1, vertex2)));
				System.out.println("Les potentiels après l'update : " + potentials);
				predecessors.put(vertex2, vertex1);
			}
		}
		catch(Exception e) { System.out.println(e.getMessage());}
	}
	
	public void findShortestPath()
	{
		predecessors = new HashMap<mxCell, mxCell>();
		
		mxCell startCell = getBeginning();
		mxCell endCell = getEnd();
		
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");
		
		currentStep++;
		
		Initialization(startCell);
		
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		int nbVertices = vertices.length;
		
		for (int k = 1; k < nbVertices; k++)
		{
			for (Object o : edges)
			{
				mxCell edge = (mxCell) o;
				updateDistances((mxCell)edge.getSource(), (mxCell)edge.getTarget());
			}
			steps.add(copy(graph));
		}
		
		graph.getModel().setStyle(endCell, "BOLD_END");
		steps.add(copy(graph));
		
		for (Object o : edges)																// On regarde s'il y a un cycle négatif
		{
			mxCell edge = (mxCell) o;
			try 
			{
				if ((getPotential((mxCell)edge.getSource())) + getDistanceBetween((mxCell)edge.getSource(), (mxCell)edge.getTarget()) < getPotential((mxCell)edge.getTarget()))
				{
					System.out.println("OHLALA Y'A UN CYCLE NEGATIF IL VA FALLOIR CREER UNE EXCEPTION ICI");
				}
			}
			catch(Exception e) { System.out.println(e.getMessage());}
		}
		
		mxCell vertex = endCell;
		mxCell edge = new mxCell();
		while (!(vertex.equals(startCell)))
		{
			for (Object ob : graph.getEdgesBetween(predecessors.get(vertex), vertex))
			{
				edge = (mxCell) ob;
			}
			graph.getModel().setStyle(edge, "BOLD_EDGE");
			vertex = predecessors.get(vertex);
			graph.getModel().setStyle(vertex, "BOLD_VERTEX");
			steps.add(copy(graph));
		}	
	}
}