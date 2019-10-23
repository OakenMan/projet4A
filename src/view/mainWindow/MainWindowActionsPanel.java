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
	private JButton bLoadFile;
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

		//		createGap();

		JPanel center = new JPanel();

		center.add(new JLabel("----- PARAMETRES -----"));

		//		createGap();

		bLoadFile = new JButton("Choix du graphe");
		bLoadFile.addActionListener(this);
		bLoadFile.setPreferredSize(new Dimension(240, 25));
		center.add(bLoadFile);

		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(240, 25));
		tfGraphFile.setEditable(false);
		center.add(tfGraphFile);

		createGap(center);

		center.add(new JLabel("Choix de l'algorithme"));

		cbAlgo = new JComboBox<String>();
		cbAlgo.addItem("Dijkstra");
		cbAlgo.addItem("Bellman-Ford");
		cbAlgo.addItem("A*");

		cbAlgo.setPreferredSize(new Dimension(240, 25));
		center.add(cbAlgo);

		center.add(new InfoDijkstra());

		createGap(center);

		center.add(new JLabel("Choix du départ et de l'arrivée"));

		JLabel bStartVertex = new JLabel("Départ :");
		bStartVertex.setPreferredSize(new Dimension(60, 25));
		center.add(bStartVertex);

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
				if(tfStartVertex.getText().matches("[0-9]*") && !tfStartVertex.getText().equals("")) {
					if (MainWindowController.containsVertex(Integer.parseInt(tfStartVertex.getText()))) {
						if(Integer.parseInt(tfStartVertex.getText()) != MainWindowController.getEnd()) {
							tfStartVertex.setBackground(Color.GREEN);
							MainWindowController.setStart(Integer.parseInt(tfStartVertex.getText()));
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
				if(tfEndVertex.getText().matches("[0-9]*") && !tfEndVertex.getText().equals("")) {
					if (MainWindowController.containsVertex(Integer.parseInt(tfEndVertex.getText()))) {
						if(Integer.parseInt(tfEndVertex.getText()) != MainWindowController.getStart()) {
							tfEndVertex.setBackground(Color.GREEN);
							MainWindowController.setEnd(Integer.parseInt(tfEndVertex.getText()));
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

		//		createGap();

		south.add(new JLabel("Vitesse (iterations par seconde)"));

		//		tfSpeed = new JTextField();
		//		tfSpeed.setPreferredSize(new Dimension(240, 25));
		//		add(tfSpeed);

		speedSpinnerModel = new SpinnerNumberModel(1.0, 0.1, 5.0, 0.1);
		speedSpinnerModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				MainWindowController.setSpeed((double)speedSpinnerModel.getValue());
			}
		});

		JSpinner speedSpinner = new JSpinner(speedSpinnerModel);
		speedSpinner.setPreferredSize(new Dimension(240, 25));
		south.add(speedSpinner);

		createGap(south);

		JButton calcShortestPath = new JButton("Calculer le PCC");
		calcShortestPath.addActionListener(this);
		calcShortestPath.setPreferredSize(new Dimension(240, 25));
		south.add(calcShortestPath);

		bFirstStep = new JButton();
		bFirstStep.addActionListener(this);
		bFirstStep.setPreferredSize(new Dimension(44, 25));
		bFirstStep.setIcon(new ImageIcon(rewindIcon));
		south.add(bFirstStep);

		bPreviousStep = new JButton();
		bPreviousStep.addActionListener(this);
		bPreviousStep.setPreferredSize(new Dimension(44, 25));
		bPreviousStep.setIcon(new ImageIcon(backIcon));
		south.add(bPreviousStep);

		bPlayPause = new JButton();
		bPlayPause.addActionListener(this);
		bPlayPause.setPreferredSize(new Dimension(44, 25));
		bPlayPause.setIcon(new ImageIcon(playIcon));
		south.add(bPlayPause);

		bNextStep = new JButton();
		bNextStep.addActionListener(this);
		bNextStep.setPreferredSize(new Dimension(44, 25));
		bNextStep.setIcon(new ImageIcon(nextIcon));
		south.add(bNextStep);

		bLastStep = new JButton();
		bLastStep.addActionListener(this);
		bLastStep.setPreferredSize(new Dimension(44, 25));
		bLastStep.setIcon(new ImageIcon(fastForwardIcon));
		south.add(bLastStep);

		add(south, BorderLayout.SOUTH);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Choix du graphe" :		MainWindowController.loadGraph();
		tfGraphFile.setText(MainWindowController.getGraphPath()); 	break;
		case "Calculer le PCC" :		MainWindowController.findPCC(); break;
		}
		if(e.getSource() == bFirstStep) { MainWindowController.firstStep(); }
		else if(e.getSource() == bPreviousStep) { MainWindowController.previousStep(); }
		else if(e.getSource() == bPlayPause) { MainWindowController.playPause(); }
		else if(e.getSource() == bNextStep) { MainWindowController.nextStep(); }
		else if(e.getSource() == bLastStep) { MainWindowController.lastStep(); }

	}

	public void createGap(JPanel panel) {
		JLabel separator = new JLabel("");
		separator.setPreferredSize(new Dimension(240, 10));
		panel.add(separator);
	}

	public void resetParameters() {
		tfStartVertex.setText("");
		tfStartVertex.setBackground(null);
		tfEndVertex.setText("");
		tfEndVertex.setBackground(null);
	}
}
