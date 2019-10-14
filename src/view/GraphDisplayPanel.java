package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDisplayPanel extends JPanel {

	private mxGraph graph;
	private mxGraphComponent graphComponent;

	public GraphDisplayPanel(mxGraph g) {
		this.graph = g;
		
		setLayout(new BorderLayout());

		graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setOpaque(true);
	    graphComponent.getViewport().setBackground(Color.WHITE);
		
		add(graphComponent, BorderLayout.CENTER);
	}
	
	public void setGraph(mxGraph graph) {
		this.graph = graph;
		graphComponent.setGraph(graph);
		graphComponent.refresh();
	}
}
