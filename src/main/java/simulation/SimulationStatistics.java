package simulation;

public record SimulationStatistics (
    int noOfAnimals,
    int noOfPlants,
    double meanLifeLength,
    double meanNumberOfChildren,
    double meanEnergy,
    int dayNumber
) {}
