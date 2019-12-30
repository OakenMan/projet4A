package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import controller.mainWindow.MainWindowController;
import model.Edge;
import model.Graph;
import model.Vertex;

public class TravellingSalesman extends AbstractShortestPath {

	private long time;

	private String baseSolution;			// Solution de base pour TSP
	private String heurtistique;			// Heurtistique pour TSP
	private int nbTries;					// Nombre d'essais pour TSP
	private int nbExchanges;				// Nombre d'échanges pour TSP

	/*===== BUILDER =====*/
	public TravellingSalesman(Graph graph) {
		super(graph);
	}

	/*===== METHODS =====*/

	/** ~O(n³)
	 * Calcule tous les solutions de bases possibles à partir de l'algo du plus proche voisin et renvoie la meilleure
	 * @return la meilleure solution de départ avec PPV
	 */
	public Solution findBestStartSolution() {
		Solution bestSolution = shortestNeighboor(null);
		int depart = -1;

		for(Object o : graph.getChildVertices(graph.getDefaultParent())) {
			Solution tmp = shortestNeighboor((Vertex)o);
			if(tmp.getWeight() < bestSolution.getWeight()) {
				bestSolution = tmp.copy();
				depart = ((Vertex)o).getIntValue();
			}
		}

		System.out.println("Solution de base : Départ="+depart+", Poids="+bestSolution.getWeight());
		return bestSolution;
	}

	/**	~O(n²)
	 * Calcule une solution de départ avec l'algorithme du plus proche voisin
	 * wantedStart le sommet de départ, ou null si on veut laisser le hasard décider
	 * @return une solution de départ
	 */
	public Solution shortestNeighboor(Vertex wantedStart) {
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		Vertex startVertex = null;

		// Si on a pas choisi de point de départ, on en choisit un au hasard
		if(wantedStart == null) {
			int randomNumber = (int)(Math.random() * vertices.length);
			startVertex = (Vertex) vertices[randomNumber];
		}
		// Sinon on prend celui qui a été renseigné
		else {
			startVertex = wantedStart;
		}

		vertexList.add(startVertex);

		Vertex lastVertex = startVertex;
		Vertex actualVertex = null;

		// Tant qu'on a pas parcouru tous les sommets...
		while (vertexList.size() != vertices.length) {
			int min = INFINITE;
			for (Object o : vertices) {
				Vertex vertex = (Vertex) o;

				// Si on a pas déjà ajouté ce sommet
				if (!(vertexList.contains(vertex))) {
					try {
						if (getDistanceBetween(lastVertex, vertex) < min) {
							actualVertex = vertex;
							min = getDistanceBetween(lastVertex, vertex);
						}
					} catch(Exception e) {
						//						e.printStackTrace();
					}
				}
			}
			lastVertex = actualVertex;
			vertexList.add(lastVertex);
		}

		Solution solution = new Solution(vertexList, graph);

		return solution;
	}

	/** O(n)
	 * Calcule une solution de départ au hasard
	 * @return une solution de départ
	 */
	public Solution randomSolution()
	{
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());

		for (Object o : vertices){
			vertexList.add((Vertex)o);
		}

		Collections.shuffle(vertexList);
		Solution solution = new Solution(vertexList, graph);

