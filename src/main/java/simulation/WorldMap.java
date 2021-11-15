package simulation;

import java.util.*;

public class WorldMap extends AbstractWorldMap {
    private static final int ANIMALS_NUM = 20, PLANTS_NUM = 200;
    private static final int INITIAL_ENERGY = 20;

    private final List<Animal> animals = new ArrayList<>();
    private final Map<Vector2D, List<Animal>> animalsPositions = new HashMap<>();
    private final Map<Vector2D, Plant> plants = new HashMap<>();
    private final Random random = new Random();

    public WorldMap(int width, int height) {
        super(width, height);
        for (int i = 0; i < ANIMALS_NUM; i++) {
            Animal animal = new Animal(getRandomVector(), INITIAL_ENERGY);
            animals.add(animal);
            placeAnimalOnMap(animal);
        }
        for (int i = 0; i < PLANTS_NUM; i++) {
            addNewPlant();
        }
    }

    private Plant getPlantAtPosition(Vector2D position) {
        return plants.get(position);
    }

    @Override
    public void run() {
        animalsPositions.clear();
        for (Animal animal : animals) {
            animal.move(MapDirection.values()[this.random.nextInt(MapDirection.values().length)]);
            placeAnimalOnMap(animal);
        }
    }

    private void placeAnimalOnMap(Animal animal) {
        animalsPositions
                .computeIfAbsent(animal.getPosition(), k -> new LinkedList<>())
                .add(animal);
    }

    public void eat() {
        for (Animal animal : animals) {
            eatPlantAtPosition(animal.getPosition());
        }
    }

    private void eatPlantAtPosition(Vector2D position) {
        Plant plant = getPlantAtPosition(position);
        if (plant != null) {
            this.plants.remove(plant.getPosition());
            System.out.println("Animal ate plant at position " + plant.getPosition());
            addNewPlant();
        }
    }

    private Vector2D getRandomVector() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    private void addNewPlant() {
        Vector2D pos = getRandomVector();
        while (isOccupiedByPlant(pos)) pos = getRandomVector();
        plants.put(pos, new Plant(pos));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        return getPlantAtPosition(position) != null;
    }
}
