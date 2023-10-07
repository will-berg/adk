import java.io.IOException;

public class Parse {
    public static GraphInfo parseBipartite(Kattio io) throws IOException
    {
        GraphInfo g = new GraphInfo();

        // Load meta info of graph
        int xCount = io.getInt();
        g.xCount = xCount;

        int yCount = io.getInt();
        g.yCount = yCount;

        int edgeCount = io.getInt();
        g.edgeCount = edgeCount;

        // Load edges of graph
        for (int i = 0; i < edgeCount; i++)
        {
            int x = io.getInt();
            int y = io.getInt();

            Edge e = new Edge(x, y, 1, 1);
            g.edges.add(e);
            g.addX(x);
            g.addY(y);
        }

        return g;
    }

    public static GraphInfo parseFlow(Kattio io) throws IOException
    {
        GraphInfo g = new GraphInfo();

        int _nodeCount = io.getInt();
        int source = io.getInt();
        int sink = io.getInt();
        int eCount = io.getInt();

        for (int i = 0; i < eCount; i++)
        {
            int xNode = io.getInt();
            int yNode = io.getInt();
            int xyFlow = io.getInt();

            Edge e = new Edge(xNode, yNode, xyFlow);
            g.edges.add(e);
            g.edges.add(new Edge(yNode, xNode, xyFlow, xyFlow));
            if (!(xNode == source || yNode == sink))
            {
                g.addX(xNode);
                g.addY(yNode);
            }
        }
        
        g.edgeCount = eCount;
        g.source = source;
        g.sink = sink;
        g.xCount = g.xNodes.size();
        g.yCount = g.yNodes.size();

        return g;
    }


    public static GraphInfo parseMaxFlow(Kattio io, int x, int y) throws IOException
    {
        GraphInfo g = new GraphInfo();

        // Load meta info of graph
        int _nodeCount = io.getInt();
        int sourceNode = io.getInt();
        int sinkNode = io.getInt();
        int sourceSinkflow = io.getInt();
        int edgeCount = io.getInt();

        // Load edges of graph
        for (int i = 0; i < edgeCount; i++)
        {
            int xNode = io.getInt();
            int yNode = io.getInt();
            int xyFlow = io.getInt();

            if (xNode == sourceNode || yNode == sinkNode)
            {
                continue;
            }

            Edge e = new Edge(xNode, yNode, xyFlow);
            g.edges.add(e);
            g.addX(xNode);
            g.addY(yNode);
        }

        g.xCount = x;
        g.yCount = y;
        g.edgeCount = edgeCount;
        g.source = sourceNode;
        g.sink = sinkNode;
        g.totalFlow = sourceSinkflow;

        return g;
    }

    public static GraphInfo parseMaxFlow(Kattio io) throws IOException
    {
        GraphInfo gi = parseMaxFlow(io, 0, 0);
        gi.xCount = gi.xNodes.size();
        gi.yCount = gi.yNodes.size();
        return gi;
    }
}
