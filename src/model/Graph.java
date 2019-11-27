package model;

import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

/**
 * Héritage de la classe mxGraph, pour implémenter de nouvelles méthodes aux graphes
 */
public class Graph extends mxGraph {

	public Graph() {
		super();
	}

	/**
	 * Surchage la fonction createVertex de mxGraph afin de créer un Vertex au lieu d'un mxCell
	 */
	@Override
	public Object createVertex(Object parent, String id, Object value, double x,
			double y, double width, double height, String style,
			boolean relative) 
	{
		mxGeometry geometry = new mxGeometry(x, y, width, height);
		geometry.setRelative(relative);

		Vertex vertex = new Vertex(value, geometry, style);
		vertex.setId(id);
		vertex.setVertex(true);
		vertex.setConnectable(true);

		System.out.println("On créé un vertex avec potentiel = "+vertex.getPotential());

		return vertex;
	}

	/**
	 * Surchage la fonction createEdge de mxGraph afin de créer un Edge au lieu d'un mxCell
	 */
	@Override
	public Object createEdge(Object parent, String id, Object value,
			Object source, Object target, String style)
	{
		Edge edge = new Edge(value, new mxGeometry(), style);
		edge.setId(id);
		edge.setEdge(true);
		edge.getGeometry().setRelative(true);

		System.out.println("On créé un edge avec potentiel = "+edge.getPotential());

		return edge;
	}

	//---------------------------------------------

}
