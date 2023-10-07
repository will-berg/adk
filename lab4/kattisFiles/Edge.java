public class Edge {
	private int source;
	private int target;

	// Source och target är roller, om det är en kant mellan dem är de i samma scen
	public Edge(int source, int target) {
		this.source = source;
		this.target = target;
	}

	public int getSource() {
		return source;
	}

	public int getTarget() {
		return target;
	}
}
