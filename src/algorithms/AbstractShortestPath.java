package algorithms;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.StyleSheet;

public abstract class AbstractShortestPath implements ShortestPath {

	final int INFINITE = 9999999;
	
	/*===== ATTRIBUTES =====*/
	protected Graph graph;
	protected ArrayList<Graph> steps;
	protected int nbSteps;
	protected int currentStep;

	/*===== BUILDER =====*/
	public AbstractShortestPath(Graph graph) {
		steps = new ArrayList<Graph>();
		this.graph = graph;
		currentStep = 0;
		findShortestPath();
	}

	/*===== METHODS =====*/
	@Override
	public abstract void findShortestPath();

	public void displayPotentials(Graph graph) {
		System.out.println("potentials =");
		JPanel potPane = MainWindowController.getView().getGraphPanel().getPotentialsPane();
		//		JViewport potPane = MainWindowController.getView().getGraphPanel().getGraphPane();

		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		potPane.removeAll();

		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;

			int pot = vertex.getPotential();
			String potStr = "";
			
			if(pot == INFINITE) {
				potStr = "\u221e";
			}
			else if(pot > 0) {
				potStr = String.valueOf(pot);
			}
				JLabel potential = new JLabel(potStr);
				System.out.print(vertex.getPotential() + " ");

				int x = (int)vertex.getGeometry().getX();
				int y = (int)vertex.getGeometry().getY()-30;

				potential.setBounds(new Rectangle(x, y, 50, 50));
				potential.setBackground(Color.WHITE);
				potential.setForeground(Color.RED);
				potPane.add(potential);
				potPane.validate();
		}
	}

	public Vertex getBeginning()
	{
		Vertex startVertex = null;

		// On récupère le sommet de départ
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : vertices)
		{
			Vertex vertex = (Vertex) c;

			if(vertex.getIntValue() == MainWindowController.getStart()) 
			{
				startVertex = vertex;
				graph.getModel().setStyle(startVertex, "BOLD_START");
				System.out.println("Départ = "+startVertex.getIntValue());
			}
		}
		return startVertex;
	}

	public Vertex getEnd()
	{
		Vertex endVertex = null;

		// On récupère le sommet d'arrivée
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : vertices)
		{
			Vertex vertex = (Vertex) c;

			if(vertex.getIntValue() == MainWindowController.getEnd()) 
			{
				endVertex = vertex;
				System.out.println("Arrivée = "+endVertex.getIntValue());
			}
		}
		return endVertex;
	}

	/**
	 * Renvoie une copie du graphe passé en paramètre
	 * @param graph le graphe à copier
	 * @return une copie du graphe
	 */
	public Graph copy(Graph graph) {

		// Créé un nouveau graphe
		Graph copy = new Graph();
		copy.setStylesheet(new StyleSheet());

		Object parent = copy.getDefaultParent();

		// On récupère la liste des sommets du graphe à copier
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		// Pour chacun de ses sommets...
		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;

			Vertex vertexCopy = (Vertex) copy.createVertex(parent, 
					null, 
					vertex.getValue(), 
					graph.getCellGeometry(vertex).getX(), 
					graph.getCellGeometry(vertex).getY(), 
					graph.getCellGeometry(vertex).getWidth(), 
					graph.getCellGeometry(vertex).getHeight(),
					vertex.getStyle(), 
					false);

			vertexCopy.setPotential(vertex.getPotential());
			System.out.print(vertexCopy.getPotential() + " ");

			copy.addCell(vertexCopy, parent);
		}
		System.out.println("");

		// On récupère la liste des arcs du graphe à copier
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		// Pour chacun de ses arcs...
		for(Object c : edges) {
			Edge edge = (Edge) c;
			// On l'ajoute à la copie
			if(edge.getSource() != null && edge.getTarget() != null) {
				copy.insertEdge(parent, 
						null, 
						edge.getValue(), 
						getCellFromId(copy, edge.getSource().getId()), 
						getCellFromId(copy, edge.getTarget().getId()), 
						edge.getStyle());
			}
			//			System.out.println("add 1 vertex");
		}

		return copy;
	}

	/**
	 * Renvoie un sommet en fonction de son ID
	 * @param g le graphe où rechercher
	 * @param id l'idée du sommet à trouver
	 * @return le sommet lié à l'id, ou null si non trouvé
	 */
	public Vertex getCellFromId(Graph graph, String id) {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;
			if(vertex.getId().equals(id)) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Renvoie la première étape de l'algo
	 * @return
	 */
	public Graph getFirstStep() {
		currentStep = 0;
		return steps.get(currentStep);
	}

	/**
	 * Renvoie la dernière étape de l'algo
	 * @return
	 */
	public Graph getLastStep() {
		currentStep = steps.size()-1;
		return steps.get(currentStep);
	}

	/**
	 * Renvoie l'étape précédente de l'algo
	 * @return
	 * @throws Exception
	 */
	public Graph getPreviousStep() throws Exception {
		if(currentStep > 0) {
			currentStep--;
			return steps.get(currentStep);
		}
		else {
			throw new Exception("Can't go to previous step");
		}
	}

	/**
	 * Renvoie l'étape suivante de l'algo
	 * @return
	 * @throws Exception
	 */
	public Graph getNextStep() throws Exception {
		if(currentStep < steps.size()) {
			currentStep++;
			return steps.get(currentStep);
		}
		else {
			throw new Exception("Can't go to next step");
		}
	}

	public int getNbSteps() {
		return steps.size();
	}

	public int getCurrentStep() {
		return currentStep;
	}

	public int getDistanceBetween(Vertex vertex1, Vertex vertex2) throws Exception
	{
		Object[] edgesFromVertex1 = graph.getOutgoingEdges(vertex1);
		for (Object o : edgesFromVertex1)
		{
			Edge edge = (Edge) o;
			if (edge.getTarget().equals(vertex2))
			{
				return edge.getIntValue();
			}
		}
		throw new Exception ("Les deux sommets " + vertex1 + " et " + vertex2 + " ne sont pas reliés entre eux");
	}
}


