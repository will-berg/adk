import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;


public class Graph {
    private Hashtable<Integer, LinkedList<Edge>> edges;
    private int xCount, yCount, eCount;
    private LRU<Integer, Edge> edgeCache;

    public int getX() {
        return xCount;
    }

    public int getY() {
        return yCount;
    }

    public int getE() {
        return eCount;
    }

    public Graph(int x, int y, int e) {
        this.xCount = x;
        this.yCount = y;
        this.eCount = e;
        this.edges = new Hashtable<>(x + y + 2 + 1, 1f); // (LinkedList<Edge>[])Array.newInstance(LinkedList.class, x + y + 2 + 1);
        this.edgeCache = new LRU<>(x + y); // new HashCache<>(x + y);
    }

    private static int edgeId(int x, int y) {
        return 17 * Integer.hashCode(x) + 97 * Integer.hashCode(y);
    }

    public Edge getEdge(int from, int to) {
        int id = edgeId(from, to);
        Edge e = edgeCache.get(id);
        if (e != null) {
            return e;
        }

        for (Edge n : getNeighbours(from)) {
            // Cache all values we go past
            edgeCache.put(edgeId(n.getFrom(), n.getTo()), n);
            if (n.getTo() == to) {
                return n;
            }
        }
        return null;
    }

    public LinkedList<Edge> getNeighbours(int from) {
        LinkedList<Edge> neighbours = edges.get(from);
        if (neighbours == null)
        {
            neighbours = new LinkedList<>();
            edges.put(from, neighbours);
        }
        return neighbours;
    }


    public int getFlow(int from, int to) {
        Edge n = getEdge(from, to);
        if (n != null) {
            return n.getFlow();
        }
        return -1;
    }

    public void setFlow(int from, int to, int newFlow) {
        Edge n = getEdge(from, to);
        if (n != null) {
            n.setFlow(newFlow);
        }
    }

    public void addEdge(int from, int to, int maxCapacity) {
        Edge e = new Edge(from, to, maxCapacity);
        getNeighbours(from).add(e);
    }

    public void addEdge(Edge e) {
        getNeighbours(e.getFrom()).add(e);
    }

    // public static void printBipartite(
    //     Kattio io,
    //     Graph g,
    //     boolean weight)
    // {
    //     io.print(g.xCount);
    //     io.print(' ');
    //     io.println(g.yCount);
    //     io.println(g.eCount);
    //     for (int i = 0; i < g.edges.size(); i++)
    //     {
    //         for (Edge e : g.edges[i])
    //         {
    //             io.print(e.getFrom());
    //             io.print(' ');
    //             io.print(e.getTo());
    //             if (weight)
    //             {
    //                 io.print(' ');
    //                 io.print(e.getFlow());
    //             }
    //             io.println();
    //         }
    //     }
    //     io.flush();
    // }

    // public static void printMaxFlow(
    //     Kattio io,
    //     Graph g,
    //     boolean weight)
    // {
    //     io.println(g.edges.length);
    //     io.print(g.xCount + g.yCount + 1);
    //     io.print(' ');
    //     io.println(g.xCount + g.yCount + 2);
    //     io.println(g.eCount);
    //     for (int i = 0; i < g.edges.length; i++)
    //     {
    //         for (Edge e : g.edges[i])
    //         {
    //             io.print(e.getFrom());
    //             io.print(' ');
    //             io.print(e.getTo());
    //             if (weight)
    //             {
    //                 io.print(' ');
    //                 io.print(e.getFlow());
    //             }
    //             io.println();
    //         }
    //     }
    //     io.flush();
    // }

    public static Graph toGraph(int x, int y, int edges, LinkedList<Edge> inputEdges) {
        Graph g = new Graph(x, y, edges);
        for (Edge e : inputEdges)
        {
            g.addEdge(e);
        }
        return g;
    }

    public static Graph toGraph(GraphInfo gi) {
        return Graph.toGraph(gi.xNodes.size(), gi.yNodes.size(), gi.edgeCount, gi.edges);
    }

    public static Graph toMaxFlowGraph(
        GraphInfo g,
        int outgoingCapacity,
        int ingoingCapacity) {
        GraphInfo gi = GraphInfo.toMaxFlow(g, outgoingCapacity, ingoingCapacity);
        return Graph.toGraph(gi);
    }

    public static void main(String[] args) throws IOException {
        Kattio io = new Kattio(System.in, System.out);
        // FileWriter writer = new FileWriter("debug.info");
        /*
            --bi-to-max-flow
            --max-flow-to-bi
        */
        GraphInfo gi;

        if (args.length > 0) {
            switch (args[0]) {
                case "--bi-max-flow":
                case "-b":
                    gi = Parse.parseBipartite(io);
                    gi = GraphInfo.toMaxFlow(gi, 1, 1);
                    GraphInfo.printFlow(io, gi, true);
                    break;
                case "--max-flow-bi":
                case "-m":
                    gi = Parse.parseMaxFlow(io);
                    GraphInfo.printBipartite(io, gi, false);
                    break;
            }
        } else {
            gi = Parse.parseBipartite(io);
            gi = GraphInfo.toMaxFlow(gi, 1, 1);
            GraphInfo.printFlow(io, gi, true);

            gi = Parse.parseMaxFlow(io, gi.xCount, gi.yCount);
            GraphInfo.printBipartite(io, gi, false);
        }
        io.close();
    }
}
