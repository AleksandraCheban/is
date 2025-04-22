import java.util.concurrent.ThreadLocalRandom;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super(0.01, 1000, 0, 0, "🐛");
    }

    @Override
    public void move() {} // Не двигается

    @Override
    public void eat() {
        if (!location.getPlants().isEmpty() &&
                ThreadLocalRandom.current().nextInt(10) == 0) {
            satiety += location.getPlants().get(0).getWeight() * 0.1;
            location.getPlants().get(0).die();
        }
    }

    @Override
    public void reproduce() {
        if (satiety >= foodNeeded * 0.3) {
            int offspringCount = ThreadLocalRandom.current().nextInt(10) + 5;
            for (int i = 0; i < offspringCount; i++) {
                try {
                    location.addAnimal(new Caterpillar());
                } catch (Exception e) {
                    System.err.println("Error creating offspring: " + e.getMessage());
                }
            }
        }
    }
}
