import java.util.ArrayList;

public class Reduction {
	private Kattio io;
	private ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private Node[] nodes;


	public static void main(String[] args) {
		new Reduction();
	}


	public Reduction() {
		io = new Kattio(System.in, System.out);

		// Instans av graffärgningsproblemet. Läs antal hörn, kanter och målet.
		int v = io.getInt();
		int e = io.getInt();
		int m = io.getInt();

		// Optimering, i fallet då m är större än v kommer det alltid att gå
		if (m >= v) {
			createYesInstance();
		} else {
			nodes = new Node[3*v];

			createNodesAndEdges(v, e);

			e = edgeList.size();
			m += 2;

			possibleActors(v, m);

			v = nodes.length;
			output(v, e, m);

			io.close();
		}
	}


	public void createNodesAndEdges(int v, int e) {
		// Skapa befintliga noder samt nya noder med kanter mellan dem
		for (int i = 1; i <= v; ++i) {
			// Två nya noder per befintlig
			Node existingNode = new Node(i);
			Node newNode1 = new Node(v+i);
			Node newNode2 = new Node(2*v+i);

			nodes[i-1] = existingNode;
			nodes[v+i-1] = newNode1;
			nodes[2*v+i-1] = newNode2;

			// Alla nya roller kan spelas av skådespelare 1 eller 2 (divorna)
			newNode1.addActor(1);
			newNode1.addActor(2);
			newNode2.addActor(1);
			newNode2.addActor(2);

			// Kant från befintliga noder till de nya
			edgeList.add(new Edge(existingNode.getRole(), newNode1.getRole()));
			edgeList.add(new Edge(existingNode.getRole(), newNode2.getRole()));
		}

		// Läs in och skapa de befintliga kanterna
		for (int i = 0; i < e; ++i) {
			int a = io.getInt();
			int b = io.getInt();

			edgeList.add(new Edge(a, b));
		}
	}


	// Lägg till möjliga skådespelare för de gamla rollerna
	public void possibleActors(int v, int m) {
		for (int i = 0; i < v; i++) {
			// Minsta antalet färger en graf kan färgas med är det kromatiska talet som är <= maxvalensen för grafen +1.
			// Det räcker därmed att räkna upp till maxvalensen för grafen +1.
			// Alla nya noder kommer ha valens 1. De befintliga noderna kan som mest ha kant till alla andra befintliga noder +2 för de nya noderna - dvs. v-1+2 = v+1.
			// Skippa skådespelare 1 och 2 (divorna)
			for (int j = 3; j <= v+3; j++) {
				if (j > m) break;
				nodes[i].getActors().add(j);
			}
		}
	}


	// Printar instans av rollbesättningsproblemet
	public void output(int n, int s, int k) {
		System.out.println(n);
		System.out.println(s);
		System.out.println(k);
		for (int i = 0; i < n; i++) {
			// Antalet möjliga skådespelare för rollen, lista av dessa skådespelare
			System.out.print(nodes[i].getActors().size());
			for (int j = 0; j < nodes[i].getActors().size(); j++) {
				System.out.print(" " + nodes[i].getActors().get(j));
			}
			System.out.println();
		}
		for (int j = 0; j < s; j++) {
			// Antalet roller i scenen, lista av dessa roller
			System.out.println("2 " + edgeList.get(j).getSource() + " " + edgeList.get(j).getTarget());
		}
	}


	// Minsta möjliga produktion, standard ja-instans för fallen då m är större eller lika med v
	public void createYesInstance() {
		System.out.println(3);
		System.out.println(2);
		System.out.println(3);
		System.out.println("1 " + "1");
		System.out.println("1 " + "2");
		System.out.println("1 " + "3");
		System.out.println("2 " + "1 " + "3");
		System.out.println("2 " + "2 " + "3");
		}
	}
