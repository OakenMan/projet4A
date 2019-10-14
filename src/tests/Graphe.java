package tests;

import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
 
public class Graphe extends JFrame {
 
  /** Pour éviter un warning venant du JFrame */
  private static final long serialVersionUID = -8123406571694511514L;
 
  public Graphe() {
    super("JGrapghX tutoriel: Exemple 1");
 
    
    mxGraph graph = new mxGraph();
    graph.setAllowLoops(true);
    graph.setAllowDanglingEdges(false);
    
    graph.setCellsDeletable(false);
    graph.setCellsCloneable(false);
    graph.setCellsEditable(true);
    graph.setCellsMovable(true);
    graph.setCellsResizable(true);
    graph.setCellsSelectable(true);
    
    mxStylesheet styleSheet = graph.getStylesheet();
    
    Hashtable<String, Object> style = new Hashtable<String, Object>();
    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
    style.put(mxConstants.STYLE_FONTSIZE, 15);
    style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    style.put(mxConstants.STYLE_FILLCOLOR, "#9999ff");
    style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
    style.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
    style.put(mxConstants.STYLE_STROKEWIDTH, 3);
    style.put(mxConstants.STYLE_STROKECOLOR, "#6464ff");
    styleSheet.putCellStyle("ROUNDED", style);
    
    Hashtable<String, Object> styleStart = new Hashtable<String, Object>();
    styleStart.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
    styleStart.put(mxConstants.STYLE_FONTSIZE, 15);
    styleStart.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    styleStart.put(mxConstants.STYLE_FILLCOLOR, "#99ff99");
    styleStart.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
    styleStart.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
    styleStart.put(mxConstants.STYLE_STROKEWIDTH, 3);
    styleStart.put(mxConstants.STYLE_STROKECOLOR, "#64ff64");
    styleSheet.putCellStyle("START", styleStart);
    
    Hashtable<String, Object> styleEnd = new Hashtable<String, Object>();
    styleEnd.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
    styleEnd.put(mxConstants.STYLE_FONTSIZE, 15);
    styleEnd.put(mxConstants.STYLE_FONTCOLOR, "#000000");
    styleEnd.put(mxConstants.STYLE_FILLCOLOR, "#ff9999");
    styleEnd.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
    styleEnd.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
    styleEnd.put(mxConstants.STYLE_STROKEWIDTH, 3);
    styleEnd.put(mxConstants.STYLE_STROKECOLOR, "#ff6464");
    styleSheet.putCellStyle("END", styleEnd);
    
    Hashtable<String, Object> defaultEdge = new Hashtable<String, Object>();
    defaultEdge.put(mxConstants.STYLE_LABEL_BACKGROUNDCOLOR, "#ffffff");
    defaultEdge.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
    defaultEdge.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
    defaultEdge.put(mxConstants.STYLE_FONTSIZE, 15);
    defaultEdge.put(mxConstants.STYLE_STROKEWIDTH, 3);
    styleSheet.putCellStyle("DEFAULT", defaultEdge);
    
    Hashtable<String, Object> styleBoldEdge = new Hashtable<String, Object>();
    styleBoldEdge.put(mxConstants.STYLE_FONTSIZE, 15);
    styleBoldEdge.put(mxConstants.STYLE_STROKEWIDTH, 3);
    styleSheet.putCellStyle("BOLD_EDGE", styleBoldEdge);
    
    Object parent = graph.getDefaultParent();
 
    graph.getModel().beginUpdate();
    try {
      Object v1 = graph.insertVertex(parent, null, "1", 20, 20, 30, 30, "ROUNDED");
      Object v2 = graph.insertVertex(parent, null, "2", 20, 200, 30, 30, "ROUNDED");
      Object v3 = graph.insertVertex(parent, null, "3", 200, 20, 30, 30, "ROUNDED");
      Object v4 = graph.insertVertex(parent, null, "4", 200, 200, 30, 30, "ROUNDED");
      Object e1 = graph.insertEdge(parent, null, "12", v1, v2, "DEFAULT");
      Object e2 = graph.insertEdge(parent, null, "8", v3, v1, "DEFAULT");
      Object e3 = graph.insertEdge(parent, null, "3", v1, v4, "DEFAULT");
      Object e4 = graph.insertEdge(parent, null, "14", v3, v4, "DEFAULT");
      Object e5 = graph.insertEdge(parent, null, "6", v4, v2, "DEFAULT");
      
      Object[] selection = {v3};
      graph.setCellStyle("START", selection);
      selection[0] = v2;
      graph.setCellStyle("END", selection);
      Object[] selection2 = {e2, e3, e5};
      graph.setCellStyle("BOLD_EDGE", selection2);
      
      Object[] incoming = graph.getIncomingEdges(v4);
      System.out.println(incoming[0]);
      System.out.println(incoming[1]);
      
    } finally {
      graph.getModel().endUpdate();
    }
 
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    
    getContentPane().add(graphComponent);
  }
 
  /**
   * @param args
   */
  public static void main(String[] args) {
	Graphe frame = new Graphe();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setSize(400, 320);
    frame.setVisible(true);
  }
}
