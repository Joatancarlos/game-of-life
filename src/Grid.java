import java.util.Arrays;
public class Grid {
    private int[][] grid;

    public Grid(GameConfig config) {
        this.grid = generateGrid(config.getInitialPopulation(), config.getHeight(), config.getWidth());
    }

    /**
     * Gera um grid bidimensional com valores iniciais definidos como 0. Esses valores podem
     * ser alterados para 1 (célula viva) ou permanecer como 0 (célula morta) segundo a população inicial fornecida.
     * @param initialPopulation É uma ‘string’ que representa as linhas do grid sendo composta por 0's e 1's.
     * @param height Número de linhas do grid.
     * @param width Número de colunas do grid.
     * @return Um grid 2D com as células vivas ou mortas com base na população inicial.
     * @throws IllegalArgumentException é lançado quando o comprimento de uma linha da população inicial é maior que o número de colunas.
     */

     int [][] generateGrid(String initialPopulation, int height, int width) {
//        Inicia-se com os valores 0's
        int [][] grid = new int[height][width];
        String[] rows = initialPopulation.split("#");

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
                throw new IllegalArgumentException("The size of the line " + rows[i] + " is greater than the length of the table. Remove " + removeValues + " values.");
            }
        }
        return grid;
    }

    /**
     * Exibe o conteúdo da matriz seguindo o número de linhas e colunas.
     */
    void showGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Conta o número de células vizinhas vivas ao redor de uma célula específica numa grade,
     * considerando o tipo de vizinhança definida.
     * @param i posição da linha da célula
     * @param j posição da coluna da célula
     * @param type o tipo de vizinhança a ser considerada. Os valores possíveis são:
     *              <ul>
     *                 <li>1: Considera apenas os vizinhos ortogonais <strong>(exclui as diagonais)</strong>.</li>
     *                 <li>2: Exclui as diagonais principais <strong>(superior esquerda e inferior direita)</strong>.</li>
     *                 <li>3: Considera <strong>todos</strong> os vizinhos ao redor da célula.</li>
     *                 <li>4: Considera apenas os vizinhos nas direções cardinais <strong>(cima, baixo, esquerda, direita)</strong>.</li>
     *                 <li>5: Considera apenas os vizinhos nas direções horizontal <strong>(esquerda e direita)</strong>.</li>
     *               </ul>
     * @return Um 'int.' que representa quantidade de vizinhos da célula analisada
     */

    private int countNeighbors(int i, int j, int type) {
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

        return neighbors;

    }

    /**
     * Atualiza o grid para a nova geração com os novos valores das células aplicando as regras do Game of Life.
     * @param type indica o padrão de vizinhaça que será analisada
     */
    public void updateGrid(int type) {
        int[][] neighborsGrid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {

                int neighbors = countNeighbors(i, j, type);

//                Aplicando as regras do GoL para cada célula
                if (grid[i][j] == 1) {
                    neighborsGrid[i][j] = (neighbors < 2 || neighbors > 3) ? 0 : 1;
                } else {
                    neighborsGrid[i][j] = (neighbors == 3) ? 1 : 0;
                }
            }
        }
        grid = neighborsGrid;
    }
}
