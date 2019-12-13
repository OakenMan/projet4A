package controller.graphBuilder;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mxgraph.model.mxIGraphModel;

import model.Edge;
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

		view = new GraphBuilderWindow(graph);
		
		resetGraph();
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
	 */
	public static void addVertex(String x, String y) {
		Object parent = graph.getDefaultParent();

		graph.getModel().beginUpdate();

		try {
			// Si on a spécifié une position :
			if(x != null && y != null) {
				if(x.matches("[0-9]+") && y.matches("[0-9]+")) {
					graph.insertVertex(parent, null, nbVertex, Integer.parseInt(x), Integer.parseInt(y), vertexRadius, vertexRadius);
				}
				else {
					graph.insertVertex(parent, null, nbVertex, 340, 250, vertexRadius, vertexRadius);
				}
			}
			// Sinon :
			else {
				graph.insertVertex(parent, null, nbVertex, 340, 250, vertexRadius, vertexRadius);
			}
			nbVertex++;
		} finally {
			graph.getModel().endUpdate();
		}
	}

	/**
	 * Relie tous les sommets entre eux, en calculant automatiquement les distances
	 */
	public static void connectAllVertices() {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		// Pour chaque couple de sommets
		for (Object o1 : vertices) 																			
		{
			for (Object o2 : vertices) 																			
			{
				Vertex vertex1 = (Vertex) o1;
				Vertex vertex2 = (Vertex) o2;

				// On ajoute un arc entre les 2 sommets (sauf boucle)
				if (!(vertex1.equals(vertex2)))
				{
					double x1 = graph.getCellGeometry(vertex1).getX();
					double y1 = graph.getCellGeometry(vertex1).getY();
					double x2 = graph.getCellGeometry(vertex2).getX();
					double y2 = graph.getCellGeometry(vertex2).getY();

					int distance = (int)Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));

					graph.insertEdge(graph.getDefaultParent(), null, distance, vertex1, vertex2);
				}
			}
		}
	}

	/**
	 * Met à jour le style pour cacher les arcs
	 * @param b true pour cacher les arcs, false sinon
	 */
	public static void hideEdges(boolean b) {
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());

		for (Object o : edges) 		
		{
			Edge edge = (Edge) o;
			if(b) {	graph.getModel().setStyle(edge, "INVISIBLE");    }
			else  { graph.getModel().setStyle(edge, "DEFAULT_EDGE"); }		
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
				System.out.println("Le charge a été chargé avec succès");
			}
			else {
				System.err.println("Erreur : extension invalide");
			}
		} else {
			// L'utilisateur n'a pas choisi de graphe, on ne fait rien
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
			System.out.println("Le graphe a été sauvegardé au chemin : " + path);
		} else {
			// Le graphe n'a pas été sauvegardé
		}	
	}

	/**
	 * Créé un nouveau graphe à la place de celui en cours d'édition
	 */
	public static void resetGraph() {
		graph = new Graph();
		graph.setStylesheet(new StyleSheet());
		
		graph.setCellsResizable(false);
		graph.setAllowDanglingEdges(false);
		
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

