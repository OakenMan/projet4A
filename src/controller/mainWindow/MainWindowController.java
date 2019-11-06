package controller.mainWindow;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

import algorithms.AbstractShortestPath;
import algorithms.AlgoTest;
import util.Serialize;
import util.StyleSheet;
import view.mainWindow.MainWindow;

public class MainWindowController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;
	private static mxGraph graph;
	private static AbstractShortestPath asp;

	private static int start;
	private static int end;

	private static double speed;

	private static String graphPath;

	/*===== BUILDER =====*/
	public static void main(String[] args) {
		start = -1;
		end = -1;
		graphPath = "";

		graph = new mxGraph();
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
	 * Charge un graphe depuis un fichier *.grp
	 */
	public static void loadGraph() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Graph file", "grp"));

		// Ouverture d'une fenêtre de type "Ouvrir fichier"
		int returnVal = fileChooser.showOpenDialog(new JFrame());

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
				System.out.println("Error : invalid extension");
			}
		} else {
			//			System.out.println("Graph wasn't loaded");
		}	
	}

	/**
	 * Méthode pour savoir si le graphe contient ou non un sommet
	 * @param id l'id du sommet à vérifier
	 * @return true si le graphe contient le sommet, false sinon
	 * TODO : à bouger dans une autre classe ??
	 */
	public static boolean containsVertex(int id) {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : cells)
		{
			mxCell cell = (mxCell) c;
			if(id == (int)cell.getValue()) {
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
			mxCell cell = (mxCell) c;
			if((int)cell.getValue() == start) {
				graph.getModel().setStyle(cell, "START");
			}
			else if((int)cell.getValue() == end) {
				graph.getModel().setStyle(cell, "END");
			}
			else {
				graph.getModel().setStyle(cell, "ROUNDED");
			}
		}
	}

	public static void findPCC() {
		asp = new AlgoTest(graph);
		asp.displayPotentials();
	}

	/*===== PLAY BUTTONS =====*/
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
				System.out.println("IT");
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


