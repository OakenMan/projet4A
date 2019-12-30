package view.graphBuilder;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.graphBuilder.GraphBuilderController;

/**
 * Panel d'actions pour la création de graphes
 */
public class GraphBuilderActionsPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	private JTextField tfGraphFile;
	private JTextField tfX;
	private JTextField tfY;

	/*===== BUILDER =====*/
	public GraphBuilderActionsPanel() {
		
		setPreferredSize(new Dimension(250, 600));

		createGap();
		
		// Label "Options"
		JLabel lOptions = new JLabel("Options", SwingConstants.CENTER);
		lOptions.setPreferredSize(new Dimension(230, 25));
		lOptions.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		add(lOptions);
		
		createGap();

		// Bouton "Charger un graphe"
		JButton bLoadFile = new JButton("Charger un graphe");
		bLoadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.loadGraph();		
				tfGraphFile.setText(GraphBuilderController.getGraphPath());
			}
		});
		bLoadFile.setPreferredSize(new Dimension(230, 25));
		add(bLoadFile);

		// Champ text pour le chemin du graphe
		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(230, 25));
		tfGraphFile.setEditable(false);
		add(tfGraphFile);

		createGap();

		// Bouton "Nouveau graphe"
		JButton bNewGraph = new JButton("Nouveau graphe");
		bNewGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.newGraph();		
				tfGraphFile.setText("");
			}
		});
		bNewGraph.setPreferredSize(new Dimension(230, 25));
		add(bNewGraph);

		createGap();

		// Bouton "Sauvegarder le graphe"
		JButton bSaveGraph = new JButton("Sauvegarder le graphe");
		bSaveGraph.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.saveGraph();
			}
		});
		bSaveGraph.setPreferredSize(new Dimension(230, 25));
		add(bSaveGraph);

		createGap();
		createGap();

		// Label "Actions"
		JLabel lActions = new JLabel("Actions", SwingConstants.CENTER);
		lActions.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		lActions.setPreferredSize(new Dimension(230, 25));
		add(lActions);
		
		createGap();

		// Bouton "Ajouter un sommet"
		JButton bAddVertex = new JButton("Ajouter un sommet");
		bAddVertex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.addVertex(tfX.getText(), tfY.getText());
			}
		});
		bAddVertex.setPreferredSize(new Dimension(230, 25));
		add(bAddVertex);
		
		add(new JLabel("x ="));

		tfX = new JTextField();
		tfX.setPreferredSize(new Dimension(70, 25));
		add(tfX);

		add(new JLabel("      y ="));

		tfY = new JTextField();
		tfY.setPreferredSize(new Dimension(70, 25));
		add(tfY);

		createGap();
		
		// Bouton "Supprimer des cellules"
		JButton bDeleteCells = new JButton("Supprimer des cellules");
		bDeleteCells.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.deleteCells();
			}
		});
		bDeleteCells.setPreferredSize(new Dimension(230, 25));
		add(bDeleteCells);
		
		createGap();
		
		// Bouton "Connecter tous les arcs"
		JButton bConnectAll = new JButton("Connecter tous les arcs");
		bConnectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.connectAllVertices();
			}
		});
		bConnectAll.setPreferredSize(new Dimension(230, 25));
		add(bConnectAll);
		
		createGap();
		
		// Option "Cacher les arcs"
		JRadioButton rbHideEdges = new JRadioButton("Cacher les arcs");
		rbHideEdges.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				GraphBuilderController.hideEdges(rbHideEdges.isSelected());
			}
		});
		add(rbHideEdges);
			
	}

	/*===== METHODS =====*/
	private void createGap() {
		JLabel separator = new JLabel();
		separator.setPreferredSize(new Dimension(240, 15));
		add(separator);
	}
}
