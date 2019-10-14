package view.mainWindow;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.mainWindow.MainWindowController;

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

	/*===== BUILDER =====*/
	public MainWindowActionsPanel() {
		setPreferredSize(new Dimension(250, 600));

		createGap();

		add(new JLabel("----- PARAMETRES -----"));

		createGap();

		bLoadFile = new JButton("Choix du graphe");
		bLoadFile.addActionListener(this);
		bLoadFile.setPreferredSize(new Dimension(240, 25));
		add(bLoadFile);

		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(240, 25));
		tfGraphFile.setEditable(false);
		add(tfGraphFile);

		createGap();

		add(new JLabel("Choix de l'algorithme"));

		cbAlgo = new JComboBox<String>();
		cbAlgo.addItem("Dijkstra");
		cbAlgo.addItem("Bellman-Ford");
		cbAlgo.addItem("A*");

		cbAlgo.setPreferredSize(new Dimension(240, 25));
		add(cbAlgo);

		createGap();

		add(new JLabel("Choix du départ et de l'arrivée"));

		JLabel bStartVertex = new JLabel("Départ :");
		bStartVertex.setPreferredSize(new Dimension(60, 25));
		add(bStartVertex);

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
		add(tfStartVertex);

		JLabel bEndVertex = new JLabel("Arrivée :");
		bEndVertex.setPreferredSize(new Dimension(60, 25));
		add(bEndVertex);

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
		add(tfEndVertex);

		createGap();
		createGap();
		createGap();

		add(new JLabel("----- SIMULATION -----"));

		createGap();
		
		add(new JLabel("Vitesse (iterations par seconde)"));
		
//		tfSpeed = new JTextField();
//		tfSpeed.setPreferredSize(new Dimension(240, 25));
//		add(tfSpeed);
		
		speedSpinnerModel = new SpinnerNumberModel(1, 0.1, 5, 0.1);
		JSpinner speedSpinner = new JSpinner(speedSpinnerModel);
		speedSpinner.setPreferredSize(new Dimension(240, 25));
		add(speedSpinner);
		
		createGap();
		
		add(new JLabel("Contrôles"));
		
		createGap();

		JButton bFirstStep = new JButton();
		bFirstStep.addActionListener(this);
		bFirstStep.setPreferredSize(new Dimension(44, 25));
		bFirstStep.setIcon(new ImageIcon(rewindIcon));
		add(bFirstStep);

		JButton bLastStep = new JButton();
		bLastStep.addActionListener(this);
		bLastStep.setPreferredSize(new Dimension(44, 25));
		bLastStep.setIcon(new ImageIcon(backIcon));
		add(bLastStep);

		JButton bPlayPause = new JButton();
		bPlayPause.addActionListener(this);
		bPlayPause.setPreferredSize(new Dimension(44, 25));
		bPlayPause.setIcon(new ImageIcon(playIcon));
		add(bPlayPause);

		JButton bNextStep = new JButton();
		bNextStep.addActionListener(this);
		bNextStep.setPreferredSize(new Dimension(44, 25));
		bNextStep.setIcon(new ImageIcon(nextIcon));
		add(bNextStep);

		JButton bEndStep = new JButton();
		bEndStep.addActionListener(this);
		bEndStep.setPreferredSize(new Dimension(44, 25));
		bEndStep.setIcon(new ImageIcon(fastForwardIcon));
		add(bEndStep);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Load graph" :		MainWindowController.loadGraph();
		tfGraphFile.setText(MainWindowController.getGraphPath()); 	break;
		}

	}

	public void createGap() {
		JLabel separator = new JLabel("");
		separator.setPreferredSize(new Dimension(240, 15));
		add(separator);
	}
}
