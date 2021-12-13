package simulation;

public class Simulation {
    private static final IWorldMap worldMap = new WorldMap();

    public static IWorldMap getWorldMap() {
        return worldMap;
    }

    public static void simulateDay() {
        worldMap.run();
        worldMap.eat();
        worldMap.reproduce();
        worldMap.atTheEndOfDay();
    }

    public static void setSimulation() {
        worldMap.setSimulation();
    }
}
