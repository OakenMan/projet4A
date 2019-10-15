package algorithms;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controller.mainWindow.MainWindowController;

public class AlgoTest extends AbstractShortestPath implements ShortestPath {

	public AlgoTest(mxGraph graph) {
		super(graph);
	}

	@Override
	public void findShortestPath() {
		mxCell startCell;
		mxCell endCell;
		
		Object[] cells = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : cells)
		{
			mxCell cell = (mxCell) c;
			if((int)cell.getValue() == MainWindowController.getStart()) {
				graph.getModel().setStyle(cell, "START");
				startCell = cell;
			}
			else if((int)cell.getValue() == MainWindowController.getStart()) {
				graph.getModel().setStyle(cell, "END");
				endCell = cell;
			}
			else {
				graph.getModel().setStyle(cell, "ROUNDED");
			}
		}
	}

}
