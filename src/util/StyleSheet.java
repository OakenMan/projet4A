package util;

import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

public class StyleSheet extends mxStylesheet {

	public StyleSheet() {

		// Style défaut pour un sommet : DEFAULT
		Hashtable<String, Object> defaultVertex = new Hashtable<String, Object>();
		defaultVertex.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		defaultVertex.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
		defaultVertex.put(mxConstants.STYLE_FONTSIZE, 15);
		defaultVertex.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		defaultVertex.put(mxConstants.STYLE_FILLCOLOR, "#C3D9FF");
		defaultVertex.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		defaultVertex.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		defaultVertex.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE);
		defaultVertex.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
		defaultVertex.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
		putCellStyle("DEFAULT", defaultVertex);

		// Style  gras pour un sommet : BOLD_VERTEX
		Hashtable<String, Object> boldVertex = new Hashtable<String, Object>();
		boldVertex.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("BOLD_VERTEX", boldVertex);

		// Style départ pour un sommet : START
		Hashtable<String, Object> styleStart = new Hashtable<String, Object>();
		styleStart.put(mxConstants.STYLE_FILLCOLOR, "#99ff99");
		styleStart.put(mxConstants.STYLE_STROKECOLOR, "#64ff64");
		putCellStyle("START", styleStart);

		// Style départ gras pour un sommet : BOLD_START
		Hashtable<String, Object> boldStart = new Hashtable<String, Object>();
		boldStart.put(mxConstants.STYLE_FILLCOLOR, "#99ff99");
		boldStart.put(mxConstants.STYLE_STROKECOLOR, "#64ff64");
		boldStart.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("BOLD_START", boldStart);

		// Style arrivée pour un sommet : END
		Hashtable<String, Object> styleEnd = new Hashtable<String, Object>();
		styleEnd.put(mxConstants.STYLE_FILLCOLOR, "#ff9999");
		styleEnd.put(mxConstants.STYLE_STROKECOLOR, "#ff6464");
		putCellStyle("END", styleEnd);

		// Style arrivée gras pour un sommet : BOLD_END
		Hashtable<String, Object> boldEnd = new Hashtable<String, Object>();
		boldEnd.put(mxConstants.STYLE_FILLCOLOR, "#ff9999");
		boldEnd.put(mxConstants.STYLE_STROKECOLOR, "#ff6464");
		boldEnd.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("BOLD_END", boldEnd);

		// Style défaut pour un arc : DEFAULT
		Hashtable<String, Object> defaultEdge = new Hashtable<String, Object>();
		defaultEdge.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
		defaultEdge.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
		defaultEdge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		defaultEdge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		defaultEdge.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
		defaultEdge.put(mxConstants.STYLE_FONTSIZE, 15);
		defaultEdge.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
		defaultEdge.put(mxConstants.STYLE_FONTCOLOR, "#446299");
		putCellStyle("DEFAULT", defaultEdge);

		// Style gras pour un arc : BOLD_EDGE
		Hashtable<String, Object> styleBoldEdge = new Hashtable<String, Object>();
		styleBoldEdge.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("BOLD_EDGE", styleBoldEdge);

		// Style gras pour un arc : INVISIBLE
		Hashtable<String, Object> styleInvisible = new Hashtable<String, Object>();
		styleInvisible.put(mxConstants.STYLE_NOLABEL, true);
		styleInvisible.put(mxConstants.STYLE_OPACITY, 0);
		putCellStyle("INVISIBLE", styleInvisible);

		setDefaultVertexStyle(defaultVertex);
		setDefaultEdgeStyle(defaultEdge);
	}
}
