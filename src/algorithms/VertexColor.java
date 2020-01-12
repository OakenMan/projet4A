package algorithms;

import java.util.HashMap;
import java.util.Map.Entry;

import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe est la classe permettant de gérer l'affichage et la résolution de l'algorithme de coloration de graphe
 * @author Aymeric Le Moal
 * @author Tom Suchel
 */
public class VertexColor extends AbstractAlgorithm {

	HashMap<Vertex, Integer> map;

	public VertexColor(Graph graph) {
		super(graph);	
	}

	/**
	 * Fonction qui execute l'algorithme de coloration de graphe. Elle fait appel aux fonctions de la classe, est elle appelée dans le constructeur
	 */
	@Override
	public void executeAlgorithm() {

		map = new HashMap<Vertex, Integer>();
		
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());	

		// On intialise la hashmap
		for(Object o : vertices) {
			Vertex v = (Vertex) o;
			map.put(v, -1);
		}

		steps.add(new Step(copy(graph)));
		
		int color = 0;

		Vertex actualVertex = (Vertex) vertices[0];
		System.out.println("On colorie "+actualVertex.getIntValue()+" en "+color);
		map.put(actualVertex, color);

		// On répète N fois l'algo suivant :
		for(int i = 0; i < graph.getChildVertices(graph.getDefaultParent()).length; i++) {

			// Pour chaque sommet...
			for (Object o : vertices) 												
			{
				actualVertex = (Vertex) o;

				boolean coloried = map.get(actualVertex) != -1;

				// Si il a pas déjà été colorié
				if(!coloried) {
					
					// On regarde chez tous ses voisins si y'en a un de la même couleur
					Object[] voisinsOut = graph.getOutgoingEdges(actualVertex);
					Object[] voisinsIn = graph.getIncomingEdges(actualVertex);
					
					boolean voisinMemeCouleur = false;
					
					for(Object o2 : voisinsOut) {
						Vertex voisin = (Vertex)(((Edge) o2).getTarget());
						if(map.get(voisin) == color) {
							voisinMemeCouleur = true;
						}
					}
					for(Object o2 : voisinsIn) {
						Vertex voisin = (Vertex)(((Edge) o2).getSource());
						if(map.get(voisin) == color) {
							voisinMemeCouleur = true;
						}
					}

					if(!voisinMemeCouleur) {
						map.put(actualVertex, color);
						paintSolution();
						steps.add(new Step(copy(graph)));
					}
				}
			}

			color++;	
		}
	}
	
	/**
	 * Méthode d'affichage de la solution
	 */
	public void paintSolution() {
		for(Entry<Vertex, Integer> entry : map.entrySet()) {
			switch(entry.getValue()) {
			case 0 : graph.getModel().setStyle(entry.getKey(), "VC_RED"); break;
			case 1 : graph.getModel().setStyle(entry.getKey(), "VC_GREEN"); break;
			case 2 : graph.getModel().setStyle(entry.getKey(), "VC_BLUE"); break;
			case 3 : graph.getModel().setStyle(entry.getKey(), "VC_YELLOW"); break;
			case 4 : graph.getModel().setStyle(entry.getKey(), "VC_CYAN"); break;
			case 5 : graph.getModel().setStyle(entry.getKey(), "VC_MAGENTA"); break;
			default : break;
			}
		}
	}

}
