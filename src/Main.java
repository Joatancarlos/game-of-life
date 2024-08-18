// Joatan Carlos Farias Feitosa
public class Main {
    public static void main(String[] args) {
        GameConfig config = GameConfig.parseArgs();
        Grid grid = new Grid(config);

        int currentGeneration = 0;
        boolean infiniteGeneration = config.getTotalGeneration() == 0;

        while (infiniteGeneration || currentGeneration < config.getTotalGeneration()) {
            try {
                System.out.printf("Stats: Grid size: [%d x %d] - Speed: [%d ms] - Generation: [%d]%n",
                        config.getHeight(), config.getWidth(), config.getSpeed(), currentGeneration);
                grid.showGrid();
                grid.updateGrid(config.getTypeNeighborhood());
                Thread.sleep(config.getSpeed());
                currentGeneration++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

/*
 * Uma célula viva morre se tiver menos de dois vizinhos vivos.
 * Uma célula viva com dois ou três vizinhos vivos passa para a geração seguinte.
 * Uma célula viva com mais de três vizinhos vivos morre.
 * Uma célula morta voltará a viver se tiver exatamente três vizinhos vivos.
 *
 *                                   A célula está viva?
 *                               /                       \
 *                             Sim                       Não
 *           Ela tem 2 ou 3 vizinhos?              Ela têm exatamente 3 vizinhos?
 *                           /                               \
 *                    Sim        Não                         Revive
 *                   /               \
 * Continua viva.                    Morre.
 * Para verificar cada vizinho, é bom criar um contador de vizinhos da célula. Dessa forma, fica mais fácil de aplicar as regras do GoL
 * */