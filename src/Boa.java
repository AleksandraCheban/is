public class Boa extends Predator {
    public Boa() {
        super(15, 30, 1, 3, "🐍");
        preyChances.put(Rabbit.class, 40);
        preyChances.put(Mouse.class, 80);
    }
}
