package simulation;

public record SimulationStatistics (
    int noOfAnimals,
    int noOfPlants,
    double meanLifeLength,
    double meanNumberOfChildren,
    double meanEnergy,
    int dayNumber
) {
    @Override
    public String toString() {
        return "Number of animals: " + noOfAnimals + "\n"
                + "Number of plants: " + noOfPlants + "\n"
                + "Mean age: " + formatNumber(meanLifeLength) + "\n"
                + "Mean number of children: " + formatNumber(meanNumberOfChildren) + "\n"
                + "Mean energy: " + formatNumber(meanEnergy) + "\n"
                + "Day number: " + dayNumber + "\n";
    }

    private String formatNumber(double number) {
        return String.format("%.2f", number);
    }
}
