/*
Exempel på in- och utdatahantering för maxflödeslabben i kursen
ADK.

Använder Kattio.java för in- och utläsning.
Se http://kattis.csc.kth.se/doc/javaio

@author: Per Austrin
*/
public class BipRed {
    Kattio io;
	private Graph G;
	private Edge[] maxMatchEdges;


	public static void main(String args[]) {
		new BipRed();
    }


    void readBipartiteGraph() {
		// Läs antal hörn och kanter
		int x = io.getInt();	// Hörn i X
		int y = io.getInt();	// Hörn i Y
		int e = io.getInt(); 	// Antalet kanter mellan X och Y

		G = new Graph(x+y+2, x, y);

		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
			int a = io.getInt();
			int b = io.getInt();
			G.addEdge(a, b);
		}

		// s ska ha alla noder i X som granne och alla noder i Y ska ha t som granne
		for (int i = 1; i <= x; i++) G.addEdge(0, i);
		for (int j = x+1; j <= x+y; j++) G.addEdge(j, x+y+1);
		// G är nu en flödesgraf
    }

	// Från G skapar vi, mha MaxFlow, restflödesgrafen som vi sedan ger till edmondkarp
	public FlowGraph createFlowGraph() {
		int v = G.getVertices();
		int s = 0;
		int t = G.getVertices()-1;
		int e = G.getEdges();

        FlowGraph FG = new FlowGraph(v, s, t, e);
		for (int i = 0; i < G.getAdjList().size(); i++) {
			for (int j = 0; j < G.getAdjList().get(i).size(); j++) {
				Edge edge = G.getAdjList().get(i).get(j);
				int a = edge.getSource();
				int b = edge.getTarget();
				int c = edge.getCapacity();
				FG.addEdge(a, b, c);
			}
		}
		MaxFlow mf = new MaxFlow();
		mf.edmondKarp(FG);
		return FG;
	}

    int readMaxFlowSolution(FlowGraph FG) {
		int v = FG.getVertices();
		int s = FG.getSource();
		int t = FG.getSink();
		int totflow = FG.getFlow();
		int e = FG.getEdges();

		// System.out.println(totflow);

		maxMatchEdges = new Edge[totflow];
		int k = 0;
		for (int i = 0; i < FG.getAdjList().size(); i++) {
			for (int j = 0; j < FG.getAdjList().get(i).size(); j++) {
				Edge edge = FG.getAdjList().get(i).get(j);
				int a = edge.getSource();
				int b = edge.getTarget();
				int f = edge.getFlow();
				if (a != s && a != t && b != s && b != t && f > 0) {
					Edge edge1 = new Edge(a, b);
					maxMatchEdges[k] = edge1;
					k++;
				}
			}
		}
		return totflow;
    }


    void writeBipMatchSolution(int totflow) {
		int maxMatch = totflow;

		// Skriv ut antal hörn och storleken på matchningen
		io.println(G.getX() + " " + G.getY());
		io.println(maxMatch);

		for (int i = 0; i < maxMatch; ++i) {
			int a = maxMatchEdges[i].getTarget();
			int b = maxMatchEdges[i].getCapacity();
			// Kant mellan a och b ingår i vår matchningslösning
			io.println(a + " " + b);
		}
    }


	// Constructor
    BipRed() {
		io = new Kattio(System.in, System.out);
		readBipartiteGraph();

		FlowGraph FG = createFlowGraph();

		int totflow = readMaxFlowSolution(FG);

		writeBipMatchSolution(totflow);

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}
}

