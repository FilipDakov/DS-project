
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] branchesWeight= new int[7];
        System.out.println("Въведете растоянието между:");
        try {
            System.out.print("Магазин 1 и магазин 2 : ");
            branchesWeight[0] = scanner.nextInt();
            System.out.printf("Магазин 1 и магазин 5 : ");
            branchesWeight[1]= scanner.nextInt();
            System.out.printf("Магазин 2 и магазин 3 : ");
            branchesWeight[2] = scanner.nextInt();
            System.out.printf("Магазин 2 и магазин 6 : ");
            branchesWeight[3] = scanner.nextInt();
            System.out.printf("Магазин 3 и магазин 4 : ");
            branchesWeight[4] = scanner.nextInt();
            System.out.printf("Магазин 4 и магазин 5 : ");
            branchesWeight[5] = scanner.nextInt();
            System.out.printf("Магазин 5 и магазин 6 : ");
            branchesWeight[6] = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Въведохте грешен символ");
            return;
        }



        Graph graph = new Graph(branchesWeight);
        graph.checkForBranchToExclude();
        graph.printResult();


    }

}
