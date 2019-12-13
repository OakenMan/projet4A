package controller.graphBuilder;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxIGraphModel;

import model.Graph;
import model.Vertex;
import util.Serialize;
import util.StyleSheet;
import view.graphBuilder.GraphBuilderWindow;

/**
 * Cette classe utilise le nouveau modèle, avec les surcharges des mxCell et de mxGraph
 */
public class GraphBuilderController {

	/*===== CONST =====*/
	private final static int vertexRadius = 40;

	/*===== ATTRIBUTES =====*/
	private static GraphBuilderWindow view;
	private static Graph graph;

	private static String graphPath;

	private static int nbVertex;

	/*===== MAIN =====*/
	public static void main(String[] args) {
		graphPath = "";
		nbVertex = 0;

		graph = new Graph();
		graph.setStylesheet(new StyleSheet());

		view = new GraphBuilderWindow(graph);
	}

	/*===== GETTERS AND SETTERS =====*/
	public static String getGraphPath() {
		return graphPath;
	}

	public static void setGraphPath(String path) {
		graphPath = path;
	}

	/*===== METHODS =====*/
	/**
	 * Ajoute un sommet au graphe
	 * TODO : faire une méthode pour calculer la position du vertex à ajouter, pour qu'il soit jamais sur un ancien vertex
	 */
	public static void addVertex(String x, String y) {
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();

		try {
			if(x.matches("\\d*") && y.matches("\\d*")) {
				graph.insertVertex(parent, null, nbVertex, Integer.parseInt(x), Integer.parseInt(y), vertexRadius, vertexRadius);
			}
			else {
				graph.insertVertex(parent, null, nbVertex, 340, 250, vertexRadius, vertexRadius);
			}
			
			nbVertex++;
		} finally {
			graph.getModel().endUpdate();
		}
	}

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

	/**
	 * Sauvegarde le graphe
	 */
	public static void saveGraph() {
		JFileChooser fileChooser = new JFileChooser();
		String path = "";

		// Ouverture d'une fenêtre de type "Sauvegarder fichier"
		int returnVal = fileChooser.showSaveDialog(new JFrame());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			String name = fileChooser.getSelectedFile().toString();
			// Si l'utilisateur rajoute déjà l'extension on garde le nom tel quel
			if(name.matches(".*.grp")) {
				path = name;
			}
			// Sinon on rajoute l'extention à la fin du fichier
			else {
				path = name + ".grp";
			}
			// On enregistre le graphe
			Serialize.save(graph.getModel(), path);
			System.out.println("Graph successfully saved as " + path);
		} else {
			//			System.out.println("Graph wasn't saved");
		}	
	}

	/**
	 * Créé un nouveau graphe à la place de celui en cours d'édition
	 */
	public static void newGraph() {
		graph = new Graph();
		graph.setStylesheet(new StyleSheet());
		view.setGraph(graph);
	}

	/**
	 * Supprime les cellules (sommets et arcs) sélectionnées
	 * Attention : lors de la suppression d'un sommet, cette fonction supprime aussi ses arcs liés
	 */
	public static void deleteCells() {
		graph.removeCells(graph.getSelectionModel().getCells());
	}
}
