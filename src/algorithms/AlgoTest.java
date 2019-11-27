package algorithms;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import controller.mainWindow.MainWindowController;

public class AlgoTest extends AbstractShortestPath {

	/*===== BUILDER =====*/
	public AlgoTest(mxGraph graph) {
		super(graph);	
	}

	/*===== METHODS =====*/
	@Override
	public void findShortestPath() {
		mxCell startCell = getBeginning();
		mxCell endCell = getEnd();
		mxCell currentCell;

		Object[] edges = null;
		
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");
		
		currentStep++;
		
		currentCell = startCell;
		
		// Tant qu'on a pas atteint l'arrivée
		while(!currentCell.equals(endCell)) {
			
			edges = graph.getOutgoingEdges(currentCell);					// On récupère la liste des arcs partant de currentCell
			
			mxCell minPath = getShortestEdge(edges);						// On récupère l'arc au plus petit poids
			
			graph.getModel().setStyle(minPath, "BOLD_EDGE");				// On change son style
			
			currentCell = (mxCell) minPath.getTarget();						// On récupère le sommet au bout de cet arc
			
			if(!currentCell.equals(endCell)) {								// Si ce sommet n'est pas l'arrivée
				potentials.put(currentCell, minPath.getValue().toString());
				graph.getModel().setStyle(currentCell, "BOLD_VERTEX");		// On change son style
				currentStep++;												// et l'étape courante
			}
			else {															// Sinon (si c'est l'arrivée)
				potentials.put(currentCell, minPath.getValue().toString());
				graph.getModel().setStyle(currentCell, "BOLD_END");			// On change son style
			}
			
			steps.add(copy(graph));											// Enfin on ajoute cette nouvelle étape au tableau
		}
	}
	
	public static mxCell getShortestEdge(Object[] edges) {
		mxCell minEdge = (mxCell)edges[0];
		for(Object e : edges) {
			mxCell edge = (mxCell) e;
			if(AbstractShortestPath.getValue(edge) < AbstractShortestPath.getValue(minEdge)) {
				minEdge = edge;
			}
		}
		return minEdge;
	}

}
