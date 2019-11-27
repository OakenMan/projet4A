package controller.mainWindow;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

import algorithms.AbstractShortestPath;
import algorithms.AlgoTest;
import algorithms.Dijkstra;
import model.Graph;
import model.Vertex;
import algorithms.BellmanFord;
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

	private static double speed;				// Vitesse de déroulement de l'algo (étapes/s)

	private static String graphPath;			// Chemin du fichier du graphe

	/*===== BUILDER =====*/
	public static void main(String[] args) {
		start = -1;
		end = -1;
		graphPath = "";

		graph = new Graph();
		graph.setStylesheet(new StyleSheet());
		view = new MainWindow(graph);
	}

	/*===== GETTERS AND SETTERS =====*/
	public static mxGraph getGraph() {
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

	public static void setSpeed(double s) {
		speed = s;
		System.out.println("set speed to "+speed+" ("+1000/speed+")");
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
				System.out.println("Graph successfully loaded");
			}
			else {
				System.err.println("Error : invalid extension");
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
		//		System.out.println("did the graph contains "+id+" ?");
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		//		System.out.println(cells.length == 0);
		for (Object c : cells)
		{
			Vertex cell = (Vertex) c;
			int val = cell.getIntValue();
			//			System.out.print(val+", ");
			if(id == val) {
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
			System.out.println("val = "+val);
			if(val == start) {
				System.out.println("val = start = " + start);
				graph.getModel().setStyle(vertex, "START");
			}
			else if(val == end) {
				System.out.println("val = end = " + end);
				graph.getModel().setStyle(vertex, "END");
			}
			else {
				graph.getModel().setStyle(vertex, "ROUNDED");
			}
		}
	}

	public static void findPCC() {
		switch(view.getActionPanel().getSelectedAlgorithm()) {
		case "AlgoTest" : 		asp = new AlgoTest(graph); break;
		case "Dijkstra" : 		asp = new Dijkstra(graph); break;
		case "Bellman-Ford" : 	asp = new BellmanFord(graph); break;
		case "A*" : 			asp = new AlgoTest(graph); break;
		default: break;
		}
		System.out.println("ALGO CHOISI : " + view.getActionPanel().getSelectedAlgorithm());
		asp.displayPotentials();
	}

	/*===== VISUALISATION BUTTONS =====*/
	public static void firstStep() {
		graph = asp.getFirstStep();
		view.setGraph(graph);
	}

	public static void lastStep() {
		graph = asp.getLastStep();
		view.setGraph(graph);
	}

	public static void previousStep() {
		try {
			graph = asp.getPreviousStep();
			view.setGraph(graph);
		}
		catch(Exception e) {
			// Cas où on est déjà au début
		}
	}

	public static void nextStep() {
		try {
			graph = asp.getNextStep();
			view.setGraph(graph);
		}
		catch(Exception e) {
			// Cas où on est déjà à la fin
		}
	}

	public static void playPause() {
		boolean run = true;
		while(run) {
			try {
				graph = asp.getNextStep();	
			}
			catch(Exception e) {
				System.out.println("fin");
				run = false;
			}
			try {
				Thread.sleep(500);
				view.setGraph(graph);
			}  catch (InterruptedException e) {
				// ...(long)((1000/speed))
			}
		}
	}

}


