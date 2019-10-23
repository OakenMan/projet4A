package tests;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.view.mxGraph;

public class GraphWithLabel extends mxGraph {

	public GraphWithLabel() {
		super();
	}

	@Override
	public Object createVertex(Object parent, String id, Object value, double x,
			double y, double width, double height, String style,
			boolean relative)
	{
		mxGeometry geometry = new mxGeometry(x, y, width, height);
		geometry.setRelative(relative);

		CellWithLabel vertex = new CellWithLabel(value, geometry, style, null);
		vertex.setId(id);	
		vertex.setVertex(true);
		vertex.setConnectable(true);

		return vertex;
	}
}
