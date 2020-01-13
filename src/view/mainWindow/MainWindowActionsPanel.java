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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import algorithms.Algorithm;
import algorithms.GraphTests;
import controller.mainWindow.MainWindowController;

/**
 * Panel d'actions pour la simulation d'algorithmes
 */
public class MainWindowActionsPanel extends JPanel {

	/*===== ICONS PATHS =====*/
	private static final URL rewindIcon = MainWindowActionsPanel.class.getResource("/icons/rewind.png");
	private static final URL backIcon = MainWindowActionsPanel.class.getResource("/icons/back.png");
	private static final URL playIcon = MainWindowActionsPanel.class.getResource("/icons/play.png");
	private static final URL pauseIcon = MainWindowActionsPanel.class.getResource("/icons/pause.png");
	private static final URL nextIcon = MainWindowActionsPanel.class.getResource("/icons/next.png");
	private static final URL fastForwardIcon = MainWindowActionsPanel.class.getResource("/icons/fast-forward.png");

	/*===== ATTRIBUTES =====*/
	/** Champ texte pour le choix du graphe **/
	private JTextField tfGraphFile;

	/** ComboBox de sélection d'algorithme **/
	private JComboBox<String> cbAlgo;

	/** Champ texte pour le choix du sommet de départ (PCC)**/
	private JTextField tfStartVertex;
	/** Champ texte pour le choix du sommet d'arrivée (PCC)**/
	private JTextField tfEndVertex;

	/** Spinner pour le choix de la vitesse **/
	private SpinnerNumberModel speedSpinnerModel;
	/** Spinner pour le choix du nombre d'essais (TSP) **/
	private SpinnerNumberModel triesSpinnerModel;
	/** Spinner pour le choix du nombre d'échanges (TSP) **/
	private SpinnerNumberModel exchangesSpinnerModel;

	/** Bouton pour lancer l'algorithme **/
	private JButton calcShortestPath;
	/** Bouton play/pause **/
	private JButton bPlayPause;

	/** Zone de texte pour afficher des infos relatives aux algorithmes **/
	private JTextArea infosPanel;
	/** Zone de texte pour afficher des erreurs **/
	private JLabel warningLabel;

	/** Panel central (choix des paramètres) **/
	private JPanel center;
	/** Panel de choix de l'algo **/
	private JPanel algoChoicePanel;
	/** Panel pour les paramètres des TSP **/
	private JPanel paramsTsp;
	/** Panel pour les paramètres de PCC **/
	private JPanel paramsPcc;
	/** Panel pour la simulation **/
	private JPanel south;

