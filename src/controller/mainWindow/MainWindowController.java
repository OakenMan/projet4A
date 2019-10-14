package controller.mainWindow;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

import util.Serialize;
import util.StyleSheet;
import view.mainWindow.MainWindow;

public class MainWindowController {

	private static MainWindow view;
	private static mxGraph graph;
	
	private static int nbVertex;
	private static int start;
	private static int end;
	
	private static String graphPath;
	
	/*===== BUILDER =====*/
	public static void main(String[] args) {
		graph = new mxGraph();
		graph.setStylesheet(new StyleSheet());
		view = new MainWindow(graph);
		
		nbVertex = 0;
		start = -1;
		end = -1;
		graphPath = "";
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
	
	public static String getGraphPath() {
		return graphPath;
	}
	
	public static void setStart(int id) {
		start = id;
		redrawGraph();
	}
	
	public static void setEnd(int id) {
		end = id;
		redrawGraph();
	}
	
	public static void setGraphPath(String path) {
		graphPath = path;
	}
	
	/*===== METHODS =====*/
	public static void loadGraph(String graphPath) {
		graph.setModel((mxIGraphModel)Serialize.load(graphPath));
		System.out.println("Graph successfully loaded");
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


