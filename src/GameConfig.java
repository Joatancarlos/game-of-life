import java.util.Arrays;
import java.util.Random;

public class GameConfig {
    public int height;
    public int width;
    public int totalGeneration;
    public int speed;
    public String initialPopulation;
    public int typeNeighborhood;

    public GameConfig(int height, int width, int totalGeneration, int speed, String initialPopulation, int typeNeighborhood) {
        this.height = height;
        this.width = width;
        this.totalGeneration = totalGeneration;
        this.speed = speed;
        this.initialPopulation = initialPopulation;
        this.typeNeighborhood = typeNeighborhood;
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

    public static GameConfig parseArgs(String[] args) {
        int height = 10;
        int width = 20;
        int totalGeneration = 5;
        int speed = 250;
        String initialPopulation = "#011##011111";
        int typeNeighborhood = 1;

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
                        int[] validType = {1, 2, 3, 4, 5};
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

        return new GameConfig(height, width, totalGeneration, speed, initialPopulation, typeNeighborhood);
    }
}
