package util;

import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

/**
 * Contient tous les styles utilisés par l'application.
 * 
 * Styles communs à plusieurs algorithmes :
 * - DEFAULT 		= style par défaut pour un sommet ou un arc
 * - BOLD_VERTEX	= sommet en gras
 * - BOLD_EDGE		= arc en gras
 * 
 * Pour le problème du plus court chemin :
 * - START			= sommet en vert
 * - BOLD_START		= sommet en vert et en gras
 * - END			= sommet en rouge
 * - BOLD_END		= sommet en rouge et en gras
 * 
 * Pour le problème du voyageur de commerce :
 * - INVISIBLE		= sommet ou arc invisible
 * 
 * Pour le problème du vertex color :
 * - VC_RED, VC_GREEN, VC_BLUE, VC_MAGENTA, VC_YELLOW, VC_CYAN = sommets colorés
 * 
 */
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

		// Style invisible pour un arc ou un sommet : INVISIBLE
		Hashtable<String, Object> styleInvisible = new Hashtable<String, Object>();
		styleInvisible.put(mxConstants.STYLE_NOLABEL, true);
		styleInvisible.put(mxConstants.STYLE_OPACITY, 0);
		putCellStyle("INVISIBLE", styleInvisible);

		// Styles pour le Vertex Color
		Hashtable<String, Object> vcRed = new Hashtable<String, Object>();
		vcRed.put(mxConstants.STYLE_FILLCOLOR, "#ff0000");
		vcRed.put(mxConstants.STYLE_STROKECOLOR, "#ff0000");
		putCellStyle("VC_RED", vcRed);
		
		Hashtable<String, Object> vcGreen = new Hashtable<String, Object>();
		vcGreen.put(mxConstants.STYLE_FILLCOLOR, "#00ff00");
		vcGreen.put(mxConstants.STYLE_STROKECOLOR, "#00ff00");
		putCellStyle("VC_GREEN", vcGreen);
		
		Hashtable<String, Object> vcBlue = new Hashtable<String, Object>();
		vcBlue.put(mxConstants.STYLE_FILLCOLOR, "#0000ff");
		vcBlue.put(mxConstants.STYLE_STROKECOLOR, "#0000ff");
		putCellStyle("VC_BLUE", vcBlue);
		
		Hashtable<String, Object> vcMagenta = new Hashtable<String, Object>();
		vcMagenta.put(mxConstants.STYLE_FILLCOLOR, "#ff00ff");
		vcMagenta.put(mxConstants.STYLE_STROKECOLOR, "#ff00ff");
		putCellStyle("VC_MAGENTA", vcMagenta);
		
		Hashtable<String, Object> vcYellow = new Hashtable<String, Object>();
		vcYellow.put(mxConstants.STYLE_FILLCOLOR, "#ffff00");
		vcYellow.put(mxConstants.STYLE_STROKECOLOR, "#ffff00");
		putCellStyle("VC_YELLOW", vcYellow);
		
		Hashtable<String, Object> vcCyan = new Hashtable<String, Object>();
		vcCyan.put(mxConstants.STYLE_FILLCOLOR, "#00ffff");
		vcCyan.put(mxConstants.STYLE_STROKECOLOR, "#00ffff");
		putCellStyle("VC_CYAN", vcCyan);

		setDefaultVertexStyle(defaultVertex);
		setDefaultEdgeStyle(defaultEdge);
	}
}
