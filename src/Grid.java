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
}
