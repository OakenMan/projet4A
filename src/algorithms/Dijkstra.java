package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

public class Dijkstra extends AbstractShortestPath {

	private static final String INFINITE = "99999";	// Valeur utilisée pour simuler l'infini
	private ArrayList<mxCell> subGraph; 			//Liste des sommets présents dans le sous graphe
	private HashMap<mxCell, mxCell> predecessors;	// Predecesseurs des sommets pour afficher le chemin à la fin
	
	public Dijkstra(mxGraph graph) 
	{
		super(graph);
	}

	public void Initialization(mxCell startCell)
	{
		System.out.println("le sommet de départ c'est " + (int)startCell.getValue());
		subGraph.add(startCell);																			
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());								// Vertices contient tous les sommets du graphe
		potentials.put(startCell, "0");
		for (Object o : vertices) 																			//On remplit le tableau des distances 
		{
			mxCell cell = (mxCell) o;
			if (!(cell.equals(startCell)))
			{
				potentials.put(cell, INFINITE);
				System.out.println("cell c'est " + (int)cell.getValue());
				predecessors.put(cell, cell);	
			}
		}
		
		ArrayList<mxCell> neighboors = new ArrayList<mxCell>();		// On crée la liste de voisin du sommet de départ
		for (Object o : graph.getOutgoingEdges((Object) startCell))
		{
			mxCell neighboor = new mxCell();
			neighboor = (mxCell)(((mxCell) o).getTarget());
			neighboors.add(neighboor);
		}
		for (mxCell c : neighboors)									// On met à jour la liste des potentiels
		{
			System.out.println("Je suis là !");
			updateDistances(startCell, c);
		}
		System.out.println("Les potentiels à la fin de l'initialisation : " + potentials);
	}
	
	public mxCell getMin()
	{
		int min = 99999;
		mxCell minCell = null;
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent()); 
			
		for (Object o : vertices)
		{
			System.out.println("La minCell c'est " + minCell);
			mxCell cell = (mxCell) o;
			if (!(subGraph.contains(cell)))						// Si le sommet n'est pas dans le sous graphe alors
			{
				try 
				{
					if (getPotential(cell) < min)
					{
						min = getPotential(cell);
						minCell = cell;				
					}
				}
				catch(Exception e) {System.out.println(e.getMessage());}
			}
		}
		return minCell;
	}
	
	public void updateDistances(mxCell vertex1, mxCell vertex2)
	{
		try 
		{
			System.out.println("Le potentiel de " + vertex2 + " c'est " + getPotential(vertex2));
			System.out.println("Le potentiel de " + vertex1 + " c'est " + getPotential(vertex1));
			System.out.println("Le nouveau potentiel de " + vertex2 + " ce serait " + getPotential(vertex1)+getDistanceBetween(vertex1, vertex2));
			if (getPotential(vertex2) == Integer.parseInt(INFINITE))
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
	
	@Override
	public void findShortestPath() 
	{
		subGraph = new ArrayList<mxCell>();
		predecessors = new HashMap<mxCell, mxCell>();
		
		mxCell startCell = getBeginning();
		mxCell endCell = getEnd();
		
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");
		
		currentStep++;
		
		Initialization(startCell);
		
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		ArrayList<mxCell> leGraph = new ArrayList<mxCell>();
		for (Object o : vertices)
		{
			mxCell cell = (mxCell) o;
			leGraph.add(cell);
		}
		
		mxCell cell = new mxCell();
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
			
			if (cell.equals(endCell))										// Si on a atteint la fin on arrête
			{
				the_end = true;
				graph.getModel().setStyle(cell, "BOLD_END");
			}
			else
			{
				ArrayList<mxCell> neighboors = new ArrayList<mxCell>();		// On crée la liste de voisin du sommet actuel
				for (Object o : graph.getOutgoingEdges((Object) cell))
				{
					mxCell neighboor = new mxCell();
					neighboor = (mxCell)(((mxCell) o).getTarget());
					neighboors.add(neighboor);
				}
				for (mxCell c : neighboors)									// On met à jour la liste des potentiels
				{
					updateDistances(cell, c);
				}
			}
			steps.add(copy(graph));	
			if (!(cell.equals(endCell)))									// Après avoir exploré le sommet on le remet normal
			{
				graph.getModel().setStyle(cell, "DEFAULT_VERTEX");
			}
		}
		if (the_end == true)
		{
			mxCell edge = new mxCell();
			while (!(cell.equals(startCell)))
			{
				for (Object o : graph.getEdgesBetween(predecessors.get(cell), cell))
				{
					edge = (mxCell) o;
				}
				graph.getModel().setStyle(edge, "BOLD_EDGE");
				cell = predecessors.get(cell);
				graph.getModel().setStyle(cell, "BOLD_VERTEX");
				steps.add(copy(graph));
			}
		}
	}



}
