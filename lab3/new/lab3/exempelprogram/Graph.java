import java.util.*;


public class Graph {
	private int x;
	private int y;
	private int vertices;
	private int edges;
	private ArrayList<LinkedList<Edge>> adjList = new ArrayList<LinkedList<Edge>>();
	private ArrayList<Edge> graphEdges;

	public Graph(int v, int x, int y) {
		this.x = x;
		this.y = y;
		vertices = v;
		edges = 0;
		graphEdges = new ArrayList<Edge>();
		for (int i = 0; i < vertices; i++) {
			adjList.add(new LinkedList<Edge>());
		}
	}

	public void addEdge(int a, int b) {
		Edge edge = new Edge(a, b, 1);
		adjList.get(a).add(edge);
		edges++;
	}

	public ArrayList<LinkedList<Edge>> getAdjList() {
		return adjList;
	}

	public int getEdges() {
		return edges;
	}

	public int getVertices() {
		return vertices;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
