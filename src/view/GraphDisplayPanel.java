package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JViewport;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDisplayPanel extends JPanel {

	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private JPanel potPane;
	private JLayeredPane lpane;

	public GraphDisplayPanel(mxGraph g) {
		this.graph = g;

		setLayout(new BorderLayout());

		// LAYERED PANE
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 800, 600);
		add(lpane, BorderLayout.CENTER);
		
        // GRAPH PANE
		graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		graphComponent.setBounds(0, 0, 1920, 1080);
		lpane.add(graphComponent, 0, 0);
		
		// POTENTIALS PANE
		potPane = new JPanel();
		potPane.setLayout(null);
		potPane.setOpaque(false);
		potPane.setBorder(BorderFactory.createLineBorder(Color.red));
		potPane.setBounds(0, 0, 1920, 1080);
		lpane.add(potPane, 1, 0);
		
//		JLabel text = new JLabel("test");
//		text.setBounds(50, 50, 50, 50);
//		potPane.add(text);

		setVisible(true);
	}

	public void setGraph(mxGraph graph) {
		this.graph = graph;
	
		graphComponent.setGraph(graph);
		graphComponent.refresh();
	}

	public JPanel getPotPane() {
		return potPane;
	}
	
	public JViewport getGraphPane() {
		return graphComponent.getViewport();
	}
}
