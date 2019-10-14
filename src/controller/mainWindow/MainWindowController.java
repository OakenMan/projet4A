package controller.mainWindow;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

import util.Serialize;
import util.StyleSheet;
import view.mainWindow.MainWindow;

public class MainWindowController {

	/*===== ATTRIBUTES =====*/
	@SuppressWarnings("unused")
	private static MainWindow view;
	private static mxGraph graph;
	
	private static int start;
	private static int end;
	
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
				System.out.println("Graph successfully loaded");
			}
			else {
				System.out.println("Error : invalid extension");
			}
		} else {
//			System.out.println("Graph wasn't loaded");
		}	
	}
	
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
	
	
}


