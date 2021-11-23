package simulation;

public class Simulation {
    private static final IWorldMap worldMap;

    static {
        worldMap = new WorldMap(
                SimulationParams.getField("width"),
                SimulationParams.getField("height"),
                SimulationParams.getField("noOfAnimals"),
                SimulationParams.getField("noOfPlants"),
                SimulationParams.getField("animalEnergy"),
                SimulationParams.getField("plantEnergy")
        );
    }

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
