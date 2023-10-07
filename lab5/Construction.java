import java.util.*;

public class Construction {
	// Key = role, value = possible actors for that role
	HashMap<Integer, ArrayList<Integer>> roleActors = new HashMap<>();
	// Key = scene, value = roles in that scene
	HashMap<Integer, ArrayList<Integer>> sceneRoles = new HashMap<>();
	// Key = role, value = list of scenes that role is in
	HashMap<Integer, ArrayList<Integer>> roleScenes = new HashMap<>();
	// Key = actor, value = assigned roles
	HashMap<Integer, ArrayList<Integer>> output = new HashMap<>();
	int superActors;
	ArrayList<Role> theRoles = new ArrayList<>();
	Kattio io;


	public class Role {
		int id;
		int actor;
		ArrayList<Integer> scenes;

		public Role(int id) {
			this.id = id;
			actor = 0;
			scenes = new ArrayList<Integer>();
		}

		public void setActor(int actor) {
			this.actor = actor;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getActor() {
			return actor;
		}

		public int getId() {
			return id;
		}

		public ArrayList<Integer> getScenes() {
			return scenes;
		}
	}


	public static void main(String[] args) {
		new Construction();
	}


	public void initLists(int roles, int scenes, int actors) {
		for (int i = 1; i <= roles; i++) {
			roleActors.put(i, new ArrayList<>());
			roleScenes.put(i, new ArrayList<>());
		}

		for (int i = 1; i <= scenes; i++) {
			sceneRoles.put(i, new ArrayList<>());
		}

		for (int i = 1; i <= actors + roles - 1; i++) {
			output.put(i, new ArrayList<>());
		}
	}


	public Construction() {
		input();
/* 		for (int role = 0; role < theRoles.size(); role++) {
			if (isPossible(role+1, 2)) {
				output.get(2).add(role+1);
				theRoles.get(role).setActor(2);
				break;
			}
		} */
		// Heuristic: loop through the possible actors for a role, assign the first possible one.
		for (int role = 1; role <= roleActors.size(); role++) {
			if (theRoles.get(role-1).getActor() == 0) {
				boolean actorFound = false;
				ArrayList<Integer> possibleActors = roleActors.get(role);
				for (int j = 0; j < possibleActors.size(); j++) {
					if (isPossible(role, possibleActors.get(j))) {
						actorFound = true;
						output.get(possibleActors.get(j)).add(role);
						theRoles.get(role-1).setActor(possibleActors.get(j));
						break;
					}
				}
				if (actorFound == false) {
					output.get(superActors).add(role);
					theRoles.get(role-1).setActor(superActors);
					superActors++;
				}
				// ArrayList<Integer> list = new ArrayList<>();
				// output.put(i, value)
			}
		}

		// Ensure p2 gets a role and does not take the only role that p1 has
		if (output.get(2).isEmpty()) {
			for (int role = 0; role < theRoles.size(); role++) {
				if (isPossible(role+1, 2)) {
					if (theRoles.get(role).getActor() == 1 && output.get(1).size() == 1) continue;
					int a = theRoles.get(role).getActor();
					theRoles.get(role).setActor(2);
					output.get(2).add(role+1);
					for (int i = 0; i < output.get(a).size(); i++) {
						if (output.get(a).get(i) == role+1) output.get(a).remove(i);
					}
				}
			}
		}

/* 		if (output.get(1).isEmpty()) {
			for (int role = 0; role < theRoles.size(); role++) {
				if (isPossible(role+1, 1)) {
					if (theRoles.get(role).getActor() == 2 && output.get(2).size() == 1) continue;
					int a = theRoles.get(role).getActor();
					theRoles.get(role).setActor(1);
					output.get(1).add(role+1);
					for (int i = 0; i < output.get(a).size(); i++) {
						if (output.get(a).get(i) == role+1) output.get(a).remove(i);
					}
				}
			}
		} */

/* 		for (Integer name: roleActors.keySet()) {
			String key = name.toString();
			String value = roleActors.get(name).toString();
			System.out.println(key + " " + value);
		}

		System.out.println();

		for (Integer name: sceneRoles.keySet()) {
			String key = name.toString();
			String value = sceneRoles.get(name).toString();
			System.out.println(key + " " + value);
		} */
		output();
		io.close();
	}

	// Checks if the given role can get assigned to the given actor
	// Possible if the assignment does not lead to 2 or more roles played by the same actor appearing in the same scene
	public boolean isPossible(int role, int actor) {
		ArrayList<Integer> scenes = theRoles.get(role-1).getScenes();
		ArrayList<Integer> actorRoles = output.get(actor); 	// List of roles played by actor
		// ArrayList<Integer> scenes = roleScenes.get(role); 	// Get scenes that role appears in
		for (int i = 0; i < scenes.size(); i++) {
			int scene = scenes.get(i);
			ArrayList<Integer> roles = sceneRoles.get(scene); 	// Get roles in the scene
			for (int j = 0; j < roles.size(); j++) {
				int r = roles.get(j); 	// If r is played by actor we have a problem. If r is played by actor 2 and actor = 1 we have a problem and vice versa.
				Role R = theRoles.get(r-1);
				if ((R.getActor() == 2 && actor == 1) || (R.getActor() == 1 && actor == 2)) {
					return false;
				}
				if (actorRoles.contains(r)) {
					return false;
				}
			}
		}
		return true;
	}


	public void input() {
		io = new Kattio(System.in, System.out);

		int roles = io.getInt();
		int scenes = io.getInt();
		int actors = io.getInt();

		superActors = actors + 1;

		initLists(roles, scenes, actors);

		for (int i = 1; i <= roles; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int actorsForRole = io.getInt();
			for (int j = 1; j <= actorsForRole; j++) {
				int actor = io.getInt();
				list.add(actor);
			}
			roleActors.put(i, list);
			Role R = new Role(i);
			theRoles.add(R);
		}

		for (int i = 1; i <= scenes; i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int rolesInScene = io.getInt();
			for (int j = 1; j <= rolesInScene; j++) {
				int role = io.getInt();
				list.add(role);
			}
			sceneRoles.put(i, list);
		}

		// Create roleScenes
		for (int i = 1; i <= sceneRoles.size(); i++) {
			for (int j = 1; j <= roles; j++) {
				if (sceneRoles.get(i).contains(j)) {
					roleScenes.get(j).add(i);
					theRoles.get(j-1).getScenes().add(i);
				}
			}
		}
	}


	public void output() {
		int a = 0;
		for (int i = 1; i <= output.size(); i++) {
			if (output.get(i).isEmpty()) continue;
			else a++;
		}
		System.out.println(a);
		for (int i = 1; i <= superActors; i++) {
			String r = "";
			for (int j = 0; j < output.get(i).size(); j++) {
				r = r + " " + output.get(i).get(j);
			}
			if (output.get(i).isEmpty()) continue;
			else System.out.println(i + " " + output.get(i).size() + "" + r);
		}
	}
}
