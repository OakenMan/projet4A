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
		mxCell startCell = null;
		mxCell endCell = null;
		mxCell currentCell;
		
		// On récupère le sommet de départ et d'arrivée
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		for (Object c : vertices)
		{
			mxCell cell = (mxCell) c;
			
			if((int)cell.getValue() == MainWindowController.getStart()) {
				startCell = cell;
				graph.getModel().setStyle(startCell, "BOLD_START");
				System.out.println("Départ = "+startCell.getValue().toString());
			}
			else if((int)cell.getValue() == MainWindowController.getEnd()) {
				endCell = cell;
				System.out.println("Arrivée = "+endCell.getValue().toString());
			}
			
		}
		// On ajoute l'étape 0 (juste le départ en gras)
		steps.add(copy(graph));
		System.out.println("Etape "+currentStep+" OK");
		
		nbSteps++;
		currentStep++;
		
		currentCell = startCell;
		
		// Tant qu'on a pas atteind l'arrivée
		while(!currentCell.equals(endCell)) {
			
			vertices = graph.getOutgoingEdges(currentCell);			// On récupère la liste des arcs partant de currentCell
			
			mxCell minPath = getShortestEdge(vertices);				// On récupère l'arc au plus petit poids
			
			graph.getModel().setStyle(minPath, "BOLD_EDGE");		// On change son style
			
			currentCell = (mxCell) minPath.getTarget();				// On récupère le sommet au bout de cet arc
			
			if(!currentCell.equals(endCell)) {							// Si ce sommet n'est pas l'arrivée
				potentials.put(currentCell, minPath.getValue().toString());
				graph.getModel().setStyle(currentCell, "BOLD_VERTEX");	// On change son style
				nbSteps++;												// On augmente le nombre d'étapes
				currentStep++;											// et l'étape courante
			}
			else {														// Sinon (si c'est l'arrivée)
				potentials.put(currentCell, minPath.getValue().toString());
				graph.getModel().setStyle(currentCell, "BOLD_END");		// On change son style
			}
			
			steps.add(copy(graph));									// Enfin on ajoute cette nouvelle étape au tableau
		}
	}
	
	public static mxCell getShortestEdge(Object[] cells) {
		mxCell minEdge = (mxCell)cells[0];
		for(Object c : cells) {
			mxCell cell = (mxCell) c;
			if(Integer.parseInt((String)cell.getValue()) < Integer.parseInt((String)minEdge.getValue())) {
				minEdge = cell;
			}
		}
		return minEdge;
	}
	
	public void displayPotentials() {
		JPanel potPane = MainWindowController.getView().getGraphPanel().getPotPane();
//		JViewport potPane = MainWindowController.getView().getGraphPanel().getGraphPane();
		
		for(Map.Entry<mxCell, String> mapentry : potentials.entrySet()) {
			JLabel potential = new JLabel(mapentry.getValue());
			
			int x = (int)mapentry.getKey().getGeometry().getX();
			int y = (int)mapentry.getKey().getGeometry().getY()-30;
			
			System.out.println("Sommet " + mapentry.getKey().getValue() + " ---> " + mapentry.getValue() + "("+x+","+y+")");
			
			potential.setBounds(new Rectangle(x, y, 50, 50));
			potential.setBackground(Color.WHITE);
			potPane.add(potential);
			
			potPane.validate();
			
		}
	}

}
