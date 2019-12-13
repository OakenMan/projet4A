package algorithms;

import java.util.ArrayList;

import model.Edge;
import model.Graph;
import model.Vertex;

public class TravellingSalesman extends AbstractShortestPath
{
	private Graph graph;
	
	public TravellingSalesman(Graph graph)
	{
		super(graph);
	}
	
	public Solution ShortestNeighboor() throws Exception
	{
		ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
		System.out.println(graph);
		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());
		System.out.println("COUCOU");
		
		int randomNumber = 0 + (int)(Math.random() * ((vertices.length - 1 - 0) + 1));
		Vertex startVertex = (Vertex) vertices[randomNumber];
		vertexList.add(startVertex);
		System.out.println(vertexList);
		
		Vertex lastVertex = startVertex;
		Vertex actualVertex = null;
		while (vertexList.size() != vertices.length)
		{
			int min = INFINITE;
			for (Object o : vertices)
			{
				Vertex vertex = (Vertex) o;
				if (!vertexList.contains(vertex))
				{
					if (min > getDistanceBetween(lastVertex, vertex))
					{
						actualVertex = vertex;
						min = getDistanceBetween(lastVertex, vertex);
					}
				}
				lastVertex = actualVertex;
				vertexList.add(lastVertex);
			}
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
		for (int index = 0; index < solution.getSolution().size() - 2; index += 2)
		{
			graph.getModel().setStyle(solution.getSolution().get(index), "BOLD_VERTEX");
			o = graph.getEdgesBetween(solution.getSolution().get(index), solution.getSolution().get(index+1));
			edge = (Edge) o[0];
			graph.getModel().setStyle(edge, "BOLD_EDGE");
			graph.getModel().setStyle(solution.getSolution().get(index+1), "BOLD_VERTEX");
		}
		steps.add(copy(graph));
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
			System.out.println("COUCOU");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage() + " ERREUR DANS SHORTEST NEIGHBOOR");
		}
		
		firstSolution.calculateWeight();
		bestSolution = firstSolution;
		
		steps.add(copy(graph));
		printSolution(bestSolution);
		
		while (counter < 3)
		{
			currentSolution = bestSolution;
			for(int i = 0; i < 10; i++)
			{
				currentNeighboor = randomEdgeExchange(currentSolution);
				if (currentNeighboor.getWeight() < currentSolution.getWeight())
				{
					currentSolution = currentNeighboor;
				}
			}
			if (currentSolution.getWeight() != bestSolution.getWeight())
			{
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
