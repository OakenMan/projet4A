package algorithms;

import java.util.ArrayList;

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
	
	public void findShortestPath()
	{
		
	}
}
