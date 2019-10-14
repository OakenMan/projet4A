package util;

import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

public class StyleSheet extends mxStylesheet {

	public StyleSheet() {

		Hashtable<String, Object> style = new Hashtable<String, Object>();
		style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		style.put(mxConstants.STYLE_FONTSIZE, 15);
		style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		style.put(mxConstants.STYLE_FILLCOLOR, "#9999ff");
		style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		style.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
		style.put(mxConstants.STYLE_STROKEWIDTH, 3);
		style.put(mxConstants.STYLE_STROKECOLOR, "#6464ff");
		putCellStyle("ROUNDED", style);

		Hashtable<String, Object> styleStart = new Hashtable<String, Object>();
		styleStart.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		styleStart.put(mxConstants.STYLE_FONTSIZE, 15);
		styleStart.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		styleStart.put(mxConstants.STYLE_FILLCOLOR, "#99ff99");
		styleStart.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		styleStart.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
		styleStart.put(mxConstants.STYLE_STROKEWIDTH, 3);
		styleStart.put(mxConstants.STYLE_STROKECOLOR, "#64ff64");
		putCellStyle("START", styleStart);

		Hashtable<String, Object> styleEnd = new Hashtable<String, Object>();
		styleEnd.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		styleEnd.put(mxConstants.STYLE_FONTSIZE, 15);
		styleEnd.put(mxConstants.STYLE_FONTCOLOR, "#000000");
		styleEnd.put(mxConstants.STYLE_FILLCOLOR, "#ff9999");
		styleEnd.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
		styleEnd.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
		styleEnd.put(mxConstants.STYLE_STROKEWIDTH, 3);
		styleEnd.put(mxConstants.STYLE_STROKECOLOR, "#ff6464");
		putCellStyle("END", styleEnd);

		Hashtable<String, Object> defaultEdge = new Hashtable<String, Object>();
		defaultEdge.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
		defaultEdge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
		defaultEdge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
		defaultEdge.put(mxConstants.STYLE_FONTSIZE, 30);
		defaultEdge.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("DEFAULT", defaultEdge);

		Hashtable<String, Object> styleBoldEdge = new Hashtable<String, Object>();
		styleBoldEdge.put(mxConstants.STYLE_FONTSIZE, 15);
		styleBoldEdge.put(mxConstants.STYLE_STROKEWIDTH, 3);
		putCellStyle("BOLD_EDGE", styleBoldEdge);
	}
}
