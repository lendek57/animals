package simulation;

public class Simulation {
    private static final int WIDTH = 20, HEIGHT = 30;
    private static final IWorldMap worldMap = new WorldMap(WIDTH, HEIGHT);

    public static IWorldMap getWorldMap() {
        return worldMap;
    }

    public static void simulateDay() {
        worldMap.run();
        worldMap.eat();
        worldMap.reproduce();
        worldMap.atTheEndOfDay();
    }
}
