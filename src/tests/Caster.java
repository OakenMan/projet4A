package tests;

import com.mxgraph.model.mxICell;

import model.Edge;
import model.Vertex;

public class Caster {

	public static Vertex castToVertex(mxICell cell) {
		Vertex v = new Vertex(cell.getValue(), cell.getGeometry(), cell.getStyle());
		return v;
	}
	
	public static Edge castToEdge(mxICell cell) {
		Edge e = new Edge(cell.getValue(), cell.getGeometry(), cell.getStyle());
		return e;
	}
	
	public static mxICell getChildAt(Object parent, int index) {
		return (parent instanceof mxICell) ? ((mxICell)parent).getChildAt(index) : null;
	}
	
	public static Vertex getVertexChildAt(Object parent, int index)
	{
		return (parent instanceof mxICell) ? Caster.castToVertex(((mxICell)parent).getChildAt(index)) : null;
	}
	
	public static Edge getEdgeChildAt(Object parent, int index)
	{
		return (parent instanceof mxICell) ? Caster.castToEdge(((mxICell)parent).getChildAt(index)) : null;
	}
}
