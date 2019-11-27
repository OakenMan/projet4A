package model;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;

/**
 * Héritage de la classe mxCell, afin de pouvoir différencier les sommets des arcs, et de pouvoir rajouter de nouveaux attributs aux arcs.
 */
public class Edge extends mxCell {

	private int potential;
	
	public Edge(Object value, mxGeometry geometry, String style)
	{
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
		setEdge(true);
		setVertex(false);
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
