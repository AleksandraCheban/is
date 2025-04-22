public class River extends Location {
    public River(int x, int y, Island island) {
        super(x, y, island);
    }

    @Override
    public void addAnimal(Animal animal) {

    }

    @Override
    public void removeAnimal(Animal animal) {

    }

    @Override
    public boolean canMoveTo(Animal animal) {
        return false; // Животные не могут перемещаться на реку
    }

    @Override
    public String getUnicodeSymbol() {
        return "🌊";
    }
}
