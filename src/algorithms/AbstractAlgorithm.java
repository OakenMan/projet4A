package algorithms;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.StyleSheet;

/**
 * Classe abstraite de laquelle tous les algorithmes héritent. 
 * Elle permet notamment de gérer les différentes étapes, et de lancer l'algorithme.
 */
public abstract class AbstractAlgorithm {

	/*===== CONSTANTS =====*/
	protected final int INFINITE = 9999999;

	/*===== ATTRIBUTES =====*/
	/** Le graphe utilisé par l'algorithme **/
	protected Graph graph;
	/** La liste d'étapes représentant la résolution de l'algorithme @see Step **/
	protected ArrayList<Step> steps;
	/** L'étape affichée actuellement **/
	protected int currentStep;

	/*===== BUILDER =====*/
	/**
	 * Constructeur par défaut pour tous les algorithmes.
	 * Initialise les différents paramètres (tableau d'étapes, graphes...) puis lance l'exécution de l'algo.
	 * Calcule le temps de calcul pour l'algorithme et l'affiche sur la dernière étape.
	 * @param graph
	 */
	public AbstractAlgorithm(Graph graph) {
		steps = new ArrayList<Step>();
		this.graph = graph;
		currentStep = 0;
		
		long time = System.currentTimeMillis();
		
		executeAlgorithm();
		
		time = System.currentTimeMillis() - time;
		
		steps.add(new Step(copy(graph), "Time = " + (float)time/1000 + "s"));
	}

	/*===== METHODS =====*/
	
	/**
	 * Méthode abstraite qui doit être implémentée dans tous les algorithmes. 
	 * Cette méthode doit appliquer l'algorithme sur le graphe et ajouter chaque étape importante en utilisant le code ci-dessous :
	 * <code>steps.add(new Step(copy(graph)), "informations supplémentaires");</code>
	 */
	public abstract void executeAlgorithm();

	/**
	 * Affiche les potentiels à l'écran
	 * @param graph le graphe concerné
	 */
	public void displayPotentials(Graph graph) {
		// On récupère le pannel d'affichage des potentiels et on le reset
		JPanel potPane = MainWindowController.getView().getGraphPanel().getPotentialsPane();
		potPane.removeAll();
		
		JLabel text = new JLabel(steps.get(currentStep).getInfo());
		text.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		text.setBounds(new Rectangle(10, 10, 1000, 25));
		potPane.add(text);
		
		// On récupère tous les sommets du graphe
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		for(Object o : vertices) {
			Vertex vertex = (Vertex) o;

			int pot = vertex.getPotential();
			String potStr = "";

			if(pot == INFINITE) {
				potStr = "\u221e";	// = logo INFINI
			}
			else if(pot > 0) {
				potStr = String.valueOf(pot);
			}
			
			JLabel potential = new JLabel(potStr);

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
	 * Renvoie le sommet de départ
	 * @return Vertex le sommet de départ
	 */
	public Vertex getStartVertex()
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
			}
		}
		return startVertex;
	}

	/**
	 * Renvoie le sommet d'arrivée
	 * @return Vertex le sommet d'arrivée
	 */
	public Vertex getEndVertex()
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
		graph.setCellsEditable(false);
		graph.setCellsMovable(false);
		graph.setCellsResizable(false);
		graph.setCellsDisconnectable(false);
		copy.setStylesheet(new StyleSheet());

		Object parent = copy.getDefaultParent();

		// On récupère la liste des sommets du graphe à copier
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		// Pour chacun de ses sommets...
		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;
			// On l'ajoute à la copie
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

			copy.addCell(vertexCopy, parent);
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
						getCellFromId(copy, ((Vertex)edge.getSource()).getIntValue()), 
						getCellFromId(copy, ((Vertex)edge.getTarget()).getIntValue()), 
						edge.getStyle());
			}
		}

		return copy;
	}

	/**
	 * Renvoie un sommet en fonction de son ID
	 * @param graph le graphe où rechercher
	 * @param id l'idée du sommet à trouver
	 * @return le sommet lié à l'id, ou null si non trouvé
	 */
	public Vertex getCellFromId(Graph graph, int id) {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for(Object c : vertices) {
			Vertex vertex = (Vertex) c;
			if(vertex.getIntValue() == id) {
				return vertex;
			}
		}
		return null;
	}

	/**
	 * Renvoie la première étape de l'algo
	 */
	public Graph getFirstStep() {
		currentStep = 0;
		return steps.get(currentStep).getGraph();
	}

	/**
	 * Renvoie la dernière étape de l'algo
	 */
	public Graph getLastStep() {
		currentStep = steps.size() - 1;
		return steps.get(currentStep).getGraph();
	}

	/**
	 * Renvoie l'étape précédente de l'algo
	 * @throws Exception lorsqu'on est déjà à la première étape
	 */
	public Graph getPreviousStep() throws Exception {
		if(currentStep > 0) {
			currentStep--;
			return steps.get(currentStep).getGraph();
		}
		else {
			throw new Exception("Can't go to previous step");
		}
	}

	/**
	 * Renvoie l'étape suivante de l'algo
	 * @throws Exception lorsqu'on est déjà à la dernière étape
	 */
	public Graph getNextStep() throws Exception {
		if(currentStep < steps.size()) {
			currentStep++;
			return steps.get(currentStep).getGraph();
		}
		else {
			throw new Exception("Can't go to next step");
		}
	}

	/**
	 * Renvoie la distance entre 2 sommets
	 * @param vertex1 le 1er sommet
	 * @param vertex2 le 2ème sommet
	 * @return la distance entre 2 sommets 
	 * @throws Exception lorsque les 2 sommets ne sont pas reliés entre eux
	 */
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


