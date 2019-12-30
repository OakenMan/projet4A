package view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JViewport;

import com.mxgraph.swing.mxGraphComponent;

import model.Graph;

/**
 * Panel d'affichage de graphe
 */
public class GraphDisplayPanel extends JPanel {

	/*===== ATTRIBUTES =====*/
	private JLayeredPane lpane;					/** Panel qui permet d'afficher le graphe + les potentiels  **/
	private mxGraphComponent graphComponent;	/** Composant d'affichage du graphe							**/
	private JPanel potentialsPane;				/** Panel d'affichage des potentiels						**/

	/*===== BUILDER =====*/
	public GraphDisplayPanel(Graph graph) {

		setLayout(new BorderLayout());
		
		// LAYERED PANE
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 1920, 1080);
		add(lpane, BorderLayout.CENTER);

		// GRAPH PANE (en fond)
		graphComponent = new mxGraphComponent(graph);
		graphComponent.getViewport().setOpaque(true);
		graphComponent.getViewport().setBackground(Color.WHITE);
		graphComponent.setBounds(0, 0, 1920, 1080);
		lpane.add(graphComponent, 0, 0);

		// POTENTIALS PANE (devant en transparence)
		potentialsPane = new JPanel();
		potentialsPane.setLayout(null);
		potentialsPane.setOpaque(false);
		potentialsPane.setBounds(0, 0, 1920, 1080);
		lpane.add(potentialsPane, 1, 0);

		setVisible(true);
	}

	/*===== GETTERS AND SETTERS =====*/
	public JPanel getPotentialsPane() {
		return potentialsPane;
	}

	public JViewport getGraphPane() {
		return graphComponent.getViewport();
	}

	public mxGraphComponent getGraphComponent() {
		return graphComponent;
	}

	/*===== METHODS =====*/

	/**
	 * Permet de mettre à jour le graphe
	 * @param graph le nouveau graphe à afficher
	 */
	public void setGraph(Graph graph) {
		graphComponent.setGraph(graph);
		graphComponent.refresh();
	}

}
