import java.util.ArrayList;
import java.util.LinkedList;

public class MaxFlow {
    Kattio io;
    // private FlowGraph G;


/*     public static void main(String[] args) {
        new MaxFlow();
    } */


/*     public void readFlowGraph() {
        int v = io.getInt();
        int s = io.getInt()-1;
        int t = io.getInt()-1;
        int e = io.getInt();

        G = new FlowGraph(v, s, t, e);

        for (int i = 0; i < e; i++) {
            int a = io.getInt()-1;
            int b = io.getInt()-1;
            int c = io.getInt();
            G.addEdge(a, b, c);
        }
    } */


    public void edmondKarp(FlowGraph G) {
        while (true) {
			// Lagrar stigen
            Edge[] pred = new Edge[G.getVertices()];

            LinkedList<Integer> q = new LinkedList<Integer>();
            q.add(G.getSource());

            // BFS för att finna kortaste stig
            while (!q.isEmpty()) {
                int curr = q.poll();

				// Kanten är obesökt, går inte till source, och flöde kan skickas
                for (Edge e : G.getAdjList().get(curr)) {
                    if (pred[e.getTarget()] == null && e.getTarget() != G.getSource() && e.getCapacity() > e.getFlow()) {
                        pred[e.getTarget()] = e;
                        q.add(e.getTarget());
                    }
                }
            }

			// Kom vi inte till sink fann vi ingen stig, annars kan flödet öka ytterligare
            if (pred[G.getSink()] == null) break;
            int bottleneck = Integer.MAX_VALUE;

			// Beräkna mängden flöde genom att hitta den begränsande kantens flöde
            for (Edge e = pred[G.getSink()]; e != null; e = pred[e.getSource()]) {
                bottleneck = Math.min(bottleneck, e.getCapacity() - e.getFlow());
            }

			// Formeln
            for (Edge e = pred[G.getSink()]; e != null; e = pred[e.getSource()]) {
                e.setFlow(e.getFlow() + bottleneck);
                e.getRev().setFlow(e.getRev().getFlow() - bottleneck);
            }
			G.setFlow(G.getFlow() + bottleneck);
        }
    }


/*     public void printSolution(int flow) {
        ArrayList<Edge> posEdges = new ArrayList<Edge>();
        int posFlow = 0;

        int v = G.getVertices();
        int s = G.getSource();
        int t = G.getSink();

        io.println(v);
        io.println((s+1) + " " + (t+1) + " " + flow);
        for (int i = 0; i < G.getAdjList().size(); i++) {
            for (int j = 0; j < G.getAdjList().get(i).size(); j++) {
                Edge currEdge = G.getAdjList().get(i).get(j);
                if (currEdge.getFlow() > 0) {
                    posFlow++;
                    posEdges.add(currEdge);
                }
            }
        }
        io.println(posFlow);
        for (Edge e: posEdges) {
            io.println((e.getSource()+1) + " " + (e.getTarget()+1) + " " + e.getFlow());
        }
    } */


    public MaxFlow() {

    }

/* 	public MaxFlow() {
        io = new Kattio(System.in, System.out);
        readFlowGraph();
        int flow = edmondKarp();
        printSolution(flow);
        io.close();
    } */
}
