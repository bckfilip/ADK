import java.util.*;

public class Heuristik {

	/**
	 * Input: V - roller E - scener M - Skådespelare Villkor typ 1 - Möjliga
	 * skådespelare för roll Villkor typ 2 - Roller i scen
	 */
	static int v, e, m, ss;
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
		ss = m;

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
		for (int roll = 1; roll <= v; roll++) {

			// Kolla om vi kan återanvända. actor=1 pga key
			for (int actor = 1; actor <= setActorRoles.size(); actor++) {
				if (canPlayRole(actor, roll)) {
					ArrayList<Integer> role = setActorRoles.get(actor);
					role.add(roll);
					roleIsSet[roll] = true;
					break;
				}
			}
			// Kolla bland icke-tilldelade skådespelare. actor=0 pga value
			ArrayList<Integer> actors = roleActors.get(roll);
			for (int actor = 0; actor < actors.size(); actor++) {
				int skådis = actors.get(actor);
				if (canPlayRole(skådis, roll)) {
					ArrayList<Integer> role = setActorRoles.get(skådis);
					role.add(roll);
					roleIsSet[roll] = true;
					break;
				}
			}
			ss++;
			setActorRoles.put(ss, new ArrayList<Integer>());
			ArrayList<Integer> roles = setActorRoles.get(ss);
			roles.add(roll);
			roleIsSet[roll] = true;
		}
	io.close();
	}

	static void setDivas() {
		int divaOneRole = 0;
		// Sätt roll för diva1
		ArrayList<Integer> possibleRoles1 = actorRoles.get(1);
		for (int role1 : possibleRoles1) {
			divaOneRole = role1;
			ArrayList<Integer> roles = setActorRoles.get(1);
			roles.add(role1);
			roleIsSet[role1] = true;
			// Sätt roll för diva2
			ArrayList<Integer> possibleRoles2 = actorRoles.get(2);
			for (int role2 : possibleRoles2) {
				if (canPlayRole(2, role2)) {
					ArrayList<Integer> roll = setActorRoles.get(2);
					roll.add(role2);
					roleIsSet[role2] = true;
					return;
				}
			}
			setActorRoles.remove(1);
			setActorRoles.put(1, new ArrayList<Integer>());
			roleIsSet[divaOneRole] = false;
			divaOneRole = 0;
		}
	}

	static boolean canPlayRole(int actor, int role) {
		if (roleIsSet[role])
			return false;

		for (int scene = 1; scene <= e; scene++) {
			// Specialfall för divor. Om diva redan har roll i denna scen + check om andra
			// divan spelar i samma scen
			if (actor == 1 || actor == 2) {
				int otherDiva = (actor == 1) ? 2 : 1;
				ArrayList<Integer> roles = setActorRoles.get(otherDiva);
				for (int roll : roles) {
					ArrayList<Integer> scen = sceneRoles.get(scene);
					if (scen.contains(roll) && scen.contains(role));
						return false;
				}
			}
			// Om actor redan har roll i denna scen
			ArrayList<Integer> roles = setActorRoles.get(actor);
			for (int roll : roles) {
				ArrayList<Integer> scen = sceneRoles.get(scene);
				if (scen.contains(roll) && scen.contains(role))
					return false;
			}
		}
		return true;
	}

	public static void main(String args[]) {
		cast();
		System.out.println("hallå");
	}
}