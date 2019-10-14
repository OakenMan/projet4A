package view.graphBuilder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mxgraph.view.mxGraph;

import view.GraphDisplayPanel;

public class GraphBuilderWindow extends JFrame {

	private GraphDisplayPanel graphPanel;
	private GraphBuilderActionsPanel actionPanel;

	public GraphBuilderWindow(mxGraph graph) {
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
	
	public void setGraph(mxGraph g) {
		graphPanel.setGraph(g);
	}
}
