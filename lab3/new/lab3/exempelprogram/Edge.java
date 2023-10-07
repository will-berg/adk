public class Edge {
	private int target;
	private int capacity;
  	private int flow;
	private int remainingCapacity;
	private int source;
	private Edge rev;

	public Edge(int source, int target, int capacity) {
		this.source = source;
		this.target = target;
		this.capacity = capacity;
		flow = 0;
		remainingCapacity = capacity;
	}

	public Edge(int target, int capacity) {
		this.target = target;
		this.capacity = capacity;
	}


	public void setRev(Edge e) {
		rev = e;
	}

	public Edge getRev() {
		return rev;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getTarget() {
		return target;
	}

	public int getSource() {
		return source;
	}

  	public int getFlow() {
		return flow;
	}

  	public int getRemainingCapacity() {
		return remainingCapacity;
	}

  	public void setFlow(int newFlow) {
		flow = newFlow;
	}

  	public void setRemainingCapacity(int newRemainingCapacity) {
    	remainingCapacity = newRemainingCapacity;
  	}

}
