package algorithms;

import java.util.ArrayList;

import model.Edge;
import model.Graph;
import model.Vertex;

public class Solution 
{
	private int weight;
	private ArrayList<Vertex> solution = new ArrayList<Vertex>();
	private Graph graph;
	
	public Solution(ArrayList<Vertex> solution, Graph graph)
	{
		this.solution = solution;
		this.graph = graph;
		calculateWeight();
	}
	public Solution(ArrayList<Vertex> solution, Graph graph, int weight)
	{
		this.solution = solution;
		this.graph = graph;
		this.weight = weight;
	}
	
	public void calculateWeight()
	{
		weight = 0;
		Edge edge = null;
		Vertex vertex1 = null;
		Vertex vertex2 = null;
		for (int i = 0; i<solution.size()-1; i++)
		{
			vertex1 = solution.get(i);
			vertex2 = solution.get(i+1);
			Object[] edges = graph.getEdgesBetween(vertex1, vertex2);
			edge = (Edge) edges[0];
			weight += edge.getIntValue();
		}
		vertex1 = solution.get(0);
		Object[] edges = graph.getEdgesBetween(vertex2, vertex1);
		edge = (Edge) edges[0];
		weight += edge.getIntValue();
	}
	
	public Solution copy()
	{
		ArrayList<Vertex> solu = (ArrayList<Vertex>)solution.clone();
		Solution sol = new Solution(solu, graph, weight);
		return sol;
	}
	
	public int getWeight() 
	{
		return weight;
	}
	public void setWeight(int weight) 
	{
		this.weight = weight;
	}
	public ArrayList<Vertex> getSolution() 
	{
		return solution;
	}
	public void setSolution(ArrayList<Vertex> solution) 
	{
		this.solution = solution;
	}
	public Graph getGraph() 
	{
		return graph;
	}
	public void setGraph(Graph graph) 
	{
		this.graph = graph;
	}
}