		return solution;
	}

	/**
	 * Choisit deux sommets au hasard et les échange
	 * @param solution
	 * @return
	 */
	public Solution randomVertexExchange(Solution solution)
	{
		Solution newSolution = solution.copy();

		int firstRandomVertex = (int)(Math.random() * newSolution.getSolution().size());
		int secondRandomVertex = (int)(Math.random() * newSolution.getSolution().size());

		Vertex temp = newSolution.getSolution().get(firstRandomVertex);
		newSolution.getSolution().set(firstRandomVertex, newSolution.getSolution().get(secondRandomVertex));
		newSolution.getSolution().set(secondRandomVertex, temp);
		newSolution.calculateWeight();

		return newSolution;
	}

	/**
	 * Echange les deux sommets fournis en paramètres
	 * @param solution
	 * @param index1
	 * @param index2
	 * @return
	 */
	public Solution vertexExchange(Solution solution, int index1, int index2)
	{
		Solution newSolution = solution.copy();

		Vertex temp = newSolution.getSolution().get(index1);
		newSolution.getSolution().set(index1, solution.getSolution().get(index2));
		newSolution.getSolution().set(index2, temp);
		newSolution.calculateWeight();

		return newSolution;
	}

	/** ~O(2n)
	 * Applique la solution passée en paramètre sur le style du graphe et l'enregistre dans les étapes
	 * @param solution
	 */
	public void printSolution(Solution solution, boolean cleanAfter)
	{
		Edge edge = null;
		Object[] o = null;

		for (int index = 0; index < solution.getSolution().size(); index += 1)
		{
			// Arc qui ferme le cycle
			if (index == solution.getSolution().size() -1)
			{
				graph.getModel().setStyle(solution.getSolution().get(index), "BOLD_VERTEX");
				o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(0), true);
				edge = (Edge) o[0];
				graph.getModel().setStyle(edge, "BOLD_EDGE");
			}
			else
			{
				graph.getModel().setStyle(solution.getSolution().get(index), "BOLD_VERTEX");
				o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(index+1), true);
				edge = (Edge) o[0];
				graph.getModel().setStyle(edge, "BOLD_EDGE");
			}
		}

		steps.add(new Step(copy(graph), "Poids de la solution : " + solution.getWeight()));

		if(cleanAfter) {
			for (int index = 0; index < solution.getSolution().size(); index += 1)
			{
				if (index == solution.getSolution().size() -1)
				{
					graph.getModel().setStyle(solution.getSolution().get(index), "ROUNDED");
					o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(0), true);
					edge = (Edge) o[0];
					graph.getModel().setStyle(edge, "INVISIBLE");
				}
				else
				{
					graph.getModel().setStyle(solution.getSolution().get(index), "ROUNDED");
					o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(index+1), true);
					edge = (Edge) o[0];
					graph.getModel().setStyle(edge, "INVISIBLE");
				}
			}
		}
	}

	public void findShortestPath()
	{
		int counter = 0;

		Solution currentSolution = null;
		Solution currentNeighboor = null;

		time = System.currentTimeMillis();

		// Choix de la solution de base :
		switch(MainWindowController.getBaseSolution()) {
		case "Shortest Neighboor (best)" : 		currentSolution = findBestStartSolution(); 	break;
		case "Shortest Neighboor (random)" : 	currentSolution = shortestNeighboor(null); 	break;
		case "Random" : 						currentSolution = randomSolution(); 		break;
		default: 								currentSolution = randomSolution(); 		break;
		}

		System.err.println("@ solution de base : " + (System.currentTimeMillis()-time) + "ms");

		time = System.currentTimeMillis();

		printSolution(currentSolution, true);

		System.err.println("@ print solution : " + (System.currentTimeMillis()-time) + "ms");

		while (counter < MainWindowController.getNbTries()) {
			System.out.println("TRY #"+counter);
			boolean amelioration = false;

			time = System.currentTimeMillis();

			// Choix de l'heurtistique :

			if(MainWindowController.getHeurtistique().equals("Echanges d'arcs aléatoires")) {
				// échange d'arcs aléatoire (efficace sur des gros graphes)
				for(int i = 0; i < MainWindowController.getNbExchanges() && !amelioration; i++)
				{
					currentNeighboor = randomVertexExchange(currentSolution);

					if (currentNeighboor.getWeight() < currentSolution.getWeight())
					{
						System.out.println("Amelioration at i="+i+" : old="+currentSolution.getWeight() + ", new="+currentNeighboor.getWeight());
						currentSolution = currentNeighboor.copy();
						printSolution(currentSolution, true);
						amelioration = true;
					}
				}
			}
			else if(MainWindowController.getHeurtistique().equals("Tous les échanges possibles")) {
				// échange de tous les arcs (efficace sur des petits graphes)
				for (int i = 0; i < currentSolution.getSolution().size() && !amelioration; i++)
				{
					for (int j = 0; j < currentSolution.getSolution().size() && !amelioration; j++)
					{
						if (i == currentSolution.getSolution().size() - 1) {
							currentNeighboor = vertexExchange(currentSolution, 0, j);
						}	
						else {
							currentNeighboor = vertexExchange(currentSolution, i+1, j);
						}
						// Si après un échange on a une meilleur solution
						if (currentNeighboor.getWeight() < currentSolution.getWeight())
						{
							System.out.println("Amelioration en échangeant "+i+" et "+j+" : old="+currentSolution.getWeight() + ", new="+currentNeighboor.getWeight());
							currentSolution = currentNeighboor.copy();
							printSolution(currentSolution, true);
						}
					}
				}
			}

			counter++;

			System.err.println("@ échange des arcs : " + (System.currentTimeMillis()-time) + "ms");
		}

		steps.remove(steps.size()-1);
		printSolution(currentSolution, false);

		System.out.println("Best solution = " + currentSolution.getWeight());
	}
}
