import java.util.*;

public class Heuristik {
	/**
	 * Input: V - roller E - scener M - Skådespelare Villkor typ 1 - Möjliga
	 * skådespelare för roll Villkor typ 2 - Roller i scen
	 */
	static Kattio io;
	static int v, e, m, ss;
	static HashMap<Integer, Set<Integer>> actorRoles;
	static HashMap<Integer, Set<Integer>> roleScenes;
	static HashMap<Integer, Set<Integer>> roleActors;
	static HashMap<Integer, Set<Integer>> sceneRoles;
	// Fyll med angivna roller. Key=skådespelare, value=roller.
	static HashMap<Integer, Set<Integer>> setActorRoles;
	// True om rollen är satt.
	static boolean[] roleIsSet;

	static void cast() {
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
			roleScenes.put(i, new HashSet<Integer>());
			roleActors.put(i, new HashSet<Integer>());
		}
		for (int i = 1; i <= e; i++) {
			sceneRoles.put(i, new HashSet<Integer>());
		}
		for (int i = 1; i <= m; i++) {
			actorRoles.put(i, new HashSet<Integer>());
			setActorRoles.put(i, new HashSet<Integer>());
		}
		// Villkor av typ 1: Vilka skådisar som kan spela varje roll
		for (int i = 1; i <= v; i++) {
			int amount = io.getInt();
			for (int j = 1; j <= amount; j++) {
				int skådis = io.getInt();
				Set<Integer> actors = roleActors.get(i);
				actors.add(skådis);
				Set<Integer> roles = actorRoles.get(skådis);
				roles.add(i);
			}
		}
		// Villkor av typ 2: Vilka roller som är med i varje scen
		for (int i = 1; i <= e; i++) {
			int amount = io.getInt();
			for (int j = 1; j <= amount; j++) {
				int roll = io.getInt();
				Set<Integer> roles = sceneRoles.get(i);
				roles.add(roll);
				Set<Integer> scenes = roleScenes.get(roll);
				scenes.add(i);
			}
		}
		/**
		 * Ansätt roller. 1 = diva1, 2 = diva2. För varje roll : kolla om vi kan
		 * återanvända Annars placera ut ny annars superskådis
		 */
		setDivas();
		for (int roll = 1; roll <= v; roll++) {
			if (roleIsSet[roll])
				continue;

			boolean found = false;
			boolean found2 = false;	
			// Kolla om vi kan återanvända. actor=1 pga key
			for (int actor = 1; actor <= m; actor++) {
				if (canPlayRole(actor, roll)) {
					Set<Integer> role = setActorRoles.get(actor);
					role.add(roll);
					roleIsSet[roll] = true;
					found = true;
					break;
				}
			}
			// Kolla bland icke-tilldelade skådespelare. actor=0 pga value
			if(!found){
				Set<Integer> actors = roleActors.get(roll);
				for (int skådis : actors) {
					if (canPlayRole(skådis, roll)) {
						Set<Integer> role = setActorRoles.get(skådis);
						role.add(roll);
						roleIsSet[roll] = true;
						found2 = true;
						break;
					}				
				}
			}
			if(!found && !found2){
				ss++;
				setActorRoles.put(ss, new HashSet<Integer>());
				Set<Integer> roles = setActorRoles.get(ss);
				roles.add(roll);
				roleIsSet[roll] = true;
			}
		}
	}

	static void setDivas() {
		int divaOneRole = 0;
		// Sätt roll för diva1
		Set<Integer> possibleRoles1 = actorRoles.get(1);
		for (int role1 : possibleRoles1) {
			divaOneRole = role1;
			Set<Integer> roles = setActorRoles.get(1);
			roles.add(role1);
			roleIsSet[role1] = true;
			// Sätt roll för diva2
			Set<Integer> possibleRoles2 = actorRoles.get(2);
			for (int role2 : possibleRoles2) {
				if (canPlayRole(2, role2)) {
					Set<Integer> roll = setActorRoles.get(2);
					roll.add(role2);
					roleIsSet[role2] = true;
					return;
				}
			}
			setActorRoles.remove(1);
			setActorRoles.put(1, new HashSet<Integer>());
			roleIsSet[divaOneRole] = false;
			divaOneRole = 0;
		}
	}

	static boolean canPlayRole(int actor, int role) {
		if (roleIsSet[role] || !actorRoles.get(actor).contains(role))
			return false;

		Set<Integer> scenes = roleScenes.get(role);
		for(int scene : scenes) {
			// Specialfall för divor. Om diva redan har roll i denna scen + check om andra
			// divan spelar i samma scen
			if (actor == 1 || actor == 2) {
				int otherDiva = (actor == 1) ? 2 : 1;
				Set<Integer> roles = setActorRoles.get(otherDiva);
				for (int roll : roles) {
					Set<Integer> scen = sceneRoles.get(scene);
					if (scen.contains(roll)){
						return false;
					}
				}
			}
			// Om actor redan har roll i denna scen
			Set<Integer> roles = setActorRoles.get(actor);
			for (int roll : roles) {
				Set<Integer> rolesInScene = sceneRoles.get(scene);
				if (rolesInScene.contains(roll)){
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String args[]) {
		cast();
		StringBuilder str = new StringBuilder();
		int setActors = 0;
		int size = setActorRoles.size();
		for(int i = 1; i <= size; i++){
			Set<Integer> roles = setActorRoles.get(i);
			if(roles.size() == 0){
				continue;
			}else{
				setActors++;
				str.append(i + " " + roles.size() + " ");
				for(int role : roles){
					str.append(role + " ");
				}
				str.append("\n");
			}
		}
		System.out.println(setActors);
		System.out.println(str.toString());		
	}
}