package simulation;

import java.util.Random;

public class Animal implements Comparable<Animal> {
	private Vector2D position;
	private int energy;
	private int age = 1;
	private final Genome genome;
	private int numberOfChildren = 0;
	private final int animalId;
	private static int counter = 0;

	public Animal(Vector2D position, int energy) {
		this.animalId = counter++;
		this.position = position;
		this.energy = energy;
		this.genome = new Genome();
	}

	public Animal(Animal mother, Animal father) {
		this.animalId = counter++;
		Vector2D move = MapDirection.values()[new Random().nextInt(MapDirection.values().length)].getUnitVector();
		position = pbc(mother.getPosition().add(move));
		energy = (mother.getEnergy() + father.getEnergy()) / 4;
		genome = new Genome(mother.getGenome(), father.getGenome());
		mother.setEnergy(3 * mother.getEnergy() / 4);
		father.setEnergy(3 * father.getEnergy() / 4);
		mother.increaseNumberOfChildren();
		father.increaseNumberOfChildren();
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void increaseNumberOfChildren() {
		numberOfChildren++;
	}

	public Genome getGenome() {
		return genome;
	}

	public Vector2D getPosition() {
		return position;
	}

	public int getEnergy() {
		return energy;
	}

	public Animal setEnergy(int newEnergy) {
		energy = newEnergy;
		return this;
	}

	public int getAge() {
		return age;
	}

	public Animal aging() {
		age++;
		return this;
	}

	public int getAnimalId() {
		return animalId;
	}

	public void move(MapDirection direction) {
		position = pbc(position.add(direction.getUnitVector()));
		System.out.println("Animal " + animalId + " moves " + direction + ": new position: " + position
				+ ": energy level: " + energy + ": age: " + age);
	}

	public void moveBasedOnGenome() {
		move(genome.getRandomMove());
	}

	private Vector2D pbc(Vector2D position) {
		int width = Simulation.getWorldMap().getWidth();
		int height = Simulation.getWorldMap().getHeight();
		if (position.x() < 0) position = position.add(new Vector2D(width, 0));
		if (position.x() >= width) position = position.subtract(new Vector2D(width, 0));
		if (position.y() < 0) position = position.add(new Vector2D(0, height));
		if (position.y() >= height) position = position.subtract(new Vector2D(0, height));

		return position;
	}

	public int compareTo(Animal animal) {
		return animal.getEnergy() == energy ? animalId - animal.getAnimalId() : energy - animal.getEnergy();
	}
}
