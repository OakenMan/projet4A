package model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * Héritage de la classe mxCell, afin de pouvoir différencier les sommets des arcs, et de pouvoir rajouter de nouveaux attributs aux sommets.
 */
public class Vertex extends mxCell {

	private int potential;
	
	public Vertex(Object value, mxGeometry geometry, String style)
	{
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
	}
	
	public void setPotential(int potential) {
		this.potential = potential;
	}
	
	public int getPotential() {
		return potential;
	}
	
	public int getIntValue()
	{
		return Integer.parseInt(value.toString());
	}
	
}
