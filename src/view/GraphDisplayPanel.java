package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Rectangle;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphDisplayPanel extends JPanel {

	private mxGraph graph;
	private mxGraphComponent graphComponent;
	private JPanel potPane;

	public GraphDisplayPanel(mxGraph g) {
		this.graph = g;

		setLayout(new BorderLayout());

		graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		
		potPane = new JPanel(new FlowLayout());
		add(potPane);

		setVisible(true);

		add(graphComponent, BorderLayout.CENTER);
	}

	public void setGraph(mxGraph graph) {
		this.graph = graph;

		JLabel test = new JLabel("TEST");
		test.setBounds(new Rectangle(200, 200, 50, 50));
		potPane.add(test);
		potPane.validate();

		graphComponent.setGraph(graph);
		graphComponent.refresh();
	}

	public mxGraphComponent getGraphComponent() {
		return graphComponent;
	}
}
