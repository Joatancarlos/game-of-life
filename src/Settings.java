public class Settings {
    private final int height;
    private final int width;
    private final int speed;
    private final String initialPopulation;
    private final int typeNeighborhood;

    public Settings(
            int height,
            int width,
            int speed,
            String initialPopulation,
            int typeNeighborhood
    ) {
        this.height = height;
        this.width = width;
        this.speed = speed;
        this.initialPopulation = initialPopulation;
        this.typeNeighborhood = typeNeighborhood;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSpeed() {
        return speed;
    }

    public String getInitialPopulation() {
        return initialPopulation;
    }

    public int getTypeNeighborhood() {
        return typeNeighborhood;
    }
}
