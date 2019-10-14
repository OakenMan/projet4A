package view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mxgraph.view.mxGraph;

import view.GraphDisplayPanel;

public class MainWindow extends JFrame {

	private GraphDisplayPanel graphPanel;
	private MainWindowActionsPanel actionPanel;

	public MainWindow(mxGraph graph) {
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
	
	public void setGraph(mxGraph g) {
		graphPanel.setGraph(g);
	}
}
