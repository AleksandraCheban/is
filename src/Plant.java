public class Plant extends LivingEntity {
    public Plant() {
        this.weight = 1;
        this.maxPerCell = 200;
        this.foodNeeded = 0;
        this.satiety = 0;
    }

    @Override
    public void liveCycle() {
        // Растения просто существуют
    }

    @Override
    public void die() {
        if (location != null) {
            location.removePlant(this);
        }
    }
}
