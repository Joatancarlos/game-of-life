// Joatan Carlos Farias Feitosa
public class Main {
    public static void main(String[] args) {
        GameConfig gameConfig = GameConfig.parseArgs(args);

        Grid grid = new Grid(gameConfig);
        int [][] generateGrid = grid.generateGrid();
        int currentGeneretion = 0;

//        Caso o parâmetro seja 0, o while vai rodar infinitamente.
        while (gameConfig.totalGeneration == 0 ? true : currentGeneretion < gameConfig.totalGeneration) {
//              Analisar o grid e verificar as regras
            try {
                System.out.println("Stats: Grid size: [" + gameConfig.height + " x " + gameConfig.width + "] - Speed: [" + gameConfig.speed + " ms] - Generation: [" + currentGeneretion + "] ");
                grid.showGrid(generateGrid);
                System.out.println();
                generateGrid = grid.checkCells(generateGrid, gameConfig.typeNeighborhood);
                Thread.sleep(gameConfig.speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            currentGeneretion++;
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