public class Eagle extends Predator {
    public Eagle() {
        super(6, 20, 3, 1, "🦅");
        preyChances.put(Rabbit.class, 20);
        preyChances.put(Mouse.class, 90);
        preyChances.put(Fox.class, 10);
    }
}
