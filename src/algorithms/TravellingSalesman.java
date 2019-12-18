package algorithms;

import java.util.ArrayList;

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
	
	public Solution randomEdgeExchange(Solution solution)
	{
		int firstRandomVertex = 0 + (int)(Math.random() * ((solution.getSolution().size() - 1 - 0) + 1));
		int secondRandomVertex = 0 + (int)(Math.random() * ((solution.getSolution().size() - 1 - 0) + 1));
		
		Vertex temp = solution.getSolution().get(firstRandomVertex);
		solution.getSolution().set(firstRandomVertex, solution.getSolution().get(secondRandomVertex));
		solution.getSolution().set(secondRandomVertex, temp);
		solution.calculateWeight();
		return solution;
	}
	
	public Solution edgeExchange(Solution solution, int index1, int index2)
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
		
		try 
		{
			firstSolution = ShortestNeighboor();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e.getMessage() + " ERREUR DANS SHORTEST NEIGHBOOR");
			System.exit(0);
		}
		
		firstSolution.calculateWeight();
		bestSolution = firstSolution;
		
		steps.add(new Step(copy(graph)));	
		printSolution(bestSolution);
		
		while (counter < 1)
		{
			currentSolution = bestSolution;
			for(int i = 0; i < 1; i++)
			{
				currentNeighboor = randomEdgeExchange(currentSolution);
				if (currentNeighboor.getWeight() < currentSolution.getWeight())
				{
					System.out.println("on échange !");
					currentSolution = currentNeighboor;
				}
			}
			System.out.println("poid du meilleur : " + bestSolution.getWeight() + "        poid de ma solution actuelle : " + currentSolution.getWeight());
			System.out.println(bestSolution.getSolution());
			if (currentSolution.getWeight() != bestSolution.getWeight())
			{
				System.out.println("j'ai échangé ma meilleure solution !");
				bestSolution = currentSolution;
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
