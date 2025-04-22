import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class LivingEntity {
    protected double weight;
    protected int maxPerCell;
    protected double foodNeeded;
    protected Location location;
    protected double satiety;
    protected Lock lock = new ReentrantLock();

    public abstract void liveCycle();
    public abstract void die();

    public double getWeight() { return weight; }
    public int getMaxPerCell() { return maxPerCell; }
    public void setLocation(Location location) { this.location = location; }
}
