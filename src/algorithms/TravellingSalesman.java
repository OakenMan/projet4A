package algorithms;

import java.util.ArrayList;
import java.util.Collections;

import model.Edge;
import model.Graph;
import model.Vertex;

public class TravellingSalesman extends AbstractShortestPath
{
	public TravellingSalesman(Graph graph)
	{
		super(graph);
		System.out.println("je suis construit !");
	}
	
	public Solution ShortestNeighboor()
	{
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		
		int randomNumber = 0 + (int)(Math.random() * ((vertices.length - 1 - 0) + 1));
		Vertex startVertex = (Vertex) vertices[randomNumber];
		vertexList.add(startVertex);
		
		Vertex lastVertex = startVertex;
		Vertex actualVertex = null;
		while (vertexList.size() != vertices.length)
		{
			int min = INFINITE;
			for (Object o : vertices)
			{
				Vertex vertex = (Vertex) o;
				if (!(vertexList.contains(vertex)))
				{
					try {
					if (min > getDistanceBetween(lastVertex, vertex))
					{
						actualVertex = vertex;
						min = getDistanceBetween(lastVertex, vertex);
					}
					}
					catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			lastVertex = actualVertex;
			vertexList.add(lastVertex);
		}
		Solution solution = new Solution(vertexList, graph);
		return solution;
	}
	
	public Solution randomSolution()
	{
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		Vertex vertex = null;
		
		for (Object o : vertices)
		{
			vertex = (Vertex) o;
			vertexList.add(vertex);
		}
		Collections.shuffle(vertexList);
		Solution solution = new Solution(vertexList, graph);
		return solution;
	}
	
	public Solution randomVertexExchange(Solution solution)
	{
		int firstRandomVertex = 0 + (int)(Math.random() * ((solution.getSolution().size() - 1 - 0) + 1));
		int secondRandomVertex = 0 + (int)(Math.random() * ((solution.getSolution().size() - 1 - 0) + 1));
		
		Vertex temp = solution.getSolution().get(firstRandomVertex);
		solution.getSolution().set(firstRandomVertex, solution.getSolution().get(secondRandomVertex));
		solution.getSolution().set(secondRandomVertex, temp);
		solution.calculateWeight();
		return solution;
	}
	
	public Solution vertexExchange(Solution solution, int index1, int index2)
	{
		Vertex temp = solution.getSolution().get(index1);
		solution.getSolution().set(index1, solution.getSolution().get(index2));
		solution.getSolution().set(index2, temp);
		solution.calculateWeight();
		return solution;
	}
	
	public void printSolution(Solution solution)
	{
		Edge edge = null;
		Object[] o = null;
		for (int index = 0; index < solution.getSolution().size(); index += 1)
		{
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
		for (int index = 0; index < solution.getSolution().size(); index += 1)
		{
			if (index == solution.getSolution().size() -1)
			{
				graph.getModel().setStyle(solution.getSolution().get(index), "DEFAULT");
				o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(0), true);
				edge = (Edge) o[0];
				graph.getModel().setStyle(edge, "INVISIBLE");
			}
			else
			{
				graph.getModel().setStyle(solution.getSolution().get(index), "DEFAULT");
				o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(index+1), true);
				edge = (Edge) o[0];
				graph.getModel().setStyle(edge, "INVISIBLE");
			}
		}
	}
	
	public void findShortestPath()
	{
		int counter = 0;
		Solution firstSolution = null;
		Solution bestSolution = null;
		Solution currentSolution = null;
		Solution currentNeighboor = null;
		Solution bestNeighboor = null;
		
		try 
		{
			//voisin le plus proche
			//firstSolution = ShortestNeighboor();
			
			//random
			firstSolution = randomSolution();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage() + " ERREUR DANS SHORTEST NEIGHBOOR");
			System.exit(0);
		}
		
		firstSolution.calculateWeight();
		bestSolution = firstSolution.copy();
		bestNeighboor = firstSolution.copy();
		
		steps.add(new Step(copy(graph)));	
		printSolution(bestSolution);
		
		while (counter < 5)
		{
			//échange de sommet aléatoire
			/*for(int i = 0; i < 1000; i++)
			{
				currentNeighboor = randomVertexExchange(currentSolution.copy()).copy();
				if (currentNeighboor.getWeight() < currentSolution.getWeight())
				{
					System.out.println("on échange !");
					currentSolution = currentNeighboor.copy();
				}
			}*/
			//échange de tous les sommets deux par deux
			/*for (int i = 0; i < currentSolution.getSolution().size(); i++)
			{
				for (int j = 0; j < currentSolution.getSolution().size(); j++)
				{
					currentNeighboor = vertexExchange(currentSolution.copy(), i, j);
					if (currentNeighboor.getWeight() < currentSolution.getWeight())
					{
						System.out.println("on échange !");
						currentSolution = currentNeighboor.copy();
					}
				}
			}*/
			//échange aléatoire de toutes les arrêtes
			for (int i = 0; i < bestNeighboor.getSolution().size(); i++)
			{
				for (int j = 0; j < bestNeighboor.getSolution().size(); j++)
				{
					if (i == bestNeighboor.getSolution().size() - 1)
						currentNeighboor = vertexExchange(bestNeighboor.copy(), 0, j);
					else
						currentNeighboor = vertexExchange(bestNeighboor.copy(), i+1, j);
					if (currentNeighboor.getWeight() < bestNeighboor.getWeight())
					{
						bestNeighboor = currentNeighboor.copy();
						printSolution(bestNeighboor);
					}
				}
			}
			currentSolution = bestNeighboor.copy();
			System.out.println("poid du meilleur : " + bestSolution.getWeight() + "        poid de ma solution actuelle : " + currentSolution.getWeight());
			System.out.println(bestSolution.getSolution());
			if (currentSolution.getWeight() < bestSolution.getWeight())
			{
				System.out.println("j'ai échangé ma meilleure solution !");
				bestSolution = currentSolution.copy();
				counter = 0;
				printSolution(bestSolution);
			}
			else 
			{
				counter++;
			}
		}
		printSolution(bestSolution);
	}
}
