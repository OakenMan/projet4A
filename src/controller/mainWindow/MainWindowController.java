package controller.mainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxIGraphModel;

import algorithms.AbstractShortestPath;
import algorithms.Algorithm;
import algorithms.BellmanFord;
import algorithms.Dijkstra;
import algorithms.GraphTests;
import algorithms.TravellingSalesman;
import algorithms.VertexColor;
import model.Edge;
import model.Graph;
import model.Vertex;
import util.Serialize;
import util.StyleSheet;
import view.mainWindow.MainWindow;

public class MainWindowController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;				// Fenêtre principale
	private static Graph graph;					// Graphe
	private static AbstractShortestPath asp;	// Algorithme (contient le tableau d'étapes)

	private static int start;					// ID du sommet de départ
	private static int end;						// ID du sommet d'arrivée

	private static int speed = 500;				// Vitesse de déroulement de l'algo (ms/étape)

	private static String graphPath;			// Chemin du fichier du graphe
	private static Algorithm algo;				// Le nom de l'algo utilisé

	private static Timer timer;

	/*===== BUILDER =====*/
	public static void main(String[] args) {
		start = -1;
		end = -1;
		graphPath = "";

		graph = new Graph();
		graph.setStylesheet(new StyleSheet());
		view = new MainWindow(graph);

		algo = null;
	}

	/*===== GETTERS AND SETTERS =====*/
	public static Graph getGraph() {
		return graph;
	}

	public static MainWindow getView() {
		return view;
	}

	public static int getStart() {
		return start;
	}

	public static int getEnd() {
		return end;
	}

	public static void setStart(int id) {
		start = id;
		redrawGraph();
	}

	public static void setEnd(int id) {
		end = id;
		redrawGraph();
	}

	public static String getGraphPath() {
		return graphPath;
	}

	public static void setGraphPath(String path) {
		graphPath = path;
	}

	public static void setSpeed(int s) {
		speed = s;
	}

	public static void setAlgo(Algorithm newAlgo) {
		algo = newAlgo;
	}

	public static Algorithm getAlgo() {
		return algo;
	}

	/*===== METHODS =====*/

	/**
	 * Ouvre une fenêtre de sélection de fichier et charge le graphe choisi
	 */
	public static void loadGraph() {
		// Crée un FileChooser et ajoute un filtre avec l'extension .grp
		JFileChooser fileChooser = new JFileChooser();									
		fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file", "grp"));

		// Ouverture d'une fenêtre de type "Ouvrir fichier"
		int returnVal = fileChooser.showOpenDialog(new JFrame());

		// Si l'opération est un succès
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// Si l'extension est correcte
			if(file.getName().matches(".*.grp")) {
				// On change le graphPath et on charge le graphe
				setGraphPath(file.getPath());
				graph.setModel((mxIGraphModel)Serialize.load(graphPath));
				view.getActionPanel().resetParameters();
				setStart(-1);
				setEnd(-1);

				graph.setCellsEditable(false);
				graph.setCellsMovable(false);
				graph.setCellsResizable(false);
				graph.setCellsDisconnectable(false);

				MainWindowController.getView().getGraphPanel().getPotentialsPane().removeAll();
				
				System.out.println(file.getName());
				if(file.getName().equals("france_with_edges.grp")) {
					// On charge la carte de la france
				}

				System.out.println("Le graphe a été chargé avec succès");
			}
			else {
				System.err.println("Erreur : extension invalide");
			}
		} else {
			// L'utilisateur n'a pas choisi de graphe, on ne fait rien
		}	
	}

	/**
	 * Méthode pour savoir si le graphe contient ou non un sommet (utilisée pour le choix du départ/arrivée)
	 * @param id l'id du sommet à vérifier
	 * @return true si le graphe contient le sommet, false sinon
	 * TODO : à bouger dans une autre classe ??
	 */
	public static boolean containsVertex(int id) {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());

		for (Object o : cells)
		{
			Vertex vertex = (Vertex) o;
			if(id == vertex.getIntValue()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Redessine le graphe à l'écran
	 * TODO : exception ClassCastException quand les label ne sont pas des chiffres
	 */
	public static void redrawGraph() {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : cells)
		{
			Vertex vertex = (Vertex) c;
			int val = vertex.getIntValue();

			if(val == start) {
				graph.getModel().setStyle(vertex, "START");
			}
			else if(val == end) {
				graph.getModel().setStyle(vertex, "END");
			}
			else {
				graph.getModel().setStyle(vertex, "ROUNDED");
			}
		}
	}
	
	public static void clearStyle() {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : cells)
		{
			Vertex vertex = (Vertex) c;
			graph.getModel().setStyle(vertex, "ROUNDED");
		}
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		for (Object c : edges)
		{
			Edge edge = (Edge) c;
			if(GraphTests.isGraphComplete(graph)) {
				graph.getModel().setStyle(edge, "INVISIBLE");
			}
			else {
				graph.getModel().setStyle(edge, "DEFAULT_EDGE");
			}	
		}
		view.getGraphPanel().getPotentialsPane().removeAll();
	}

	public static void findPCC() {
		switch(algo) {
		case DIJKSTRA: 		
			asp = new Dijkstra(graph); 					break;
		case BELLMAN_FORD : 	
			asp = new BellmanFord(graph); 				break;
		case VOYAGEUR_COMMERCE : 			
			asp = new TravellingSalesman(graph);		break;
		case VERTEX_COVER :
			asp = new VertexColor(graph);				break;
		default: break;
		}

		lastStep();
	}
	
	public static void interruptAlgorithm(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/*===== VISUALISATION BUTTONS =====*/
	public static void firstStep() {
		graph = asp.getFirstStep();
		asp.displayPotentials(graph);
		view.setGraph(graph);
	}

	public static void lastStep() {
		graph = asp.getLastStep();
		asp.displayPotentials(graph);
		view.setGraph(graph);
	}

	public static void previousStep() {
		try {
			graph = asp.getPreviousStep();
			asp.displayPotentials(graph);
			view.setGraph(graph);
		}
		catch(Exception e) {
			// Cas où on est déjà au début
		}
	}

	public static void nextStep() {
		try {
			graph = asp.getNextStep();
			asp.displayPotentials(graph);
			view.setGraph(graph);
		}
		catch(Exception e) {
			// Cas où on est déjà à la fin
		}
	}

	public static void playPause() {
		timer = createTimer();
		timer.start();
	}

	private static Timer createTimer(){

		ActionListener action = new ActionListener ()
		{
			// Méthode appelée à chaque tic du timer
			public void actionPerformed (ActionEvent event)
			{
				try {
					graph = asp.getNextStep();
					asp.displayPotentials(graph);
					view.setGraph(graph);
				} catch (Exception e) {
					timer.stop();
				}

			}
		};

		return new Timer (speed, action);
	}

}


