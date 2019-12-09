package view.graphBuilder;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.graphBuilder.GraphBuilderController;

public class GraphBuilderActionsPanel extends JPanel implements ActionListener {

	/*===== ATTRIBUTES =====*/
	private JTextField tfGraphFile;
	private JButton bLoadFile;
	private JButton bNewGraph;
	private JButton bSaveGraph;
	private JButton bAddVertex;
	private JButton bDeleteCells;
	private JTextField tfX;
	private JTextField tfY;

	/*===== BUILDER =====*/
	public GraphBuilderActionsPanel() {
		setPreferredSize(new Dimension(250, 600));

		JLabel lOptions = new JLabel("OPTIONS", SwingConstants.CENTER);
		add(lOptions);

		bLoadFile = new JButton("Load graph");
		bLoadFile.addActionListener(this);
		bLoadFile.setPreferredSize(new Dimension(240, 25));
		add(bLoadFile);

		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(240, 25));
		tfGraphFile.setEditable(false);
		add(tfGraphFile);

		createGap();

		bNewGraph = new JButton("New graph");
		bNewGraph.addActionListener(this);
		bNewGraph.setPreferredSize(new Dimension(240, 25));
		add(bNewGraph);

		createGap();

		bSaveGraph = new JButton("Save graph");
		bSaveGraph.addActionListener(this);
		bSaveGraph.setPreferredSize(new Dimension(240, 25));
		add(bSaveGraph);

		createGap();

		JLabel lActions = new JLabel("ACTIONS", SwingConstants.CENTER);
		lActions.setPreferredSize(new Dimension(250, 25));
		add(lActions);

		add(new JLabel("x ="));

		tfX = new JTextField();
		tfX.setPreferredSize(new Dimension(70, 25));
		add(tfX);

		add(new JLabel("y ="));

		tfY = new JTextField();
		tfY.setPreferredSize(new Dimension(70, 25));
		add(tfY);

		bAddVertex = new JButton("Add vertex");
		bAddVertex.addActionListener(this);
		bAddVertex.setPreferredSize(new Dimension(240, 25));
		add(bAddVertex);

		bDeleteCells = new JButton("Delete cells");
		bDeleteCells.addActionListener(this);
		bDeleteCells.setPreferredSize(new Dimension(240, 25));
		add(bDeleteCells);
	}

	/*===== METHODS =====*/
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Load graph" :		GraphBuilderController.loadGraph();		
		tfGraphFile.setText(GraphBuilderController.getGraphPath()); 	break;
		case "Save graph" : 	GraphBuilderController.saveGraph();		break;
		case "Add vertex" : 	GraphBuilderController.addVertex(tfX.getText(), tfY.getText());		break;
		case "New graph" :		GraphBuilderController.newGraph();		
		tfGraphFile.setText("");										break;
		case "Delete cells" :	GraphBuilderController.deleteCells();	break;
		}
	}

	public void createGap() {
		JLabel separator = new JLabel("");
		separator.setPreferredSize(new Dimension(240, 15));
		add(separator);
	}
}
