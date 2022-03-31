import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String [] params = scanner.nextLine().split(" ");
        int size = Integer.parseInt(params[0]);
        double scale = Double.parseDouble(params[1]);
        int coefficient = Integer.parseInt(params[2]);

        StringBuilder linear = new StringBuilder();
        for (int i = 0; i < size; i++) {
            linear.append(scanner.nextLine());
            linear.append(";");
        }

        Parser parser = new Parser(linear.toString());
        LinearSystem<BigDecimal, Equation> system = parser.getSys();
        for (int k = 0; k < system.size(); k++) {
            system.get(k).mul(BigDecimal.valueOf(coefficient));
        }

        LinearSystem<BigDecimal, Equation> system1 = system;

        //LinearSystem<Double, Equation> system = generateSystem();
        final int SIZE = system.size();

        printSystem(system);
        Algorithm<BigDecimal, Equation> algorithm = new Algorithm<>(system);
        int i, j;
        try {
            algorithm.calculate();
        } catch (NullPointerException | ArithmeticException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        BigDecimal [] vector = new BigDecimal[SIZE];
        for(i = system.size() - 1; i >= 0; i--) {
            BigDecimal sum = BigDecimal.ZERO;
            for(j = system.size() - 1; j > i; j--) {
                sum = sum.add( system.itemAt(i, j).multiply(vector[j]) );
            }
            vector[i] = system.itemAt(i, system.size()).subtract(sum).divide(system.itemAt(i, j)
                    , 18, RoundingMode.HALF_UP);
        }
        boolean sc = true;

        BigDecimal [] difference = new BigDecimal[SIZE];
        for (int ii = 0; ii < SIZE; ii++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int jj = 0; jj < SIZE; jj++) {
                sum = sum.add(system1.itemAt(ii, jj).multiply(vector[jj]));
            }
            difference[ii] = sum.subtract(system1.itemAt(ii, SIZE));
            /*if (difference[ii].compareTo(BigDecimal.valueOf(scale)) < 0) {
                System.out.println("Не может быть выполнено с заданной точностью");
                System.exit(0);
            }*/
        }
        printSystem(system);
        BigDecimal determine = system.determine(system);
        System.out.println("Difference is: " + Arrays.toString(difference));
        System.out.println("Determinant is: " + determine.setScale(6, RoundingMode.HALF_UP));
        printVector(vector);
    }

    public static void printSystem(LinearSystem<BigDecimal, Equation> system) {
        for(int i = 0; i < system.size(); i++) {
            Equation line = system.get(i);
            StringBuilder s = new StringBuilder();
            for (int j = 0; j < line.size(); j++) {
                s.append(String.format("%f; %s", system.itemAt(i, j), "\t"));
            }
            System.out.println(s);
        }
        System.out.println();
    }

    public static void printVector(BigDecimal [] x) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < x.length; i++){
            s.append(String.format(" x%d =  ", i));
            s.append(x[i]);
        }
        System.out.println(s);
    }



}
