public class Wolf extends Predator {
    public Wolf() {
        super(50, 30, 3, 8, "🐺");
        preyChances.put(Rabbit.class, 60);
        preyChances.put(Deer.class, 15);
        preyChances.put(Horse.class, 10);
    }
}
