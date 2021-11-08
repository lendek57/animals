package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldMap extends AbstractWorldMap {
    private static final int ANIMALS_NUM = 10, PLANTS_NUM = 100;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Plant> plants = new ArrayList<>();
    private final Random random = new Random();

    public WorldMap(int width, int height) {
        super(width, height);
        for (int i = 0; i < ANIMALS_NUM; i++) {
            animals.add(new Animal(getRandomVector()));
        }
        for (int i = 0; i < PLANTS_NUM; i++) {
            addNewPlant();
        }
    }

    private Plant getPlantAtPosition(Vector2D position) {
        for (Plant plant : plants) {
            if (plant.getPosition().equals(position)) return plant;
        }
        return null;
    }

    @Override
    public void run() {
        for (Animal animal : animals) {
            animal.move(MapDirection.values()[this.random.nextInt(MapDirection.values().length)]);
        }
    }

    public void eat() {
        for (Animal animal : animals) {
            Plant plant = getPlantAtPosition(animal.getPosition());
            if (plant != null) {
                this.plants.remove(plant);
                System.out.println("Animal ate plant at position " + plant.getPosition());
                addNewPlant();
            }
        }
    }

    private Vector2D getRandomVector() {
        return new Vector2D(random.nextInt(width), random.nextInt(height));
    }

    private void addNewPlant() {
        Vector2D pos = getRandomVector();
        while (isOccupiedByPlant(pos)) pos = getRandomVector();
        plants.add(new Plant(pos));
    }

    private boolean isOccupiedByPlant(Vector2D position) {
        return getPlantAtPosition(position) != null;
    }
}
