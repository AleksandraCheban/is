import java.util.Random;

public class Island {
    private final Location[][] locations;
    private final int width;
    private final int height;
    protected Island island;
    private GameField gameField;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[width][height];
        this.gameField = new GameField(width, height, this); // Передаем три аргумента
        initializeLocations();
        populateIsland();
    }

    private void initializeLocations() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                locations[x][y] = gameField.getLocation(x, y);
            }
        }
    }

    private void populateIsland() {
        Random random = new Random();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Add plants
                for (int i = 0; i < SimulationSettings.INITIAL_PLANTS_PER_LOCATION; i++) {
                    locations[x][y].addPlant(new Plant());
                }


                if (random.nextDouble() < SimulationSettings.INITIAL_ANIMAL_SPAWN_CHANCE) {
                    spawnRandomAnimals(locations[x][y]);
                }
            }
        }
    }

    private void spawnRandomAnimals(Location location) {
        Random random = new Random();
        Animal[] possibleAnimals = {
                new Wolf(), new Boa(), new Fox(), new Bear(), new Eagle(),
                new Horse(), new Deer(), new Rabbit(), new Mouse(), new Goat(),
                new Sheep(), new Boar(), new Buffalo(), new Duck(), new Caterpillar()
        };

        int animalsToSpawn = random.nextInt(5) + 1;
        for (int i = 0; i < animalsToSpawn; i++) {
            Animal animal = possibleAnimals[random.nextInt(possibleAnimals.length)];
            location.addAnimal(animal);
        }
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return locations[x][y];
        }
        return null;
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