	/*===== BUILDER =====*/
	/**
	 * Construit le panel d'actions de la MainWindow.
	 */
	public MainWindowActionsPanel() {

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250, 600));

		center = new JPanel();

		createGap(center);

		//====================== CHOIX DU GRAPHE ======================= 
		// Label "Paramètres"
		JLabel lParams = new JLabel("Paramètres", SwingConstants.CENTER);
		lParams.setPreferredSize(new Dimension(230, 25));
		lParams.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		center.add(lParams);

		// Bouton "Choix du graphe"
		JButton bLoadFile = new JButton("Choix du graphe");
		bLoadFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindowController.loadGraph();							// On charge le graphe
				tfGraphFile.setText(MainWindowController.getGraphPath()); 	// On met à jour le chemin
				algoChoicePanel.setVisible(true);							// On affiche le panel de choix d'algo
				updateOnGraphChanges();										// On met à jour les infos en fonction du graphe
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

		// Label "Choix de l'algorithme"
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

		// ActionListener qui actualise les informations en fonction de l'algo choisi
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
					case "Vertex Color" :			MainWindowController.setAlgo(Algorithm.VERTEX_COLOR);		break;
					}
				}
				updateOnGraphChanges();
				updateInfoField();
				MainWindowController.clearStyle();
			}
		});

		cbAlgo.setPreferredSize(new Dimension(230, 25));
		algoChoicePanel.add(cbAlgo);

		// Infos techniques liées à l'algo
		infosPanel = new JTextArea();
		infosPanel.setPreferredSize(new Dimension(230, 100));
		infosPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		algoChoicePanel.add(infosPanel);

		center.add(algoChoicePanel);

		algoChoicePanel.setVisible(false);

		createGap(center);
		//=============================================================== 

		//================== OPTIONS PLUS COURT CHEMIN ================== 
		paramsPcc = new JPanel();
		paramsPcc.setPreferredSize(new Dimension(240, 150));

		// Label "Options PCC"
		JLabel lOptionsPCC = new JLabel("Options PCC", SwingConstants.CENTER);
		lOptionsPCC.setPreferredSize(new Dimension(230, 25));
		lOptionsPCC.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		paramsPcc.add(lOptionsPCC);

		// Label "Départ"
		JLabel bStartVertex = new JLabel("Départ :");
		bStartVertex.setPreferredSize(new Dimension(60, 25));
		paramsPcc.add(bStartVertex);

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
				if(tfStartVertex.getText().matches("[0-9]+")) {												// Si c'est bien un nombre
					if (MainWindowController.containsVertex(Integer.parseInt(tfStartVertex.getText())) &&	// Si le sommet est bien dans le graphe
							Integer.parseInt(tfStartVertex.getText()) != MainWindowController.getEnd()) {	// Et si c'est pas déjà l'arrivée

						tfStartVertex.setBackground(Color.GREEN);
						MainWindowController.setStart(Integer.parseInt(tfStartVertex.getText()));			// Alors on modifie le départ
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
		paramsPcc.add(tfStartVertex);

		// Label "Arrivée"
		JLabel bEndVertex = new JLabel("Arrivée :");
		bEndVertex.setPreferredSize(new Dimension(60, 25));
		paramsPcc.add(bEndVertex);

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
				if(tfEndVertex.getText().matches("[0-9]+")) {												// Si c'est bien un nombre
					if (MainWindowController.containsVertex(Integer.parseInt(tfEndVertex.getText())) &&		// Si le sommet est bien dans le graphe
							Integer.parseInt(tfEndVertex.getText()) != MainWindowController.getStart()) {	// Et si c'est pas déjà le départ

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
		});
		paramsPcc.add(tfEndVertex);

		center.add(paramsPcc);

		paramsPcc.setVisible(false);
		//=============================================================== 

		//======================== WARNING TEXT =========================
		warningLabel = new JLabel("", SwingConstants.CENTER);
		warningLabel.setPreferredSize(new Dimension(230, 100));
		warningLabel.setFont(new java.awt.Font("serif", Font.PLAIN, 15));
		warningLabel.setForeground(Color.RED);
		center.add(warningLabel);
		//=============================================================== 

		//======================= PARAMETRES TSP ========================
		paramsTsp = new JPanel();
		paramsTsp.setPreferredSize(new Dimension(240, 250));

		// Label "Options TSP"
		JLabel lOptionsTSP = new JLabel("Options TSP", SwingConstants.CENTER);
		lOptionsTSP.setPreferredSize(new Dimension(230, 25));
		lOptionsTSP.setFont(new java.awt.Font("serif", Font.PLAIN, 20));
		paramsPcc.add(lOptionsTSP);

		// Label "Choix de la solution de base"
		JLabel lSolutionBase = new JLabel("Choix de la solution de base :", SwingConstants.CENTER);
		lSolutionBase.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(lSolutionBase);

		// ComboBox des solutions de base
		JComboBox<String> cbSolutionBase = new JComboBox<String>();
		cbSolutionBase.addItem("Shortest Neighboor (best)");
		cbSolutionBase.addItem("Shortest Neighboor (random)");
		cbSolutionBase.addItem("Random");
		cbSolutionBase.setPreferredSize(new Dimension(230, 25));
		cbSolutionBase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindowController.setBaseSolution(cbSolutionBase.getSelectedItem().toString());
			}
		});
		MainWindowController.setBaseSolution("Shortest Neighboor (best)");
		paramsTsp.add(cbSolutionBase);

		// Label "Choix de l'heurtistique"
		JLabel lHeurtistique = new JLabel("Choix de l'heurtistique :", SwingConstants.CENTER);
		lHeurtistique.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(lHeurtistique);

		// ComboBox des heurtistiques
		JComboBox<String> cbHeurtistique = new JComboBox<String>();
		cbHeurtistique.addItem("Echanges d'arcs aléatoires");
		cbHeurtistique.addItem("Tous les échanges possibles");
		cbHeurtistique.setPreferredSize(new Dimension(230, 25));
		cbHeurtistique.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindowController.setHeurtistique(cbHeurtistique.getSelectedItem().toString());
			}
		});
		MainWindowController.setHeurtistique("Echanges d'arcs aléatoires");
		paramsTsp.add(cbHeurtistique);

		// Label "Nbr. d'essais d'améliorations"
		JLabel lTries = new JLabel("Nbr. d'essais d'améliorations :", SwingConstants.CENTER);
		lTries.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(lTries);

		// Spinner essais d'améliorations
		triesSpinnerModel = new SpinnerNumberModel(20, 1, null, 1);
		triesSpinnerModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				MainWindowController.setNbTries((int)triesSpinnerModel.getValue());
			}
		});
		MainWindowController.setNbTries(20);
		JSpinner triesSpinner = new JSpinner(triesSpinnerModel);
		triesSpinner.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(triesSpinner);

		// Label "Nbr. d'échanges par essai"
		JLabel lIterations = new JLabel("Nbr. d'échanges par essai :", SwingConstants.CENTER);
		lIterations.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(lIterations);

		// Spinner échanges par essai
		exchangesSpinnerModel = new SpinnerNumberModel(150, 50, null, 50);
		exchangesSpinnerModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				MainWindowController.setNbExchanges((int)exchangesSpinnerModel.getValue());
			}
		});
		MainWindowController.setNbExchanges(150);
		JSpinner exchangesSpinner = new JSpinner(exchangesSpinnerModel);
		exchangesSpinner.setPreferredSize(new Dimension(230, 25));
		paramsTsp.add(exchangesSpinner);

		center.add(paramsTsp);

		//=============================================================== 

		add(center, BorderLayout.CENTER);

		//===================== SIMULATION BUTTONS ======================
		south = new JPanel();
		south.setPreferredSize(new Dimension(240, 170));

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
		speedSpinner.setPreferredSize(new Dimension(230, 25));
		south.add(speedSpinner);

		createGap(south);

		// Bouton "Executer l'algorithme"
		calcShortestPath = new JButton("Executer l'algorithme");
		calcShortestPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainWindowController.executeAlgorithm(); 
			}	
		});
		calcShortestPath.setPreferredSize(new Dimension(230, 25));
		south.add(calcShortestPath);

		// Bouton "première étape"
		JButton bFirstStep = new JButton();
		bFirstStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { MainWindowController.firstStep(); }	
		});
		bFirstStep.setPreferredSize(new Dimension(42, 25));
		bFirstStep.setIcon(new ImageIcon(rewindIcon));
		south.add(bFirstStep);

		// Bouton "étape précédente"
		JButton bPreviousStep = new JButton();
		bPreviousStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { MainWindowController.previousStep(); }	
		});
		bPreviousStep.setPreferredSize(new Dimension(42, 25));
		bPreviousStep.setIcon(new ImageIcon(backIcon));
		south.add(bPreviousStep);

		// Bouton "play/pause"
		bPlayPause = new JButton();
		bPlayPause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { MainWindowController.playPause(); }	
		});
		bPlayPause.setPreferredSize(new Dimension(42, 25));
		bPlayPause.setIcon(new ImageIcon(playIcon));
		south.add(bPlayPause);

		// Bouton "étape suivante"
		JButton bNextStep = new JButton();
		bNextStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { MainWindowController.nextStep(); }	
		});
		bNextStep.setPreferredSize(new Dimension(42, 25));
		bNextStep.setIcon(new ImageIcon(nextIcon));
		south.add(bNextStep);

		// Bouton "dernière étape"
		JButton bLastStep = new JButton();
		bLastStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { MainWindowController.lastStep(); }	
		});
		bLastStep.setPreferredSize(new Dimension(42, 25));
		bLastStep.setIcon(new ImageIcon(fastForwardIcon));
		south.add(bLastStep);

		createGap(south);

		add(south, BorderLayout.SOUTH);

		south.setVisible(false);
		paramsTsp.setVisible(false);
		//=============================================================== 
	}

	/*===== METHODS =====*/

	/**
	 * Reset les paramètres (départ et arrivée)
	 */
	public void resetParameters() {
		tfStartVertex.setText("");
		tfStartVertex.setBackground(null);
		tfEndVertex.setText("");
		tfEndVertex.setBackground(null);
	}

	/**
	 * À chaque changement d'algorithme ou de graphe, cette méthode est appelée pour met à jour
	 * certains informations conçernant ces graphes et ces algorithmes.
	 */
	public void updateOnGraphChanges() {
		// Reset tous les champs et les panels
		south.setVisible(false);
		paramsPcc.setVisible(false);
		paramsTsp.setVisible(false);
		warningLabel.setText("");

		// Si un algo a été choisi...
		if(MainWindowController.getAlgo() != null) {
			switch(MainWindowController.getAlgo()) {

			case DIJKSTRA :	
				// Dijkstra : impossible si le graphe contient des valeurs négatives
				if(GraphTests.containsNegativeValues(MainWindowController.getGraph())) {
					warningLabel.setText("<html>Erreur : <br/>Le graphe contient des valeurs négatives.<br/>"
							+ "Impossible d'appliquer l'algorithme de Dijkstra</html>");	
				}
				else {
					paramsPcc.setVisible(true);	// Affiche les options pour les algos de PCC
					south.setVisible(true);
				}
				break;

			case BELLMAN_FORD :	
				// Bellman-Ford : peut être exécuté dans tous les cas
				paramsPcc.setVisible(true);
				south.setVisible(true);
				break;

			case VOYAGEUR_COMMERCE :
				// Voyageur de commerce : nécessite un graphe complet
				if(!(GraphTests.isGraphComplete(MainWindowController.getGraph()))) {
					warningLabel.setText("<html>Erreur : <br/>Le graphe n'est pas complet.<br/>"
							+ "Impossible d'appliquer l'algorithme du voyageur de commerce.</html>");
				}
				else {
					paramsTsp.setVisible(true);		// Affiche les options pour TSP
					south.setVisible(true);
				}
				break;

			case VERTEX_COLOR :
				// Vertex Color : peut être exécuté dans tous les cas
				south.setVisible(true);
				break;

			default:
				break;
			}
		}
	}

	/**
	 * Met à jour le texte informatif en fonction de l'algorithme choisi
	 */
	public void updateInfoField() {
		infosPanel.setText(null);
		if(MainWindowController.getAlgo() != null) {
			switch(MainWindowController.getAlgo()) {
			case DIJKSTRA :	
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes quelconques\n" +
						"- Longueurs positives\n" +
						"- One to All\n"));
				break;
			case BELLMAN_FORD :	
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes quelconques\n" +
						"- Longueurs quelconques\n" +
						"- One to All\n"));
				break;
			case VOYAGEUR_COMMERCE :
				infosPanel.append(("Caractéristiques :\n" +
						"- Graphes complets\n" +
						"- Longueurs quelconques\n"));
				break;
			case VERTEX_COLOR :
				infosPanel.append(("Caractéristiques :\n" +
						"..."));
				break;
			default:
				break;
			}
		}
	}
	
	/**
	 * Change l'état de certains boutons si on joue la simulation de l'algorithme
	 * @param isPlaying vaut true si on a appuyé sur le bouton "Play", false sinon
	 */
	public void updateIfPlaying(boolean isPlaying) {
		if(isPlaying) {
			bPlayPause.setIcon(new ImageIcon(pauseIcon));
		}
		else {
			bPlayPause.setIcon(new ImageIcon(playIcon));
		}
	}
	
	private void createGap(JPanel panel) {
		JLabel separator = new JLabel();
		separator.setPreferredSize(new Dimension(230, 10));
		panel.add(separator);
	}

}
