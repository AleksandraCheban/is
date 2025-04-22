import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class IslandSimulation {
    private final Island island;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService animalExecutor;
    private final Lock statisticsLock = new ReentrantLock();
    private volatile boolean isRunning = false;

    // Simulation settings (replace with your actual settings)
    private static final int SIMULATION_TICK_DELAY_MS = 1000;
    private static final int STATISTICS_PRINT_INTERVAL = 5000;
    private static final int PLANT_GROWTH_RATE = 20; // 20% chance to grow a plant

    public IslandSimulation(Island island) {
        this.island = island;
        this.scheduler = Executors.newScheduledThreadPool(3);
        this.animalExecutor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            scheduler.scheduleAtFixedRate(this::growPlants,
                    0, SIMULATION_TICK_DELAY_MS, TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(this::animalLifeCycle,
                    0, SIMULATION_TICK_DELAY_MS, TimeUnit.MILLISECONDS);
            scheduler.scheduleAtFixedRate(this::printStatistics,
                    0, STATISTICS_PRINT_INTERVAL, TimeUnit.MILLISECONDS);
        }
    }

    private void growPlants() {
        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                if (ThreadLocalRandom.current().nextInt(100) < PLANT_GROWTH_RATE) {
                    island.getLocation(x, y).addPlant(new Plant());
                }
            }
        }
    }

    private void animalLifeCycle() {
        List<Future<?>> futures = new ArrayList<>();

        for (int x = 0; x < island.getWidth(); x++) {
            for (int y = 0; y < island.getHeight(); y++) {
                Location location = island.getLocation(x, y);
                for (Animal animal : location.getAnimals()) {
                    futures.add(animalExecutor.submit(animal::liveCycle));
                }
            }
        }

        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error during animal life cycle: " + e.getMessage());
            }
        }
    }

    private void printStatistics() {
        statisticsLock.lock();
        try {
            Map<String, Integer> animalCounts = new HashMap<>();
            int totalPlants = 0;
            int totalAnimals = 0;

            for (int x = 0; x < island.getWidth(); x++) {
                for (int y = 0; y < island.getHeight(); y++) {
                    Location loc = island.getLocation(x, y);
                    totalPlants += loc.getPlants().size();

                    for (Animal animal : loc.getAnimals()) {
                        String name = animal.getClass().getSimpleName();
                        animalCounts.merge(name, 1, Integer::sum);
                        totalAnimals++;
                    }
                }
            }

            System.out.println("\n=== Island Statistics ===");
            System.out.printf("Total plants: %d | Total animals: %d%n", totalPlants, totalAnimals);
            System.out.println("Animals by type:");
            animalCounts.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("%-12s: %d%n", e.getKey(), e.getValue()));

            // Simple console map
            System.out.println("\nIsland map (sample 10x10):");
            for (int y = 0; y < 10; y++) {
                for (int x = 0; x < 10; x++) {
                    Location loc = island.getLocation(x, y);
                    if (!loc.getAnimals().isEmpty()) {
                        System.out.print(loc.getAnimals().get(0).getUnicodeSymbol());
                    } else if (!loc.getPlants().isEmpty()) {
                        System.out.print("🌿");
                    } else {
                        System.out.print("·");
                    }
                }
                System.out.println();
            }
        } finally {
            statisticsLock.unlock();
        }
    }

    public void stop() {
        isRunning = false;
        scheduler.shutdown();
        animalExecutor.shutdown();
        try {
            if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            if (!animalExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                animalExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
            animalExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Simulation stopped");
    }
}
