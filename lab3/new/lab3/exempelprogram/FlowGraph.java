import java.util.*;


public class FlowGraph {
	private int vertices;
  	private int source;
  	private int sink;
	private int edges;
	private int flow;
	private ArrayList<LinkedList<Edge>> adjList = new ArrayList<LinkedList<Edge>>();

	public FlowGraph(int v, int s, int t, int e) {
		vertices = v;
		source = s;
		sink = t;
		edges = e;
		flow = 0;
		for (int i = 0; i < vertices; i++) {
			adjList.add(new LinkedList<Edge>());
		}
	}

  public void addEdge(int a, int b, int capacity) {
		Edge edge = new Edge(a, b, capacity);
		Edge rev = new Edge(b, a, 0);

		edge.setRev(rev);
		rev.setRev(edge);
		adjList.get(a).add(edge);
		adjList.get(b).add(rev);
		edges += 2;
	}

	public ArrayList<LinkedList<Edge>> getAdjList() {
		return adjList;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public int getEdges() {
		return edges;
	}

	public int getVertices() {
		return vertices;
	}

	public int getSink() {
		return sink;
	}

	public int getSource() {
		return source;
	}
}
