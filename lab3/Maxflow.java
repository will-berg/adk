import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Maxflow {
    private static Hashtable<Integer, Integer> predecessors;
    // private static int LOOPS_BFS = 0;
    // private static int LOOPS_BFS_LOG2 = 1;
    // TODO: bfstest.indata ger 4 5 som extra
    private static LinkedList<Edge> bfs(Graph g, int start, int end, boolean printAll) throws IOException
    {
        // LOOPS_BFS++;
        // if (LOOPS_BFS == LOOPS_BFS_LOG2)
        // {
        //     System.out.println("BFS " + LOOPS_BFS);
        //     LOOPS_BFS_LOG2 <<= 1;
        // }
        for (Integer key : predecessors.keySet())
        {
            predecessors.put(key, -1);
        }
        Queue<Integer> q = new LinkedList<>();
        q.add(start);

        // FIXME: 307 becomes the start for some reason
        // FIXME: 501 87 307 53 471 502 can't connect 501 - 307, flow issue (0)
        while (!q.isEmpty()) {
            int from = q.poll();

            LinkedList<Edge> neighbours = g.getNeighbours(from);
            for (Edge e : neighbours) {
                int to = e.getTo();
                if ((predecessors.get(to) == null || predecessors.get(to) == -1)
                    && e.getFlow() < e.getCapacity()
                    && e.getTo() != start)
                {
                    if (printAll)
                    {
                        System.out.println(from + " " + to + " (" + e.getFlow() + "/" + e.getCapacity() + ")");
                    }
                    predecessors.put(to, from);
                    q.offer(e.getTo());
                }
            }
        }

        // Reconstruct path
        LinkedList<Edge> path = new LinkedList<>();
        int current = end;
        while (current != start)
        {
            Integer next = predecessors.get(current);
            if (next == null)
            {
                return null;
            }
            path.addFirst(g.getEdge(next, current));
            current = next;
        }
        return path;
    }

    // Find the max flow along the given path
    private static int maxFlow(LinkedList<Edge> path)
    {
        int max = Integer.MAX_VALUE;
        for (Edge e : path)
        {
            int remainingFlow = e.getCapacity() - e.getFlow();
            if (remainingFlow == 0)
            {
                return 0;
            }
            max = Integer.min(max, remainingFlow);
        }
        return max;
    }

    // Reduce the flow on all paths according with 'flow'
    private static void augmentPath(Graph g, LinkedList<Edge> path, int flow)
    {
        for (Edge e : path)
        {
            int eFlow = e.getFlow();
            e.setFlow(eFlow + flow);
            Edge re = g.getEdge(e.getTo(), e.getFrom());
            re.setFlow(re.getCapacity() - flow);
        }
    }

    private static void printPath(LinkedList<Edge> path, boolean showAll)
    {
        int max = maxFlow(path);
        for (Edge e : path)
        {
            int restFlow = e.getCapacity() - e.getFlow();
            System.out.print(e.getFrom());
            System.out.print(' ');
            if (showAll)
            {
                System.out.print("[" + restFlow + "]");
                System.out.print(' ');
            }
        }
        System.out.print(path.getLast().getTo());
        System.out.print(' ');
        if (showAll)
        {
            System.out.print("[" + (path.getLast().getCapacity() - path.getLast().getFlow()) + "]");
            System.out.print(' ');
        }
        System.out.println("(" + max + ")");
    }

    private static boolean linkedEquals(
        LinkedList<Edge> a,
        LinkedList<Edge> b)
    {
        // Null
        if (a == null || b == null)
        {
            return false;
        }
        // Inequal size
        if (a.size() != b.size())
        {
            return false;
        }

        Iterator<Edge> ait = a.iterator();
        Iterator<Edge> bit = b.iterator();
        while (ait.hasNext() && bit.hasNext())
        {
            // One element was not equal
            if (!ait.next().equals(bit.next()))
            {
                return false;
            }
        }
        // All elements were used and were equal
        return true;
    }

    private static String slicer = "-".repeat(60);
    private static void printDebug(LinkedList<Edge> path, int flow)
    {
        System.out.println("Flow: " + flow);
        printPath(path, true);
        System.out.println(slicer);
    }

    public static GraphInfo solve(GraphInfo gi) throws IOException
    {
        Graph g = Graph.toGraph(gi);
        gi.totalFlow = 0;
        int nodes = g.getX() + g.getY();

        // int source = g.xCount + g.yCount + 1;
        int source = gi.source;
        // int sink = source + 1;
        int sink = gi.sink;

        predecessors = new Hashtable<>(nodes + 2 + 1, 1f); //new int[nodes + 2 + 1];

        // https://www.cs.princeton.edu/courses/archive/fall11/cos226/lectures/64MaxFlow.pdf
        // p55

        LinkedList<Edge> path = bfs(g, source, sink, false);
        LinkedList<Edge> prev = null;
        while (path != null)
        {
            int pathMax = maxFlow(path);
            printPath(path, pathMax == 0);
            augmentPath(g, path, pathMax);
            prev = path;
            path = bfs(g, source, sink, false);
            if (linkedEquals(prev, path))
            {
                path = bfs(g, source, sink, true);
                System.out.println("INF");
                System.exit(1);
            }
            gi.totalFlow += pathMax;
        }

        // Remove all unused edges
        // remove condition: flow to node is equal to its capacity (untouched)
        gi.edges.removeIf(e -> e.getFlow() <= 0 || (e.getFrom() > e.getTo() && e.getFrom() != source));
        return gi;
    }

    public static void main(String[] args) throws IOException
    {
        Kattio io = new Kattio(System.in, System.out);
        GraphInfo gi = Parse.parseFlow(io);
        // gi.debugPrint();
        GraphInfo solved = solve(gi);
        GraphInfo.printMaxFlow(io, solved, true);
    }
}
