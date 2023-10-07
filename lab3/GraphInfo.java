import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphInfo {
    public int xCount, 
        yCount, 
        edgeCount, 
        ingoingCapacity, 
        outgoingCapacity,
        source = -1,
        sink = -1,
        totalFlow = -1;

    public LinkedList<Edge> edges;
    public HashSet<Integer> xNodes, yNodes;

    public GraphInfo() {
        this.edges = new LinkedList<>();
        this.xNodes = new HashSet<>();
        this.yNodes = new HashSet<>();
    }

    public void addX(int x) {
        this.xNodes.add(x);
    }

    public void addY(int y) {
        this.yNodes.add(y);
    }

    public static GraphInfo toMaxFlow(
        GraphInfo g, 
        int outgoingCapacity, 
        int ingoingCapacity)
    {
        int source = g.xCount + g.yCount + 1;
        int sink = source + 1;
        
        g.source = source;
        g.sink = sink;

        for (int y : g.yNodes)
        {
            Edge e = new Edge(y, sink, ingoingCapacity, ingoingCapacity);
            g.edges.add(e);
        }
        g.edgeCount += g.yNodes.size();

        for (int x : g.xNodes)
        {
            Edge e = new Edge(source, x, outgoingCapacity, outgoingCapacity);
            g.edges.add(e);
        }
        g.edgeCount += g.xNodes.size();
        
        return g;
    }

    public static void printFlow(Kattio io, GraphInfo gi, boolean weight) {
        // int nodeCount = gi.xCount + gi.yCount;
        // io.println(nodeCount + 2);
        io.println(gi.xCount + gi.yCount + 2);
        io.print(gi.source);
        io.print(' ');
        io.println(gi.sink);
        io.println(gi.edges.size());
        gi.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge arg0, Edge arg1) {
                return Integer.compare(arg0.getFrom(), arg1.getFrom());
            }
        });
        for (Edge e : gi.edges) {
            io.print(e.getFrom());
            io.print(' ');
            io.print(e.getTo());
            if (weight) {
                io.print(' ');
                io.print(e.getFlow());
            }
            io.println();
        }
        io.flush();
    }

    public static void printMaxFlow(Kattio io, GraphInfo gi, boolean weight) {
        io.println(gi.xNodes.size() + gi.yNodes.size() + 2);
        io.print(gi.source);
        io.print(' ');
        io.print(gi.sink);
        io.print(' ');
        io.println(gi.totalFlow);
        io.println(gi.edges.size());
        gi.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge arg0, Edge arg1) {
                return Integer.compare(arg0.getFrom(), arg1.getFrom());
            }
        });
        for (Edge e : gi.edges) {
            io.print(e.getFrom());
            io.print(' ');
            io.print(e.getTo());
            if (weight) {
                io.print(' ');
                io.print(e.getFlow());
            }
            io.println();
        }
        io.flush();
    }

    public static void printBipartite(Kattio io, GraphInfo gi, boolean weight) {
        io.print(gi.xCount);
        io.print(' ');
        io.println(gi.yCount);
        io.println(gi.edges.size());
        gi.edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge arg0, Edge arg1) {
                return Integer.compare(arg0.getFrom(), arg1.getFrom());
            }
        });
        for (Edge e : gi.edges) {
            io.print(e.getFrom());
            io.print(' ');
            io.print(e.getTo());
            if (weight) {
                io.print(' ');
                io.print(e.getFlow());
            }
            io.println();
        }
        io.flush();
    }

    public void debugPrint()
    {
        int slicers = 60;
        System.out.println("Debug Print");
        System.out.println("-".repeat(slicers));
        System.out.println("xCount:    " + this.xCount);
        System.out.println("yCount:    " + this.yCount);
        System.out.println("eCount:    " + this.edgeCount);
        System.out.println("in:        " + this.ingoingCapacity);
        System.out.println("out:       " + this.outgoingCapacity);
        System.out.println("source:    " + this.source);
        System.out.println("sink:      " + this.sink);
        System.out.println("totalFlow: " + this.totalFlow);
        System.out.println("-".repeat(slicers));
        System.out.println("Edges:");
        for (Edge e : this.edges)
        {
            System.out.println(e.debugString());
        }
        System.out.println("-".repeat(slicers));
        System.out.println("X nodes:");
        System.out.println(Arrays.toString(this.xNodes.toArray()));
        System.out.println("-".repeat(slicers));
        System.out.println("Y nodes:");
        System.out.println(Arrays.toString(this.yNodes.toArray()));
        System.out.println("-".repeat(slicers));
    }
}
