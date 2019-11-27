package view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mxgraph.view.mxGraph;

import model.Graph;
import view.GraphDisplayPanel;

public class MainWindow extends JFrame {

	/*===== ATTRIBUTES =====*/
	private GraphDisplayPanel graphPanel;			// Panel d'affichage du graphe
	private MainWindowActionsPanel actionPanel;		// Panel des actions

	/*===== BUILDER =====*/
	public MainWindow(Graph graph) {
		// Window configuration (name, size and minimum size, location...)
		setTitle("Create graph");
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
	
	/**
	 * Permet de mettre à jour le graphe
	 * @param graph le nouveau graphe à afficher
	 */
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