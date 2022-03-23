import java.util.ArrayList;

/**
 * This class accomplishes Mission Nuke'm
 */
public class DefenseAgainstEnemyTroops {
    private ArrayList<Integer> numberOfEnemiesArrivingPerHour;

    public DefenseAgainstEnemyTroops(ArrayList<Integer> numberOfEnemiesArrivingPerHour){
        this.numberOfEnemiesArrivingPerHour = numberOfEnemiesArrivingPerHour;
    }

    public ArrayList<Integer> getNumberOfEnemiesArrivingPerHour() {
        return numberOfEnemiesArrivingPerHour;
    }

    private int getRechargedWeaponPower(int hoursCharging){
        return hoursCharging*hoursCharging;
    }

    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalEnemyDefenseSolution
     */
    public OptimalEnemyDefenseSolution getOptimalDefenseSolutionDP(){
        // TODO: YOUR CODE HERE

        ArrayList<Integer> sol = new ArrayList<>();
        ArrayList<ArrayList<Integer>> hours = new ArrayList<>();
        ArrayList<Integer> forHours = new ArrayList<>();
        int maxI = 0;
        int lastSol=0;

        for (int j = 0; j<numberOfEnemiesArrivingPerHour.size();j++){
            sol.add(0);
        }
        hours.add(new ArrayList<>());


        for (int j = 1; j<numberOfEnemiesArrivingPerHour.size();j++){
            for (int i = 0; i<j;i++){
                lastSol=sol.get(j);
                sol.set(j,Math.max(sol.get(j),sol.get(i)+Math.min(numberOfEnemiesArrivingPerHour.get(j),getRechargedWeaponPower(j-i))));
                if(sol.get(j)>lastSol){
                    maxI=i;
                }
            }

            forHours.addAll(hours.get(maxI));
            forHours.add(j);
            hours.add(new ArrayList<>(forHours));
            forHours.clear();
        }

        /*for (Integer i : sol){
            System.out.print(i+" ");
        }
        System.out.println();
        for (ArrayList i : hours){
            for (Object j : i) {
                System.out.print(j + " ");
            }
            System.out.print("* ");
        }
        System.out.println();*/

        return new OptimalEnemyDefenseSolution(sol.get(sol.size()-1),hours.get(hours.size()-1));
    }
}
