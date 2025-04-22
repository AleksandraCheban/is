public class Bear extends Predator {
    public Bear() {
        super(500, 5, 2, 80, "🐻");
        preyChances.put(Rabbit.class, 80);
        preyChances.put(Deer.class, 40);
        preyChances.put(Boar.class, 50);
    }
}
