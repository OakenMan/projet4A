package view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Graph;
import view.GraphDisplayPanel;

/**
 * Fenetre principale, pour la simulation d'algorithmes
 * @see GraphDisplayPanel
 * @see MainWindowActionsPanel
 */
public class MainWindow extends JFrame {

	/*===== ATTRIBUTES =====*/
	private GraphDisplayPanel graphPanel;			/** Panel d'affichage du graphe **/
	private MainWindowActionsPanel actionPanel;		/** Panel des actions			**/

	/*===== BUILDER =====*/
	public MainWindow(Graph graph) {
		// Configuration de la fenetre
		setTitle("Simulation d'algorithmes");
		setSize(1920, 1080);
		setMinimumSize(new Dimension(1000, 600));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(true);	

		setLayout(new BorderLayout());

		graphPanel = new GraphDisplayPanel(graph);
		actionPanel = new MainWindowActionsPanel();

		add(graphPanel, BorderLayout.CENTER);
		add(actionPanel, BorderLayout.EAST);

		setVisible(true);
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public void setGraph(Graph graph) {
		graphPanel.setGraph(graph);
	}
	
	public MainWindowActionsPanel getActionPanel() {
		return actionPanel;
	}
	
	public GraphDisplayPanel getGraphPanel() {
		return graphPanel;
	}
}