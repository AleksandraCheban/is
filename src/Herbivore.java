import java.util.concurrent.ThreadLocalRandom;

public abstract class Herbivore extends Animal {
    public Herbivore(double weight, int maxPerCell, int maxSpeed, double foodNeeded, String unicodeSymbol) {
        super(weight, maxPerCell, maxSpeed, foodNeeded, unicodeSymbol);
    }

    @Override
    public void eat() {
        if (!location.getPlants().isEmpty()) {
            Plant plant = location.getPlants().get(0);
            satiety = Math.min(satiety + plant.getWeight(), foodNeeded);
            plant.die();
        }
    }

    @Override
    public void reproduce() {
        if (satiety < foodNeeded * 0.5) return;

        long potentialMates = location.getAnimals().stream()
                .filter(a -> a.getClass() == this.getClass())
                .count();

        if (potentialMates >= 2) {
            int offspringCount = ThreadLocalRandom.current().nextInt(4) + 1;
            for (int i = 0; i < offspringCount; i++) {
                try {
                    Animal offspring = this.getClass().getConstructor().newInstance();
                    location.addAnimal(offspring);
                } catch (Exception e) {
                    System.err.println("Error creating offspring: " + e.getMessage());
                }
            }
        }
    }
}
