package view.graphBuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Graph;
import view.GraphDisplayPanel;

public class GraphBuilderWindow extends JFrame {

	private GraphDisplayPanel graphPanel;
	private GraphBuilderActionsPanel actionPanel;

	public GraphBuilderWindow(Graph graph) {
		// Window configuration (name, size and minimum size, location...)
		setTitle("Create graph");
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
	
	public void setGraph(Graph g) {
		graphPanel.setGraph(g);
	}
}
