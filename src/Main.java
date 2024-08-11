import java.util.*;

public class Main {
    private static boolean running = true;
    public static void showGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] checkCells(int[][] grid, int type) {
        int[][] neighborsGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int neighbors = 0;
//              Percorre os vizinhos dos eixos i e j
                for(int neighborI = -1; neighborI <= 1; neighborI++) {
                    for(int neighborJ = -1; neighborJ <= 1; neighborJ++) {
//                        verificação do parâmetro "n"
                        switch (type) {
                            case 1:
                                if (neighborI == -1 && neighborJ == -1) continue;
                                if (neighborI == -1 && neighborJ == 1) continue;
                                if (neighborI == 1 && neighborJ == -1) continue;
                                if (neighborI == 1 && neighborJ == 1) continue;
                                break;
                            case 2:
                                if (neighborI == -1 && neighborJ == 1) continue;
                                if (neighborI == 1 && neighborJ == -1) continue;
                                break;
                            case 3:
                                break;
                            case 4:
                                if (neighborI == -1 && neighborJ == 0) continue;
                                if (neighborI == 0 && neighborJ == -1) continue;
                                if (neighborI == 0 && neighborJ == 1) continue;
                                if (neighborI == 1 && neighborJ == 0) continue;
                                break;
                            case 5:
                                if (neighborI == 0 && neighborJ == -1) continue;
                                if (neighborI == 0 && neighborJ == 1) continue;
                                break;
                            default:
                                System.out.println("Wrong type");
                        }

//                      Ignora a célula atual
                        if (neighborI == 0 && neighborJ == 0) continue;
                        int indexNeighborI = i + neighborI;
                        int indexNeighborJ = j + neighborJ;
//                      Verifica se o índice da célula está dentro grid
                        if (indexNeighborI >= 0 && indexNeighborI < grid.length && indexNeighborJ >= 0 && indexNeighborJ < grid[i].length) {
                            if (grid[indexNeighborI][indexNeighborJ] != -1 && grid[indexNeighborI][indexNeighborJ] != 0) {
                                neighbors += 1;
                            }
                        }
                    }
                }
//                Aplicando as regras do GoL para cada célula
                if(grid[i][j] == 1) {
                    if (neighbors < 2 || neighbors > 3) {
                        neighborsGrid[i][j] = 0;
                    } else {
                        neighborsGrid[i][j] = 1;
                    }
                } else if(neighbors == 3) {
                    neighborsGrid[i][j] = 1;
                } else {
                    neighborsGrid[i][j] = 0;
                }
            }
        }
        return neighborsGrid;
    }

    public static String randomInitialPopulation (int height, int width) {
//        O número de '#' tem que ser menor ou igual a height
//        O número de 0's ou 1's tem que ser menor ou igual a width

        Random random = new Random();

        int numHash = random.nextInt(height+1);
        String randomPopulation = "";

        for (int i = 0; i < numHash; i++) {
            int numRow = random.nextInt(width+1);
            for (int j = 0; j < numRow; j++) {
                int cellState = random.nextInt(2);
                randomPopulation += cellState;
            }
            randomPopulation += "#";
        }
        return randomPopulation;
    }

    public static void main(String[] args) {
//        Validando se existem 5 argumentos
//        Definindo valores iniciais para o java não reclamar
        int height = 10;
        int width = 20;
        int totalGeneration = 5;
        int speed = 250;
//      Se não tiver uma população inicial, deve ser gerada randomicamente
        String initialPopulation = "#011##011111";
        int typeNeighborhood = 1;


//      Validar cada argumento
        int count = 0;
        while (count < args.length) {
            switch (count) {
                case 0:
                    int[] validHeight = {10, 20, 40};
                    try {
                        int numHeight = Integer.parseInt(args[count].split("=")[1]);
                        if (Arrays.stream(validHeight).anyMatch(num -> num == numHeight)) {
                            height = numHeight;
                        } else {
                            System.out.println("Invalid Height");
                            System.exit(0);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Height must be a number.");
                        System.exit(0);
                    }
                    break;
                case 1:
                    int[] validWidth = {10, 20, 40, 80};
                    try {
                        int numWidth = Integer.parseInt(args[count].split("=")[1]);
                        if (Arrays.stream(validWidth).anyMatch(num -> num == numWidth)) {
                            width = numWidth;
                        } else {
                            System.out.println("Invalid Width");
                            System.exit(0);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Width must be a number.");
                        System.exit(0);
                    }
                    break;
                case 2:
                    try {
                        int numGen = Integer.parseInt(args[count].split("=")[1]);
                        if (numGen >= 0) {
                            totalGeneration = numGen;
                        } else {
                            System.out.println("Number of generations must be more than 0");
                            System.exit(0);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Number of generations must be a number.");
                        System.exit(0);
                    }
                    break;
                case 3:
                    try {
                        int[] validSpeed = {250, 1000};
                        int numSpeed = Integer.parseInt(args[count].split("=")[1]);
                        if (numSpeed >= validSpeed[0] && numSpeed <= validSpeed[1]) {
                            speed = numSpeed;
                        } else {
                            System.out.println("The speed value should be between 250-1000 ms");
                            System.exit(0);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Speed must be a number.");
                        System.exit(0);
                    }
                    break;
                case 4:
                    try {
                        String population = args[count].split("=")[1];
                        initialPopulation = population;

                    } catch (ArrayIndexOutOfBoundsException e) {
                        initialPopulation = randomInitialPopulation(height, width);
                    }
                    break;
                case 5:
                    try {
                        int[] validType = {1,2,3,4,5};
                        int numType = Integer.parseInt(args[count].split("=")[1]);
                        if (numType >= validType[0] && numType <= validType[4]) {
                            typeNeighborhood = numType;
                        } else {
                            System.out.println("The Type neighborhood should be between 1 and 5.");
                            System.exit(0);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Type neighborhood must be a number.");
                        System.exit(0);
                    }
                    break;
                default:
                    System.out.println("Insufficient arguments");
                    System.exit(0);
            }
            count++;
        }

        Grid grid = new Grid(height, width, totalGeneration, speed, initialPopulation, typeNeighborhood);
        int [][] generateGrid = grid.generateGrid();
        int currentGeneretion = 0;

        while (totalGeneration == 0 ? true: currentGeneretion < totalGeneration) {
//              Analisar o grid e verificar as regras
            try {
                System.out.println("Stats: Grid size: [" + height + " x " + width + "] - Speed: [" + speed + " ms] - Generation: [" + currentGeneretion + "] ");
                showGrid(generateGrid);
                System.out.println();
                generateGrid = checkCells(generateGrid, typeNeighborhood);
                Thread.sleep(speed);
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