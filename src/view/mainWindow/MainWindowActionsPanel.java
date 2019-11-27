package view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.mainWindow.MainWindowController;
import view.algoInfos.InfoDijkstra;

public class MainWindowActionsPanel extends JPanel implements ActionListener {

	/*===== ICONS PATHS =====*/
	protected static final URL rewindIcon = MainWindowActionsPanel.class.getResource("/icons/rewind.png");
	protected static final URL backIcon = MainWindowActionsPanel.class.getResource("/icons/back.png");
	protected static final URL playIcon = MainWindowActionsPanel.class.getResource("/icons/play.png");
	protected static final URL pauseIcon = MainWindowActionsPanel.class.getResource("/icons/pause.png");
	protected static final URL nextIcon = MainWindowActionsPanel.class.getResource("/icons/next.png");
	protected static final URL fastForwardIcon = MainWindowActionsPanel.class.getResource("/icons/fast-forward.png");

	/*===== ATTRIBUTES =====*/
	private JTextField tfGraphFile;

	private JComboBox<String> cbAlgo;

	private JTextField tfStartVertex;
	private JTextField tfEndVertex;

	private SpinnerNumberModel speedSpinnerModel;

	private JButton bFirstStep;
	private JButton bPreviousStep;
	private JButton bPlayPause;
	private JButton bNextStep;
	private JButton bLastStep;

	/*===== BUILDER =====*/
	public MainWindowActionsPanel() {
		
		setPreferredSize(new Dimension(250, 600));
		setLayout(new BorderLayout());

		JPanel center = new JPanel();

		center.add(new JLabel("----- PARAMETRES -----"));

		// Bouton "choix du graphe"
		JButton bLoadFile = new JButton("Choix du graphe");
		bLoadFile.addActionListener(this);
		bLoadFile.setPreferredSize(new Dimension(240, 25));
		center.add(bLoadFile);

		// Champ texte avec chemin du graphe
		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(240, 25));
		tfGraphFile.setEditable(false);
		center.add(tfGraphFile);

		createGap(center);

		center.add(new JLabel("Choix de l'algorithme"));

		// Liste des algos
		cbAlgo = new JComboBox<String>();
		cbAlgo.addItem("AlgoTest");
		cbAlgo.addItem("Dijkstra");
		cbAlgo.addItem("Bellman-Ford");
		cbAlgo.addItem("A*");

		cbAlgo.setPreferredSize(new Dimension(240, 25));
		center.add(cbAlgo);

		// Infos liées à l'algo
		center.add(new InfoDijkstra());

		createGap(center);

		center.add(new JLabel("Choix du départ et de l'arrivée"));

		JLabel bStartVertex = new JLabel("Départ :");
		bStartVertex.setPreferredSize(new Dimension(60, 25));
		center.add(bStartVertex);

