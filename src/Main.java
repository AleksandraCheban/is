public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");


        Island island = new Island(100, 20);
        IslandSimulation simulation = new IslandSimulation(island);
        simulation.start();


        Runtime.getRuntime().addShutdownHook(new Thread(simulation::stop));


        try {
            Thread.sleep(20000); // Ждем 20 секунд
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        simulation.stop();
    }
}
