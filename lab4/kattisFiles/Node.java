import java.util.ArrayList;

public class Node {
	private int role;
	private ArrayList<Integer> actors = new ArrayList<>();

	public Node(int role) {
		this.role = role;
	}

	public int getRole() {
		return role;
	}

	public ArrayList<Integer> getActors() {
		return actors;
	}

	public void addActor(int actor) {
		actors.add(actor);
	}
}
