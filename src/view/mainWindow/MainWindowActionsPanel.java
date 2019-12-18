package view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algorithms.Algorithm;
import algorithms.GraphTests;
import controller.mainWindow.MainWindowController;
import view.algoInfos.AbstractAlgoInfos;

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

	private JButton calcShortestPath;

	private JButton bFirstStep;
	private JButton bPreviousStep;
	private JButton bPlayPause;
	private JButton bNextStep;
	private JButton bLastStep;

	private AbstractAlgoInfos infosPanel;

	private JLabel warningLabel;

	private JPanel center;
	private JPanel algoChoicePanel;
	private JPanel optionsPanel;
	private JPanel south;

	/*===== BUILDER =====*/
	public MainWindowActionsPanel() {

		setPreferredSize(new Dimension(250, 600));
		setLayout(new BorderLayout());

		center = new JPanel();

		createGap(center);

		//====================== CHOIX DU GRAPHE ======================= 
		JLabel lParams = new JLabel("Paramètres", SwingConstants.CENTER);
		lParams.setPreferredSize(new Dimension(230, 25));
		lParams.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		center.add(lParams);

		// Bouton "choix du graphe"
		JButton bLoadFile = new JButton("Choix du graphe");
		bLoadFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindowController.loadGraph();
				tfGraphFile.setText(MainWindowController.getGraphPath()); 
				algoChoicePanel.setVisible(true);
				updateOnGraphChanges();
			}

		});
		bLoadFile.setPreferredSize(new Dimension(230, 25));
		center.add(bLoadFile);

		// Champ texte avec chemin du graphe
		tfGraphFile = new JTextField();
		tfGraphFile.setPreferredSize(new Dimension(230, 25));
		tfGraphFile.setEditable(false);
		center.add(tfGraphFile);

		createGap(center);
		//=============================================================== 

		//====================== CHOIX DE L'ALGO  ======================= 
		algoChoicePanel = new JPanel();
		algoChoicePanel.setPreferredSize(new Dimension(240, 150));

		JLabel lAlgo = new JLabel("Choix de l'algorithme", SwingConstants.CENTER);
		lAlgo.setPreferredSize(new Dimension(230, 25));
		lAlgo.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		algoChoicePanel.add(lAlgo);

		// Liste des algos
		cbAlgo = new JComboBox<String>();
		cbAlgo.addItem(null);
		cbAlgo.addItem("Dijkstra");
		cbAlgo.addItem("Bellman-Ford");
		cbAlgo.addItem("Voyageur de commerce");
		cbAlgo.addItem("Vertex Color");

		cbAlgo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(cbAlgo.getSelectedItem() == null) {
					MainWindowController.setAlgo(null);
				}
				else {
					switch(cbAlgo.getSelectedItem().toString()) {
					case "Dijkstra" :				MainWindowController.setAlgo(Algorithm.DIJKSTRA);			break;
					case "Bellman-Ford" :			MainWindowController.setAlgo(Algorithm.BELLMAN_FORD);		break;
					case "Voyageur de commerce" :	MainWindowController.setAlgo(Algorithm.VOYAGEUR_COMMERCE);	break;
					case "Vertex Color" :			MainWindowController.setAlgo(Algorithm.VERTEX_COVER);		break;
					}
				}
				updateOnGraphChanges();
				updateInfoField();
				MainWindowController.clearStyle();
			}
		});

		cbAlgo.setPreferredSize(new Dimension(230, 25));
		algoChoicePanel.add(cbAlgo);

		// Infos liées à l'algo
		infosPanel = new AbstractAlgoInfos();
		algoChoicePanel.add(infosPanel);

		center.add(algoChoicePanel);

		algoChoicePanel.setVisible(false);

		createGap(center);
		//=============================================================== 

		//================== INFORMATIONS SUR L'ALGO  =================== 
		optionsPanel = new JPanel();
		optionsPanel.setPreferredSize(new Dimension(240, 150));

		JLabel lOptions = new JLabel("Options", SwingConstants.CENTER);
		lOptions.setPreferredSize(new Dimension(230, 25));
		lOptions.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		optionsPanel.add(lOptions);

		JLabel bStartVertex = new JLabel("Départ :");
		bStartVertex.setPreferredSize(new Dimension(60, 25));
		optionsPanel.add(bStartVertex);

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
				MainWindowController.clearStyle();
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
		optionsPanel.add(tfStartVertex);

		JLabel bEndVertex = new JLabel("Arrivée :");
		bEndVertex.setPreferredSize(new Dimension(60, 25));
		optionsPanel.add(bEndVertex);

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
				MainWindowController.clearStyle();
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
		optionsPanel.add(tfEndVertex);

		center.add(optionsPanel);

		optionsPanel.setVisible(false);
		//=============================================================== 

		//======================== WARNING TEXT =========================
		warningLabel = new JLabel("", SwingConstants.CENTER);
		warningLabel.setPreferredSize(new Dimension(230, 100));
		warningLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 15));
		warningLabel.setForeground(Color.RED);
		center.add(warningLabel);
		//=============================================================== 

		add(center, BorderLayout.CENTER);

		//===================== SIMULATION BUTTONS ======================
		south = new JPanel();
		south.setPreferredSize(new Dimension(240, 160));

		JLabel lSimulation = new JLabel("Simulation", SwingConstants.CENTER);
		lSimulation.setPreferredSize(new Dimension(230, 25));
		lSimulation.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		south.add(lSimulation);

		south.add(new JLabel("Vitesse (iterations par seconde)"));

		// Spinner pour choisir la vitesse (en étapes/s)
		speedSpinnerModel = new SpinnerNumberModel(0.5, 0.1, 5.0, 0.1);		// Défaut=1, Min=0.1, Max=5, Step=0.1
		speedSpinnerModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {						// A chaque changement on met à jour la valeur de speed
				MainWindowController.setSpeed((int)(1000 * (double)speedSpinnerModel.getNumber()));
			}
		});

		JSpinner speedSpinner = new JSpinner(speedSpinnerModel);
		speedSpinner.setPreferredSize(new Dimension(240, 25));
		south.add(speedSpinner);

		createGap(south);

		// Bouton "Calcul du plus court chemin"
		calcShortestPath = new JButton("Calculer le plus court chemin");
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

		south.setVisible(false);
		//=============================================================== 
	}

	/*===== METHODS =====*/

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Calculer le plus court chemin" :		
			MainWindowController.findPCC(); 
			break;
		}

		if(e.getSource() == bFirstStep) 		{ MainWindowController.firstStep(); 	}
		else if(e.getSource() == bPreviousStep) { MainWindowController.previousStep(); 	}
		else if(e.getSource() == bPlayPause) 	{ MainWindowController.playPause(); 	}
		else if(e.getSource() == bNextStep) 	{ MainWindowController.nextStep(); 		}
		else if(e.getSource() == bLastStep) 	{ MainWindowController.lastStep(); 		}

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

	public void updateOnGraphChanges() {
		south.setVisible(false);
		optionsPanel.setVisible(false);
		warningLabel.setText("");

		if(MainWindowController.getAlgo() != null) {
			switch(MainWindowController.getAlgo()) {

			case DIJKSTRA :	
				if(GraphTests.containsNegativeValues(MainWindowController.getGraph())) {
					warningLabel.setText("<html>Erreur : <br/>Le graphe contient des valeurs négatives.<br/>"
							+ "Impossible d'appliquer l'algorithme de Dijkstra</html>");	
				}
				else {
					optionsPanel.setVisible(true);
					south.setVisible(true);
				}
				break;

			case BELLMAN_FORD :	
				optionsPanel.setVisible(true);
				south.setVisible(true);
				break;

			case VOYAGEUR_COMMERCE :
				if(!(GraphTests.isGraphComplete(MainWindowController.getGraph()))) {
					warningLabel.setText("<html>Erreur : <br/>Le graphe n'est pas complet.<br/>"
							+ "Impossible d'appliquer l'algorithme du voyageur de commerce.</html>");
				}
				else {
					south.setVisible(true);
				}
				break;
				
			case VERTEX_COVER :
				south.setVisible(true);
				break;

			default:
				break;
			}
		}
	}

	public void updateInfoField() {
		infosPanel.setText(null);
		if(MainWindowController.getAlgo() != null) {
			switch(MainWindowController.getAlgo()) {
			case DIJKSTRA :	
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes quelconques\n" +
						"- Longueurs positives\n" +
						"- One to All\n" +
						"- Complexité : O(n.log(n))"));
				break;
			case BELLMAN_FORD :	
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes quelconques\n" +
						"- Longueurs quelconques\n" +
						"- One to All\n" +
						"- Complexité : O(n*m)"));
				break;
			case VOYAGEUR_COMMERCE :
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes complets\n" +
						"- Longueurs quelconques\n" +
						"- Complexité : O(beaucoup)"));
				break;
			default:
				break;
			}
		}
	}
}
