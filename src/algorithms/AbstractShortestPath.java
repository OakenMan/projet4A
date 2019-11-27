package algorithms;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controller.mainWindow.MainWindowController;
import util.StyleSheet;

public abstract class AbstractShortestPath implements ShortestPath {

	/*===== ATTRIBUTES =====*/
	protected mxGraph graph;						// Le graphe
	protected ArrayList<mxGraph> steps;				// Les diff�rents �tats du graphe
	protected int currentStep;						// L'�tat actuel
	protected HashMap<mxCell, String> potentials;	// Les distances � ce sommet l� depuis le sommet de d�part
	
	/*===== BUILDER =====*/
	public AbstractShortestPath(mxGraph graph) {
		steps = new ArrayList<mxGraph>();
		potentials = new HashMap<mxCell, String>();
		this.graph = graph;
		currentStep = 0;
		findShortestPath();
	}
	
	/*===== METHODS =====*/
	@Override
	public abstract void findShortestPath();
	
	public void displayPotentials() {
		JPanel potPane = MainWindowController.getView().getGraphPanel().getPotPane();
//		JViewport potPane = MainWindowController.getView().getGraphPanel().getGraphPane();
		
		for(Map.Entry<mxCell, String> mapentry : potentials.entrySet()) {
			JLabel potential = new JLabel(mapentry.getValue());
			
			int x = (int)mapentry.getKey().getGeometry().getX();
			int y = (int)mapentry.getKey().getGeometry().getY()-30;
			
			System.out.println("Sommet " + mapentry.getKey().getValue() + " ---> " + mapentry.getValue() + "("+x+","+y+")");
			
			potential.setBounds(new Rectangle(x, y, 50, 50));
			potential.setBackground(Color.WHITE);
			potential.setForeground(Color.RED);
			potPane.add(potential);
			
			potPane.validate();
			
		}
	}
	public mxCell getBeginning()
	{
		mxCell startCell = null;
		
		// On r�cup�re le sommet de d�part
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : vertices)
		{
			mxCell cell = (mxCell) c;
			
			if((int)cell.getValue() == MainWindowController.getStart()) 
			{
				startCell = cell;
				graph.getModel().setStyle(startCell, "BOLD_START");
				System.out.println("Départ = "+startCell.getValue().toString());
			}
		}
		return startCell;
	}
	
	public mxCell getEnd()
	{
		mxCell endCell = null;
		
		// On r�cup�re le sommet d'arriv�
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : vertices)
		{
			mxCell cell = (mxCell) c;

			if((int)cell.getValue() == MainWindowController.getEnd()) 
			{
				endCell = cell;
				System.out.println("Arrivée = "+endCell.getValue().toString());
			}
		}
		return endCell;
	}
	
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
		currentStep = steps.size();
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
	
	public static int getValue(mxCell edge)
	{
		return Integer.parseInt((String)edge.getValue());
	}
	
	public int getPotential(mxCell vertex) throws Exception
	{
		for(Map.Entry<mxCell, String> mapentry : potentials.entrySet()) 
		{
			if (mapentry.getKey().equals(vertex))
				return Integer.parseInt(mapentry.getValue());
		}
		throw new Exception ("Le sommet " + vertex + " n'est pas pr�sent dans le tableau de potentiel");
	}
	
	public int getDistanceBetween(mxCell vertex1, mxCell vertex2) throws Exception
	{
		Object[] edgesFromVertex1 = graph.getOutgoingEdges(vertex1);
		for (Object o : edgesFromVertex1)
		{
			mxCell edge = (mxCell) o;
			if (edge.getTarget().equals(vertex2))
			{
				return getValue(edge);
			}
		}
		throw new Exception ("Les deux sommets " + vertex1 + " et " + vertex2 + " ne sont pas reli�s entre eux");
	}
}


