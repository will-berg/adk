public class Edge {
    private int from, to, capacity, flow;

    public int getTo() {
        return to;
    }

    public int getFrom() {
        return from;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFlow() {
        return flow;
    }

    public int getResFlow() {
        return capacity - flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public Edge(int from, int to, int capacity, int flow) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = flow;
    }

    public Edge(int from, int to) {
        this.from = from;
        this.to = to;
        this.capacity = 1;
        this.flow = 0;
    }

    public String debugString() {
        return String.format("%s %s (%s)", this.getFrom(), this.getTo(), this.getFlow());
    }

    public String edgeString() {
        return from + " " + to;
    }

    public boolean equals(Edge e) {
        return from == e.from && to == e.to;
    }
}
