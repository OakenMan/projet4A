package view.graphBuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Graph;
import view.GraphDisplayPanel;

/**
 * Fenêtre de création de graphe
 * @see GraphDisplayPanel
 * @see GraphBuilderActionsPanel
 */
public class GraphBuilderWindow extends JFrame {

	/*===== ATTRIBUTES =====*/
	/** Le panel d'affichage du graphe **/
	private GraphDisplayPanel graphPanel;	
	/** Le panel d'actions			   **/
	private GraphBuilderActionsPanel actionPanel;	

	/*===== BUIDER =====*/
	/**
	 * Construit la fenêtre de création de graphes.
	 * @param graph le graph affiché sur la fenêtre
	 */
	public GraphBuilderWindow(Graph graph) {
		// Configuration de la fenetre
		setTitle("Création de graphes");
		setSize(1920, 1080);
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);	

		setLayout(new BorderLayout());

		graphPanel = new GraphDisplayPanel(graph);
		actionPanel = new GraphBuilderActionsPanel();

		add(graphPanel, BorderLayout.CENTER);
		add(actionPanel, BorderLayout.EAST);

		setVisible(true);
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public void setGraph(Graph g) {
		graphPanel.setGraph(g);
	}
}
