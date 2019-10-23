package algorithms;

import java.util.ArrayList;
import java.util.HashMap;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import util.StyleSheet;

public abstract class AbstractShortestPath implements ShortestPath {

	/*===== ATTRIBUTES =====*/
	protected mxGraph graph;
	protected ArrayList<mxGraph> steps;
	protected int nbSteps;
	protected int currentStep;
	protected HashMap<mxCell, String> potentials;
	
	/*===== BUILDER =====*/
	public AbstractShortestPath(mxGraph graph) {
		steps = new ArrayList<mxGraph>();
		potentials = new HashMap<mxCell, String>();
		this.graph = graph;
		nbSteps = 0;
		currentStep = 0;
		findShortestPath();
	}
	
	/*===== METHODS =====*/
	@Override
	public abstract void findShortestPath();
	
	public abstract void displayPotentials();
	
	/**
	 * Renvoie une copie du graphe passé en paramètre
	 * @param graph le graphe à copier
	 * @return une copie du graphe
	 */
	public mxGraph copy(mxGraph graph) {

		// Créé un nouveau graphe
		mxGraph copy = new mxGraph();
		copy.setStylesheet(new StyleSheet());
		
		Object parent = copy.getDefaultParent();
		
		// On récupère la liste des sommets du graphe à copier
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		// Pour chacun de ses sommets...
		for(Object c : vertices) {
			mxCell vertex = (mxCell) c;
			// On l'ajoute à la copie
			copy.insertVertex(parent, 
					null, 
					vertex.getValue(), 
					graph.getCellGeometry(vertex).getX(), 
					graph.getCellGeometry(vertex).getY(), 
					graph.getCellGeometry(vertex).getWidth(), 
					graph.getCellGeometry(vertex).getHeight(), 
					vertex.getStyle());
		}
		
		// On récupère la liste des arcs du graphe à copier
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		// Pour chacun de ses arcs...
		for(Object c : edges) {
			mxCell edge = (mxCell) c;
			// On l'ajoute à la copie
			if(edge.getSource() != null && edge.getTarget() != null) {
			copy.insertEdge(parent, 
					null, 
					edge.getValue(), 
					getCellFromId(copy, edge.getSource().getId()), 
					getCellFromId(copy, edge.getTarget().getId()), 
					edge.getStyle());
			}
		}

		return copy;
	}
	
	/**
	 * Renvoie un sommet en fonction de son ID
	 * @param g le graphe où rechercher
	 * @param id l'idée du sommet à trouver
	 * @return le sommet lié à l'id, ou null si non trouvé
	 */
	public mxCell getCellFromId(mxGraph graph, String id) {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for(Object c : vertices) {
			mxCell vertex = (mxCell) c;
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
	public mxGraph getFirstStep() {
		currentStep = 0;
		return steps.get(currentStep);
	}
	
	/**
	 * Renvoie la dernière étape de l'algo
	 * @return
	 */
	public mxGraph getLastStep() {
		currentStep = nbSteps;
		return steps.get(currentStep);
	}
	
	/**
	 * Renvoie l'étape précédente de l'algo
	 * @return
	 * @throws Exception
	 */
	public mxGraph getPreviousStep() throws Exception {
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
	public mxGraph getNextStep() throws Exception {
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
	public void displayGraph(mxGraph graph) {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		for(Object c : cells) {
			mxCell cell = (mxCell) c;
			System.out.println("Vertex " + cell.getValue() + " : " + cell.getStyle());
		}
	}
}


