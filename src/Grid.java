import java.util.Arrays;

public class Grid {
    private int height;
    private int width;
    private int totalGeneration;
    private int speed;
    //      Se não tiver uma população inicial, deve ser gerada randomicamente
    private String initialPopulation;
    private int typeNeighborhood;

    public Grid(int height, int width, int totalGeneration, int speed, String initialPopulation, int typeNeighborhood) {
        this.height = height;
        this.width = width;
        this.totalGeneration = totalGeneration;
        this.speed = speed;
        this.initialPopulation = initialPopulation;
        this.typeNeighborhood = typeNeighborhood;
    }

     int [][] generateGrid() {
        int [][] grid = new int[height][width];
        String[] rows = initialPopulation.split("#");
//             Preenche o grid com 0's
        for (int i = 0; i < grid.length; i++) {
            Arrays.fill(grid[i], 0);
        }
//            Insere as células vivas ou mortas com base na população inicial
        for (int i = 0; i < rows.length; i++) {

            if (rows[i].length() <= width) {
                if (rows[i] != "") {
                    for (int j = 0; j < rows[i].length(); j++) {
                        char oneOrZero = rows[i].charAt(j);
                        grid[i][j] = Integer.parseInt(String.valueOf(oneOrZero));
                    }
                }
            } else {
                int removeValues = rows[i].length() - width;
                System.out.println("The size of the line " + rows[i] + " is greater than the length of the table." + " Remove " + removeValues + " values.");
                System.exit(0);
            }
        }
        return grid;
    }

    void showGrid(int[][] grid) {
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
}
