import java.util.*;

public class Heuristik{

  /**
   * Input:
   * V - roller
   * E - scener
   * M - Skådespelare
   * Villkor typ 1 - Möjliga skådespelare för roll
   * Villkor typ 2 - Roller i scen
   */

  static void cast(){
  Kattio io;
  io = new Kattio(System.in, System.out);
  //Struktur för maps: namnet anger input/output. actorRoles = <Actor, roles[]>
  HashMap<Integer, ArrayList<Integer>> actorRoles = new HashMap<>();
  HashMap<Integer, ArrayList<Integer>> roleScenes = new HashMap<>();
  HashMap<Integer, ArrayList<Integer>> roleActors = new HashMap<>();
  HashMap<Integer, ArrayList<Integer>> sceneRoles = new HashMap<>();
  //Fyll med angivna roller. Key=skådespelare, value=roller.
  HashMap<Integer, ArrayList<Integer>> setCast= new HashMap<>();

  int v, e, m;
  v = io.getInt();  //roller
  e = io.getInt();  //scener
  m = io.getInt();  //skådisar(divor inräknade). Inkrementera för superskådisar
  
  //Fyller maps med listor
  for(int i = 1; i <= v; i++){
    roleScenes.put(i, new ArrayList<Integer>());
    roleActors.put(i, new ArrayList<Integer>());
  }
  for(int i = 1; i <= e; i++){
    sceneRoles.put(i, new ArrayList<Integer>());
  }
  for(int i = 1; i <= m; i++){
    actorRoles.put(i, new ArrayList<Integer>());
  }
  //Villkor av typ 1: Vilka skådisar som kan spela varje roll
  for(int i = 1; i <= v; i++){
    int amount = io.getInt();
    for(int j = 1; j <= amount; j++){
      int skådis = io.getInt();
      ArrayList<Integer> actors = roleActors.get(i);
      actors.add(skådis);
      ArrayList<Integer> roles = actorRoles.get(skådis);
      roles.add(i);
    }
  }
  //Villkor av typ 2: Vilka roller som är med i varje scen
  for(int i = 1; i <= e; i++){
    int amount = io.getInt();
    for(int j = 1; j <= amount; j++){
      int roll = io.getInt();
      ArrayList<Integer> roles = sceneRoles.get(i);
      roles.add(roll);
      ArrayList<Integer> scenes = roleScenes.get(roll);
      scenes.add(i);
    }
  }
  /**
   * Ansätt roller. 1 = diva1, 2 = diva2.
   * För varje roll : kolla om vi kan återanvända
   * Annars placera ut ny
   * annars superskådis
  */



  io.close();
  }
}