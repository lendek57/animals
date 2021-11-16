package simulation;

import java.util.*;
import java.util.stream.Collectors;

public class WorldMap extends AbstractWorldMap {
    private static final int ANIMALS_NUM = 1000, PLANTS_NUM = 200;
    private static final int INITIAL_ENERGY = 20;
    private static final int PLANT_ENERGY = 10;

    private int dayNumber = 1;
    private List<Animal> animals = new ArrayList<>();
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
        System.out.println("Today is day number " + dayNumber);
        animalsPositions.clear();
        animals.forEach(animal -> {
            animal.move(MapDirection.values()[this.random.nextInt(MapDirection.values().length)]);
            placeAnimalOnMap(animal);
        });
    }

    private void placeAnimalOnMap(Animal animal) {
        animalsPositions
                .computeIfAbsent(animal.getPosition(), k -> new LinkedList<>())
                .add(animal);
    }

    public void eat() {
        animalsPositions.forEach(this::eatPlantAtPosition);
    }

    private void eatPlantAtPosition(Vector2D position, List<Animal> animals) {
        if (isOccupiedByPlant(position)) {
            animals
                    .stream()
                    .max(Animal::compareTo)
                    .ifPresent((animal -> {
                        animal.setEnergy(animal.getEnergy() + PLANT_ENERGY);
                        plants.remove(animal.getPosition());
                        System.out.println("Animal " + animal.getAnimalId() + " ate plant at position " + animal.getPosition());
                        addNewPlant();
                    }));
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

    @Override
    public void atTheEndOfDay() {
        animals = animals.stream()
                .map(Animal::aging)
                .map(animal -> animal.setEnergy(animal.getEnergy() - 1))
                .filter(animal -> animal.getEnergy() > 0)
                .collect(Collectors.toList());
        this.dayNumber++;
    }
}
