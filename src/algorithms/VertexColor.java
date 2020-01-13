package algorithms;

import java.util.HashMap;
import java.util.Map.Entry;

import model.Edge;
import model.Graph;
import model.Vertex;

/**
 * Cette classe est la classe permettant de gérer l'affichage et la résolution de l'algorithme de coloration de graphe
 */
public class VertexColor extends AbstractAlgorithm {

	/*===== ATTRIBUTES =====*/
	/** HashMap qui attribut une couleur (sous la forme d'un Integer) à chaque sommet **/
	HashMap<Vertex, Integer> map;		

	/*===== BUILDER =====*/
	public VertexColor(Graph graph) {
		super(graph);	
	}

	/*===== METHODS =====*/
	
	/**
	 * Fonction qui execute l'algorithme de coloration de graphe. Elle fait appel aux fonctions de la classe, est elle appelée dans le constructeur
	 */
	@Override
	public void executeAlgorithm() {

		// On intialise la hashmap (on attribue la couleur -1 à chaque sommet)
		map = new HashMap<Vertex, Integer>();
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());	
		for(Object o : vertices) {
			Vertex v = (Vertex) o;
			map.put(v, -1);
		}

		// On ajoute l'étape de départ
		steps.add(new Step(copy(graph)));
		
		int color = 0;

		// On colorie le premier sommet avec la première couleur
		Vertex actualVertex = (Vertex) vertices[0];
		map.put(actualVertex, color);

		int nbVertices = graph.getChildVertices(graph.getDefaultParent()).length;
		
		// On répète N fois l'algo suivant :
		for(int i = 0; i < nbVertices; i++) {

			// Pour chaque sommet...
			for (Object o : vertices) 												
			{
				actualVertex = (Vertex) o;

				// On vérifie si il a déjà été colorié
				boolean coloried = map.get(actualVertex) != -1;

				// Si il a pas déjà été colorié
				if(!coloried) {
					
					// On regarde chez tous ses voisins si y'en a un de la même couleur
					Object[] neighboorsOut = graph.getOutgoingEdges(actualVertex);
					Object[] neighboorsIn = graph.getIncomingEdges(actualVertex);
					
					boolean sameColorNeighboor = false;
					
					for(Object o2 : neighboorsOut) {
						Vertex voisin = (Vertex)(((Edge) o2).getTarget());
						if(map.get(voisin) == color) {
							sameColorNeighboor = true;
						}
					}
					for(Object o2 : neighboorsIn) {
						Vertex voisin = (Vertex)(((Edge) o2).getSource());
						if(map.get(voisin) == color) {
							sameColorNeighboor = true;
						}
					}
					
					// Si il en a pas de la même couleur, on lui donne cette couleur
					if(!sameColorNeighboor) {
						map.put(actualVertex, color);
						paintSolution();
						steps.add(new Step(copy(graph)));
					}
				}
			}

			// Une fois qu'on a regardé tous les sommets, on change de couleur et on recommence
			color++;	
		}
	}
	
	/**
	 * Cette méthode change le style du graphe pour appliquer les couleurs à chaque sommet
	*/
	public void paintSolution() {
		for(Entry<Vertex, Integer> entry : map.entrySet()) {
			switch(entry.getValue()) {
			case 0 : graph.getModel().setStyle(entry.getKey(), "VC_RED"); 		break;
			case 1 : graph.getModel().setStyle(entry.getKey(), "VC_GREEN"); 	break;
			case 2 : graph.getModel().setStyle(entry.getKey(), "VC_BLUE"); 		break;
			case 3 : graph.getModel().setStyle(entry.getKey(), "VC_YELLOW"); 	break;
			case 4 : graph.getModel().setStyle(entry.getKey(), "VC_CYAN"); 		break;
			case 5 : graph.getModel().setStyle(entry.getKey(), "VC_MAGENTA"); 	break;
			default : break;
			}
		}
	}

}
