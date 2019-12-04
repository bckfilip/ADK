import java.util.*;

public class Heuristik {

	/**
	 * Input: V - roller E - scener M - Skådespelare Villkor typ 1 - Möjliga
	 * skådespelare för roll Villkor typ 2 - Roller i scen
	 */
	static int v, e, m;
	static HashMap<Integer, ArrayList<Integer>> actorRoles;
	static HashMap<Integer, ArrayList<Integer>> roleScenes;
	static HashMap<Integer, ArrayList<Integer>> roleActors;
	static HashMap<Integer, ArrayList<Integer>> sceneRoles;
	// Fyll med angivna roller. Key=skådespelare, value=roller.
	static HashMap<Integer, ArrayList<Integer>> setActorRoles;
	// True om rollen är satt.
	static boolean[] roleIsSet;

	static void cast() {
		Kattio io;
		io = new Kattio(System.in, System.out);
		v = io.getInt(); // roller
		e = io.getInt(); // scener
		m = io.getInt(); // skådisar(divor inräknade). Inkrementera för superskådisar

		actorRoles = new HashMap<>();
		roleScenes = new HashMap<>();
		roleActors = new HashMap<>();
		sceneRoles = new HashMap<>();
		setActorRoles = new HashMap<>();
		roleIsSet = new boolean[v + 1];

		// Fyller maps med listor
		for (int i = 1; i <= v; i++) {
			roleScenes.put(i, new ArrayList<Integer>());
			roleActors.put(i, new ArrayList<Integer>());
		}
		for (int i = 1; i <= e; i++) {
			sceneRoles.put(i, new ArrayList<Integer>());
		}
		for (int i = 1; i <= m; i++) {
			actorRoles.put(i, new ArrayList<Integer>());
			setActorRoles.put(i, new ArrayList<Integer>());
		}
		// Villkor av typ 1: Vilka skådisar som kan spela varje roll
		for (int i = 1; i <= v; i++) {
			int amount = io.getInt();
			for (int j = 1; j <= amount; j++) {
				int skådis = io.getInt();
				ArrayList<Integer> actors = roleActors.get(i);
				actors.add(skådis);
				ArrayList<Integer> roles = actorRoles.get(skådis);
				roles.add(i);
			}
		}
		// Villkor av typ 2: Vilka roller som är med i varje scen
		for (int i = 1; i <= e; i++) {
			int amount = io.getInt();
			for (int j = 1; j <= amount; j++) {
				int roll = io.getInt();
				ArrayList<Integer> roles = sceneRoles.get(i);
				roles.add(roll);
				ArrayList<Integer> scenes = roleScenes.get(roll);
				scenes.add(i);
			}
		}
		/**
		 * Ansätt roller. 1 = diva1, 2 = diva2. För varje roll : kolla om vi kan
		 * återanvända Annars placera ut ny annars superskådis
		 */
		setDivas();
		for (int i = 1; i <= v; i++) {

		}

		io.close();
	}

	static void setDivas() {
		int divaOneRole = 0;
		boolean diva2set = false;

		// Sätt roll för diva1
		for (int i : actorRoles.get(1)) {
			if (canPlayRole(1, i)) {
				divaOneRole = i;
				ArrayList<Integer> roles = setActorRoles.get(1);
				roles.add(i);
				roleIsSet[i] = true;
				break;
			}
		}
		// Sätt roll för diva2
		for (int i : actorRoles.get(2)) {
			if (canPlayRole(2, i)) {
				ArrayList<Integer> roles = setActorRoles.get(2);
				roles.add(i);
				roleIsSet[i] = true;
				diva2set = true;
			}
		}
		// Vettefan om detta funkar
		if (!diva2set) {
			setActorRoles.remove(1);
			roleIsSet[divaOneRole] = false;
			setDivas();
		}
	}

	static boolean canPlayRole(int actor, int role) {
		if (roleIsSet[role] == true)
			return false;

		for (int scene = 1; scene <= v; scene++) {
			if (actor == 1 || actor == 2) {
				int otherDiva = (actor == 1) ? 2 : 1;
				for (int roleOtherDiva : setActorRoles.get(otherDiva)) {
					if (sceneRoles.get(scene).contains(roleOtherDiva) && sceneRoles.get(scene).contains(role));
					return false;
				}
			}
			for (int roles : setActorRoles.get(actor)) {
				if (sceneRoles.get(scene).contains(roles) && sceneRoles.get(scene).contains(role))
					return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		cast();
	}
}