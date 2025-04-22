public class Fox extends Predator {
    public Fox() {
        super(8, 30, 2, 2, "🦊");
        preyChances.put(Rabbit.class, 70);
        preyChances.put(Mouse.class, 90);
        preyChances.put(Duck.class, 60);
    }
}
