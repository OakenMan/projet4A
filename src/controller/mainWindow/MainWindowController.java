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

/**
 * Controlleur de l'application principale (simulation d'algorithmes)
 * Contient la méthode main qui lance l'application, ainsi que des méthodes relatives à la
 * simulation d'algorithmes sur les graphes.
 */
public class MainWindowController {

	/*===== ATTRIBUTES =====*/
	private static MainWindow view;				/** Fenêtre principale							**/
	private static Graph graph;					/** Graphe										**/
	private static AbstractShortestPath asp;	/** Algorithme (contient le tableau d'étapes)	**/

	// Paramètres pour PCC
	private static int start;					/** ID du sommet de départ						**/
	private static int end;						/** ID du sommet d'arrivée						**/

	// Paramètres pour TSP
	private static String baseSolution;			/** Solution de base pour TSP					**/
	private static String heurtistique;			/** Heurtistique pour TSP						**/
	private static int nbTries;					/** Nombre d'essais pour TSP					**/
	private static int nbExchanges;				/** Nombre d'échanges pour TSP					**/

	private static String graphPath;			/** Chemin du fichier du graphe					**/
	private static Algorithm algo;				/** Le nom de l'algo utilisé					**/

	private static int speed = 500;				/** Vitesse de déroulement de l'algo (ms/étape)	**/
	private static Timer timer;					/** Timer utilisé pour la visualisation			**/

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

	// --------------- Pour PCC ---------------
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

	// --------------- Pour TSP ---------------
	public static void setBaseSolution(String baseSolution) {
		MainWindowController.baseSolution = baseSolution;
	}

	public static void setHeurtistique(String heurtistique) {
		MainWindowController.heurtistique = heurtistique;
	}

	public static void setNbTries(int nbTries) {
		MainWindowController.nbTries = nbTries;
	}

	public static void setNbExchanges(int nbExchanges) {
		MainWindowController.nbExchanges = nbExchanges;
	}
	
	public static String getBaseSolution() {
		return baseSolution;
	}

	public static String getHeurtistique() {
		return heurtistique;
	}

	public static int getNbTries() {
		return nbTries;
	}

	public static int getNbExchanges() {
		return nbExchanges;
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
				// On change le graphPath
				setGraphPath(file.getPath());
				
				// On charge le graphe et on change certaines de ses propriétés
				graph.setModel((mxIGraphModel)Serialize.load(graphPath));
				graph.setCellsEditable(false);			// Impossible d'éditer les cellules
				graph.setCellsMovable(false);			// Impossible de bouger les cellules
				graph.setCellsResizable(false);			// Impossible de redimensionner les cellules
				graph.setCellsDisconnectable(false);	// Impossible de déconnecter les arcs
				
				// On reset les paramètres des algorithmes
				view.getActionPanel().resetParameters();
				setStart(-1);
				setEnd(-1);

				// On reset le panel d'affichage des potentiels
				MainWindowController.getView().getGraphPanel().getPotentialsPane().removeAll();
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "Erreur : extension invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			// L'utilisateur n'a pas choisi de graphe, on ne fait rien
		}	
	}

	/**
	 * Méthode pour savoir si le graphe contient ou non un sommet (utilisée pour le choix du départ/arrivée)
	 * @param id l'id du sommet à vérifier
	 * @return true si le graphe contient le sommet, false sinon
	 */
	public static boolean containsVertex(int id) {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());

		for (Object o : cells) {
			Vertex vertex = (Vertex) o;
			if(id == vertex.getIntValue()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Applique les changement de styles sur le graphe affiché à l'écran
	 */
	public static void redrawGraph() {
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		
		for (Object o : cells) {
			Vertex vertex = (Vertex) o;
			int val = vertex.getIntValue();

			if(val == start) {
				graph.getModel().setStyle(vertex, "START");
			}
			else if(val == end) {
				graph.getModel().setStyle(vertex, "END");
			}
			else {
				graph.getModel().setStyle(vertex, "");
			}
		}
	}

	/**
	 * Reset le style du graphe affiché à l'écran
	 */
	public static void clearStyle() {
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		
		// On applique le style par défault à tous les sommets
		for (Object o : vertices) {
			Vertex vertex = (Vertex) o;
			graph.getModel().setStyle(vertex, "");
		}
		
		Object[] edges = graph.getChildEdges(graph.getDefaultParent());
		
		// On applique le style par défault (ou INVISIBLE si le graphe est complet) à tous les arcs
		for (Object o : edges) {
			Edge edge = (Edge) o;
			if(GraphTests.isGraphComplete(graph)) {
				graph.getModel().setStyle(edge, "INVISIBLE");
			}
			else {
				graph.getModel().setStyle(edge, "");
			}	
		}
		
		view.getGraphPanel().getPotentialsPane().removeAll();
	}

	/**
	 * Execute l'algorithme choisi.
	 * Attention : cette méthode peut prendre un certain temps à s'éxecuter.
	 */
	public static void executeAlgorithm() {
		switch(algo) {
		case DIJKSTRA: 		
			asp = new Dijkstra(graph); 					break;
		case BELLMAN_FORD : 	
			asp = new BellmanFord(graph); 				break;
		case VOYAGEUR_COMMERCE : 			
			asp = new TravellingSalesman(graph);		break;
		case VERTEX_COLOR :
			asp = new VertexColor(graph);				break;
		default: break;
		}

		lastStep();		// Affiche la dernière étape (le resultat final) de l'algorithme
	}

	/**
	 * Interromp l'algorithme en court d'éxecution.
	 * @param message le message d'erreur à afficher
	 */
	public static void interruptAlgorithm(String message) {
		JOptionPane.showMessageDialog(new JFrame(), message, "Erreur", JOptionPane.ERROR_MESSAGE);
	}

	/*===== VISUALISATION BUTTONS =====*/
	
	/**
	 * Affiche la première étape
	 */
	public static void firstStep() {
		graph = asp.getFirstStep();
		asp.displayPotentials(graph);
		view.setGraph(graph);
	}

	/**
	 * Affiche la dernière étape
	 */
	public static void lastStep() {
		graph = asp.getLastStep();
		asp.displayPotentials(graph);
		view.setGraph(graph);
	}

	/**
	 * Affiche l'étape précédente
	 */
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

	/**
	 * Affiche l'étape suivante
	 */
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

	private static boolean isPlaying = false;
	
	/**
	 * Lance la visualisation de l'algorithme
	 */
	public static void playPause() {
		// Si on appuie sur le bouton "Play/Pause" alors que on est dans l'état "Pause"
		if(!isPlaying) {
			timer = createTimer();	// On créé un nouveau timer
			timer.start();			// On le lance
			isPlaying = true;		// On va dans l'état "Play"
		}
		// Sinon
		else {						
			timer.stop();			// On arrête le timer en cours
			isPlaying = false;		// On va dans l'état "Pause"
		}	
		// On actualise l'affichage
		view.getActionPanel().updateIfPlaying(isPlaying);
	}

	private static Timer createTimer(){

		ActionListener action = new ActionListener ()
		{
			// Méthode appelée à chaque tic du timer
			@Override
			public void actionPerformed (ActionEvent event)
			{
				try {
					graph = asp.getNextStep();
					asp.displayPotentials(graph);
					view.setGraph(graph);
				} catch (Exception e) {
					timer.stop();
					isPlaying = false;
					view.getActionPanel().updateIfPlaying(isPlaying);
				}

			}
		};

		return new Timer (speed, action);
	}

}


