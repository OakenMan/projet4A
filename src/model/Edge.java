package model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * Héritage de la classe mxCell, afin de pouvoir différencier les sommets des arcs, et de pouvoir rajouter de nouveaux attributs aux arcs.
 */
public class Edge extends mxCell {

	/*===== ATTRIBUTES =====*/
	/** Potentiel de l'arc (non-utilisé dans les algorithmes actuellement implémentés) **/
	private int potential;

	/*===== BUILDER =====*/
	public Edge(Object value, mxGeometry geometry, String style) {
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
		setEdge(true);
		setVertex(false);
	}

	/*===== GETTERS AND SETTERS =====*/
	public void setPotential(int potential) {
		this.potential = potential;
	}

	public int getPotential() {
		return potential;
	}

	/**
	 * Utilisée pour récupérer le poids d'un arc, si c'est un nombre
	 * @return le poids de l'arc, ou 0 si cette valeur n'est pas un nombre
	 */
	public int getIntValue() {
		if(value.toString().matches("(-)?[0-9]+")) {
			return Integer.parseInt(value.toString());
		}
		return 0;
	}
}
