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

	/*===== ATTRIBUTES =====*/
	protected Graph graph;
	protected ArrayList<Graph> steps;
	protected int nbSteps;
	protected int currentStep;
	protected HashMap<Vertex, String> potentials;
	
	/*===== BUILDER =====*/
	public AbstractShortestPath(Graph graph) {
		steps = new ArrayList<Graph>();
		potentials = new HashMap<Vertex, String>();
		this.graph = graph;
		nbSteps = 0;
		currentStep = 0;
		findShortestPath();
	}
	
	/*===== METHODS =====*/
	@Override
	public abstract void findShortestPath();
	
	public void displayPotentials() {
		JPanel potPane = MainWindowController.getView().getGraphPanel().getPotentialsPane();
		
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		
		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;
			
			JLabel potential = new JLabel(String.valueOf(vertex.getPotential()));
			
			int x = (int)vertex.getGeometry().getX();
			int y = (int)vertex.getGeometry().getY()-30;
			
			potential.setBounds(new Rectangle(x, y, 50, 50));
			potential.setBackground(Color.WHITE);
			potential.setForeground(Color.RED);
			potPane.add(potential);
			
			potPane.validate();
			
		}
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
			// On l'ajoute à la copie
			copy.insertVertex(parent, 
					null, 
					vertex.getValue(), 
					graph.getCellGeometry(vertex).getX(), 
					graph.getCellGeometry(vertex).getY(), 
					graph.getCellGeometry(vertex).getWidth(), 
					graph.getCellGeometry(vertex).getHeight(), 
					vertex.getStyle());
			System.out.println("add 1 vertex");
		}
		
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
			System.out.println("add 1 vertex");
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
		if(currentStep < nbSteps) {
			currentStep++;
			return steps.get(currentStep);
		}
		else {
			throw new Exception("Can't go to next step");
		}
	}
	
	public int getNbSteps() {
		return nbSteps;
	}
	
	public int getCurrentStep() {
		return currentStep;
	}
	
	/**
	 * [TEMP] méthode de debug pour afficher un graphe dans la console
	 * @param graph
	 */
	public void displayGraph(Graph graph) {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for(Object c : vertices) {
			Vertex cell = (Vertex) c;
			System.out.println("Vertex " + cell.getValue() + " : " + cell.getStyle());
		}
	}
}


