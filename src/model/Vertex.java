package model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * Héritage de la classe mxCell, afin de pouvoir différencier les sommets des arcs, et de pouvoir rajouter de nouveaux attributs aux sommets.
 */
public class Vertex extends mxCell {

	/*===== ATTRIBUTES =====*/
	/** Potentiel du sommet **/
	private int potential;
	
	/*===== BUILDERS =====*/
	public Vertex() {
		super();
	}
	
	public Vertex(Object value, mxGeometry geometry, String style) {
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
	}
	
	/*===== GETTERS AND SETTERS =====*/
	public void setPotential(int potential) {
		this.potential = potential;
	}
	
	public int getPotential() {
		return potential;
	}
	
	/**
	 * Utilisée pour récupérer la valeur d'un sommet, si c'est un nombre
	 * @return la valeur du sommet, ou 0 si cette valeur n'est pas un nombre
	 */
	public int getIntValue() {
		if(value.toString().matches("(-)?[0-9]+")) {
			return Integer.parseInt(value.toString());
		}
		return 0;
	}
	
}
