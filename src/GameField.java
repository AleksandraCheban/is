public class GameField {
    private Location[][] field;
    private Island island;

    public GameField(int width, int height, Island island) {
        this.island = island;
        field = new Location[width][height];
        initializeField();
    }

    private void initializeField() {
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field[x].length; y++) {
                if (isRiver(x, y)) {
                    field[x][y] = new River(x, y, island); // Используйте поле island
                } else {
                    field[x][y] = new Location(x, y, island); // Используйте поле island
                }
            }
        }
    }

    private boolean isRiver(int x, int y) {
        // Логика определения, является ли локация рекой
        return y == field[x].length / 2;
    }

    public Location getLocation(int x, int y) {
        return field[x][y];
    }
}
