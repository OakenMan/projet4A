//package algorithms;
//
//import java.util.ArrayList;
//
//import model.Edge;
//import model.Graph;
//import model.Vertex;
//
//public class VertexColor extends AbstractShortestPath {
//
//	ArrayList<ArrayList<Vertex>> vertexPerColor;
//	
//	public VertexColor(Graph graph) {
//		super(graph);
//		
//		vertexPerColor = new ArrayList<ArrayList<Vertex>>();
//		for(ArrayList<Vertex> av : vertexPerColor) {
//			av = new ArrayList<Vertex>();
//		}
//	}
//
//	@Override
//	public void findShortestPath() {
//		Object[] vertices = graph.getChildVertices(graph.getDefaultParent());	
//		
//		int color = 0;
//		
//		Vertex actualVertex = (Vertex) vertices[0];
//		vertexPerColor.get(color).add(actualVertex);
//		
//		// Pour chaque sommet...
//		for (Object o : vertices) 												
//		{
//			actualVertex = (Vertex) o;
//			// Si il a pas déjà été colorié
//			boolean coloried = false;
//			
//			
//			for(Object o2 : vertexPerColor.get(color)) {
//				Vertex coloriedVertex = (Vertex) o2;
//				if(!isConnected(vertex, coloriedVertex)) {
//					vertexPerColor.get(color).add(vertex);
//				}
//			}
//			
//		}
//	}
//	
//	public boolean isConnected(Vertex v1, Vertex v2) {
//		Object[] edgesFromV1 = graph.getOutgoingEdges(v1);
//		
//		for (Object o : edgesFromV1)
//		{
//			Edge edge = (Edge) o;
//			if (edge.getTarget().equals(v1) || v1.equals(v2))
//			{
//				return true;
//			}
//		}
//		return false;
//	}
//
//}
