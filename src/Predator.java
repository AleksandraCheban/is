import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Predator extends Animal {
    protected Map<Class<? extends Animal>, Integer> preyChances = new HashMap<>();

    public Predator(double weight, int maxPerCell, int maxSpeed, double foodNeeded, String unicodeSymbol) {
        super(weight, maxPerCell, maxSpeed, foodNeeded, unicodeSymbol);
    }

    @Override
    public void eat() {
        location.getAnimals().stream()
                .filter(a -> preyChances.containsKey(a.getClass()))
                .findFirst()
                .ifPresent(prey -> {
                    int chance = preyChances.get(prey.getClass());
                    if (ThreadLocalRandom.current().nextInt(100) < chance) {
                        satiety = Math.min(satiety + prey.getWeight(), foodNeeded);
                        prey.die();
                    }
                });
    }

    @Override
    public void reproduce() {
        if (satiety < foodNeeded * 0.7) return;

        long potentialMates = location.getAnimals().stream()
                .filter(a -> a.getClass() == this.getClass())
                .count();

        if (potentialMates >= 2) {
            int offspringCount = ThreadLocalRandom.current().nextInt(3) + 1;
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
