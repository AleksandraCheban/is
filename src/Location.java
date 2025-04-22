import java.util.ArrayList;
import java.util.List;

public class Location {
    private int x;
    private int y;
    private List<Animal> animals;
    private List<Plant> plants;
    private Island island;

    public Location(int x, int y, Island island) {
        this.x = x;
        this.y = y;
        this.island = island;
        this.animals = new ArrayList<>();
        this.plants = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void addPlant(Plant plant) {
        plants.add(plant);
    }

    public void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public boolean canMoveTo(Animal animal) {
        return true; // По умолчанию можно перемещаться везде
    }

    public List<Location> getAdjacentLocations() {
        // Реализация получения смежных локаций
        return new ArrayList<>();
    }

    public String getUnicodeSymbol() {
        return " "; // Символ для обычной локации
    }
}
