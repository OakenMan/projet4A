package controller.graphBuilder;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.mxgraph.model.mxIGraphModel;
import com.mxgraph.view.mxGraph;

import util.Serialize;
import util.StyleSheet;
import view.graphBuilder.GraphBuilderWindow;

public class GraphBuilderController {
	
	private static GraphBuilderWindow view;
	private static mxGraph graph;
	
	private static int nbVertex;
	
	public static void main(String[] args) {
		graph = new mxGraph();
		graph.setStylesheet(new StyleSheet());
		view = new GraphBuilderWindow(graph);
		
		nbVertex = 0;
	}
	
	public static void addVertex() {
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();

		try {
			nbVertex++;
			graph.insertVertex(parent, null, nbVertex, 340, 250, 30, 30, "ROUNDED");
		} finally {
			graph.getModel().endUpdate();
		}
	}
	
	public static void loadGraph(String graphPath) {
		graph.setModel((mxIGraphModel)Serialize.load(graphPath));
		System.out.println("Graph successfully loaded");
	}
	
	public static void saveGraph() {
		JFileChooser fileChooser = new JFileChooser();
		String path = "";
		
		int returnVal = fileChooser.showSaveDialog(new JFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			path = fileChooser.getSelectedFile().toString() + ".grp";
			Serialize.save(graph.getModel(), path);
			System.out.println("Graph successfully saved as " + path);
		} else {
			System.out.println("Graph wasn't saved");
		}	
	}
	
	public static void newGraph() {
		graph = new mxGraph();
		graph.setStylesheet(new StyleSheet());
		view.setGraph(graph);
	}
	
	public static void deleteCells() {
		graph.removeCells(graph.getSelectionModel().getCells());
	}
}
