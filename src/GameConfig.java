import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class GameConfig {
    private final int height;
    private final int width;
    private final int totalGeneration;
    private final int speed;
    private final String initialPopulation;
    private final int typeNeighborhood;

    public GameConfig(
            int height,
            int width,
            int totalGeneration,
            int speed,
            String initialPopulation,
            int typeNeighborhood
    ) {
        this.height = height;
        this.width = width;
        this.totalGeneration = totalGeneration;
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
    public int getTotalGeneration() {
        return totalGeneration;
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

    /**
     * Irá criar uma população inicial que contém '0's (células mortas), '1's (células vivas), e
     * '#'s como delimitadores de linha.
     * @param height informa o número máximo de linhas ('#') que poderá implementado.
     * @param width informa o número máximo de 0's e 1's que estarão contidos numa linha.
     * @return uma ‘string’ representando a população inicial aleatória, com '0's, '1's e '#'s.
     */

    public static String randomInitialPopulation (int height, int width) {
//        O número de '#' tem que ser menor ou igual a height
//        O número de 0's ou 1's tem que ser menor ou igual a width

        Random random = new Random();

        int numHash = random.nextInt(height+1);
        String randomPopulation = "";

        for (int i = 0; i < numHash; i++) {
            int numCol = random.nextInt(width+1);
            for (int j = 0; j < numCol; j++) {
                int cellState = random.nextInt(2);
                randomPopulation += cellState;
            }
            randomPopulation += "#";
        }
        return randomPopulation;
    }

    /**
     * Verifica se uma string de população inicial é válida. A string é considerada válida se:
     * <ul>
     *   <li>Os caracteres em cada linha (delimitada por '#') forem apenas '0' ou '1'.</li>
     *   <li>Cada linha tiver um comprimento menor ou igual ao valor especificado de largura (width).</li>
     * </ul>
     * Se qualquer linha contiver um caractere inválido ou exceder a largura especificada, o método retorna `true`.
     * Caso contrário, retorna `false`.
     *
     * @param population A string representando a população inicial, onde cada linha é separada por '#'.
     * @param width A largura máxima permitida para cada linha na string de população.
     * @return `true` se a string de população inicial for inválida, `false` se for válida.
     * @throws NumberFormatException Se algum caractere na string não puder ser convertido para um número.
     */
    public static boolean invalideInitialP(String population, int width) {

        String[] rows = population.split("#");

        for (int i = 0; i < rows.length; i++) {
            if (rows[i].length() <= width) {
                for (int j = 0; j < rows[i].length(); j++) {
                    char c = rows[i].charAt(j);
                    if (!Arrays.asList(0, 1).contains(Integer.parseInt(String.valueOf(c)))) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Converte os argumentos de linha de comando em uma configuração para o jogo. Os parâmetros aceitos são:
     *   <ul>
     *     <li><strong>height</strong>: Altura da grade (valores válidos: 10, 20, 40).</li>
     *     <li><strong>width</strong>: Largura da grade (valores válidos: 10, 20, 40, 80).</li>
     *     <li><strong>totalGeneration</strong>: Número total de gerações a serem executadas (deve ser >= 0).</li>
     *     <li><strong>speed</strong>: Velocidade em milissegundos entre as gerações (intervalo válido: 250-1000 ms).</li>
     *     <li><strong>initialPopulation</strong>: String representando a população inicial (opcional, valor padrão gerado aleatoriamente).</li>
     *     <li><strong>typeNeighborhood</strong>: Tipo de vizinhança a ser usada (valores válidos: 1, 2, 3, 4, 5).</li>
     *   </ul>
     * @return um objeto {@link GameConfig} contendo as configurações do jogo.
     */
    public static GameConfig parseArgs() {
        Scanner sc = new Scanner(System.in);

        int height;
        System.out.println("Insira os valores 10, 20 ou 40 para a quantidade de linhas: ");
        while (true) {
            try {
                height = sc.nextInt();
                if (Arrays.asList(10, 20, 40).contains(height)) {
                    break;
                } else {
                    System.out.println("Valor inválido. Por favor, insira uma das 3 opções: 10, 20, ou 40.");
                }
            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira somente números.");
                sc.next();
            }
        }

        int width;
        System.out.println("Insira os valores 10, 20, 40 ou 80 para a quantidade de colunas: ");
        while (true) {
            try {
                width = sc.nextInt();
                if (Arrays.asList(10, 20, 40, 80).contains(width)) {
                    break;
                } else {
                    System.out.println("Valor inválido. Por favor, insira uma das 4 opções: 10, 20, 40 ou 80.");
                }
            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira somente números.");
                sc.next();
            }
        }

        int totalGeneration;
        System.out.println("Insira um valor maior ou igual a 0 para o número total de gerações: ");
        while (true) {
            try {
                totalGeneration = sc.nextInt();
                if (totalGeneration >= 0) {
                    break;
                } else {
                    System.out.println("O número de gerações tem que ser maior ou igual a 0");
                }
            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira somente números.");
                sc.next();
            }
        }

        int speed;
        System.out.println("Insira a velocidade (ms). O valor tem que estar em um intervalo entre 250-1000: ");
        while (true) {
            try {
                speed = sc.nextInt();
                if (speed >= 250 && speed <= 1000) {
                    break;
                } else {
                    System.out.println("A velocidade tem que estar entre 250 e 1000 ms.");
                }
            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira somente números.");
                sc.next();
            }
        }

        System.out.println("Insira a população inicial. Caso deseje, pressione Enter sem nenhum valor para gerar uma população aleatória: ");
        sc.nextLine();
        String initialPopulation;
        while (true) {
            try {
                initialPopulation = sc.nextLine().trim();
                if (initialPopulation.isEmpty()) {
                    initialPopulation = randomInitialPopulation(height, width);
                    break;
                }
                if(invalideInitialP(initialPopulation, width)) {
                    System.out.println("Opa! Esse valor é inválido. Por gentileza, insira valores 0's ou 1's separados por '#' ou verifique se a quantidade de células é menor que o número de colunas.");
                } else {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira valores 0's ou 1's separados por '#' ");
                sc.next();
            }
        }

        int typeNeighborhood;
        System.out.println("Insira o tipo de vizinhaça com valores entre 1-5: ");
        while (true) {
            try {
                typeNeighborhood = sc.nextInt();
                if (typeNeighborhood >= 1 && typeNeighborhood <= 5) {
                    break;
                } else {
                    System.out.println("O tipo de vizinhaça deve estar entre 1 e 5.");
                }
            } catch (Exception e) {
                System.out.println("Opa! Esse valor é inválido. Por gentileza, insira somente números.");
                sc.next();
            }
        }

        return new GameConfig(height, width, totalGeneration, speed, initialPopulation, typeNeighborhood);
    }
}
