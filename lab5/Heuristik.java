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
		for (int i = 1; i <= v; i++) {
			//Kolla om vi kan återanvända
			for(int j = 1; j <= setActorRoles.size(); j++){
				if(canPlayRole(j, i)){
					ArrayList<Integer> role = setActorRoles.get(j);
					role.add(i);
					roleIsSet[i] = true;
					break;
				}
			}
			//Kolla bland icke-tilldelade skådespelare
			int size = roleActors.get(i).size();	
			for(int k = 1; k <= size; k++){
				if(canPlayRole(k, i)){
					ArrayList<Integer> role = setActorRoles.get(k);
					role.add(i);
					roleIsSet[i] = true;
					break;
				}
			}
			ss++;
			setActorRoles.put(ss, new ArrayList<Integer>());
			ArrayList<Integer> role = setActorRoles.get(ss);
			role.add(i);
			roleIsSet[i] = true;
		}

		io.close();
	}
	//diva1 -> diva2          diva2 -> diva1
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
				break;
			}
		}
		/*
		if (!diva2set) {
			setActorRoles.remove(1);
			roleIsSet[divaOneRole] = false;
			setDivas();
		}		
		*/
	}

	static boolean canPlayRole(int actor, int role) {
		if (roleIsSet[role] == true)
			return false;

		for (int scene = 1; scene <= v; scene++) {
			//Specialfall för divor. Om diva redan har roll i denna scen + check om andra divan spelar i samma scen
			if (actor == 1 || actor == 2) {
				int otherDiva = (actor == 1) ? 2 : 1;
				for (int roleOtherDiva : setActorRoles.get(otherDiva)) {
					if (sceneRoles.get(scene).contains(roleOtherDiva) && sceneRoles.get(scene).contains(role));
					return false;
				}
			}
			//Om actor redan har roll i denna scen
			for (int roles : setActorRoles.get(actor)) {
				if (sceneRoles.get(scene).contains(roles) && sceneRoles.get(scene).contains(role))
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