		// Champ départ
		tfStartVertex = new JTextField();
		tfStartVertex.setPreferredSize(new Dimension(175, 25));
		tfStartVertex.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tfStartVertex.getText().matches("[0-9]*") && !tfStartVertex.getText().equals("")) {		// Si c'est bien un nombre
					if (MainWindowController.containsVertex(Integer.parseInt(tfStartVertex.getText()))) {	// Si le sommet est bien dans le graphe
						if(Integer.parseInt(tfStartVertex.getText()) != MainWindowController.getEnd()) {	// Si c'est pas déjà la fin
							tfStartVertex.setBackground(Color.GREEN);
							MainWindowController.setStart(Integer.parseInt(tfStartVertex.getText()));		// Alors on modifie le départ
						}
						else {
							tfStartVertex.setBackground(Color.ORANGE);
							MainWindowController.setStart(-1);
						}
					}
					else {
						tfStartVertex.setBackground(Color.ORANGE);
						MainWindowController.setStart(-1);
					}
				}
				else {
					tfStartVertex.setBackground(Color.ORANGE);
					MainWindowController.setStart(-1);
				}
			}
		});
		center.add(tfStartVertex);

		JLabel bEndVertex = new JLabel("Arrivée :");
		bEndVertex.setPreferredSize(new Dimension(60, 25));
		center.add(bEndVertex);

		// Champ arrivée
		tfEndVertex = new JTextField();
		tfEndVertex.setPreferredSize(new Dimension(175, 25));
		tfEndVertex.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) { warn(); }
			@Override
			public void removeUpdate(DocumentEvent e) { warn(); }
			@Override
			public void insertUpdate(DocumentEvent e) { warn(); }
			public void warn() {
				if(tfEndVertex.getText().matches("[0-9]*") && !tfEndVertex.getText().equals("")) {			// Si c'est bien un nombre
					if (MainWindowController.containsVertex(Integer.parseInt(tfEndVertex.getText()))) {		// Si le sommet est bien dans le graphe
						if(Integer.parseInt(tfEndVertex.getText()) != MainWindowController.getStart()) {	// Si c'est pas déjà la départ
							tfEndVertex.setBackground(Color.GREEN);
							MainWindowController.setEnd(Integer.parseInt(tfEndVertex.getText()));			// Alors on modifie l'arrivée
						}
						else {
							tfEndVertex.setBackground(Color.ORANGE);
							MainWindowController.setEnd(-1);
						}
					}
					else {
						tfEndVertex.setBackground(Color.ORANGE);
						MainWindowController.setEnd(-1);
					}
				}
				else {
					tfEndVertex.setBackground(Color.ORANGE);
					MainWindowController.setEnd(-1);
				}
			}
		});
		center.add(tfEndVertex);

		add(center, BorderLayout.CENTER);

		JPanel south = new JPanel();
		south.setPreferredSize(new Dimension(240, 160));

		south.add(new JLabel("----- SIMULATION -----"));

		south.add(new JLabel("Vitesse (iterations par seconde)"));

		// Spinner pour choisir la vitesse (en étapes/s)
		speedSpinnerModel = new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1);		// Défaut=1, Min=0.1, Max=5, Step=0.1
		speedSpinnerModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {						// A chaque changement on met à jour la valeur de speed
				MainWindowController.setSpeed((double)speedSpinnerModel.getValue());
			}
		});

		JSpinner speedSpinner = new JSpinner(speedSpinnerModel);
		speedSpinner.setPreferredSize(new Dimension(240, 25));
		south.add(speedSpinner);

		createGap(south);

		// Bouton "Calcul du plus court chemin"
		JButton calcShortestPath = new JButton("Calculer le plus court chemin");
		calcShortestPath.addActionListener(this);
		calcShortestPath.setPreferredSize(new Dimension(240, 25));
		south.add(calcShortestPath);

		// Bouton "première étape"
		bFirstStep = new JButton();
		bFirstStep.addActionListener(this);
		bFirstStep.setPreferredSize(new Dimension(44, 25));
		bFirstStep.setIcon(new ImageIcon(rewindIcon));
		south.add(bFirstStep);

		// Bouton "étape précédente"
		bPreviousStep = new JButton();
		bPreviousStep.addActionListener(this);
		bPreviousStep.setPreferredSize(new Dimension(44, 25));
		bPreviousStep.setIcon(new ImageIcon(backIcon));
		south.add(bPreviousStep);

		// Bouton "play/pause"
		bPlayPause = new JButton();
		bPlayPause.addActionListener(this);
		bPlayPause.setPreferredSize(new Dimension(44, 25));
		bPlayPause.setIcon(new ImageIcon(playIcon));
		south.add(bPlayPause);

		// Bouton "étape suivante"
		bNextStep = new JButton();
		bNextStep.addActionListener(this);
		bNextStep.setPreferredSize(new Dimension(44, 25));
		bNextStep.setIcon(new ImageIcon(nextIcon));
		south.add(bNextStep);

		// Bouton "dernière étape"
		bLastStep = new JButton();
		bLastStep.addActionListener(this);
		bLastStep.setPreferredSize(new Dimension(44, 25));
		bLastStep.setIcon(new ImageIcon(fastForwardIcon));
		south.add(bLastStep);

		add(south, BorderLayout.SOUTH);
	}
	
	/*===== GETTERS AND SETTERS =====*/

	public String getSelectedAlgorithm() {
		return (String)cbAlgo.getSelectedItem();
	}
	
	/*===== METHODS =====*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Choix du graphe" :		MainWindowController.loadGraph();
		tfGraphFile.setText(MainWindowController.getGraphPath()); 	break;
		case "Calculer le plus court chemin" :		MainWindowController.findPCC(); break;
		}
		if(e.getSource() == bFirstStep) { MainWindowController.firstStep(); }
		else if(e.getSource() == bPreviousStep) { MainWindowController.previousStep(); }
		else if(e.getSource() == bPlayPause) { MainWindowController.playPause(); }
		else if(e.getSource() == bNextStep) { MainWindowController.nextStep(); }
		else if(e.getSource() == bLastStep) { MainWindowController.lastStep(); }

	}

	/**
	 * Reset les paramètres (départ et arrivée)
	 */
	public void resetParameters() {
		tfStartVertex.setText("");
		tfStartVertex.setBackground(null);
		tfEndVertex.setText("");
		tfEndVertex.setBackground(null);
	}
	
	private void createGap(JPanel panel) {
		JLabel separator = new JLabel("");
		separator.setPreferredSize(new Dimension(240, 10));
		panel.add(separator);
	}
}
