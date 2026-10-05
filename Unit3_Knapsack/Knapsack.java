import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/** 0/1 Knapsack using bottom-up Dynamic Programming, with traceback of chosen items. */
public class Knapsack {

    static int[][] buildTable(int[] wt, int[] val, int capacity) {
        int n = wt.length;
        int[][] dp = new int[n + 1][capacity + 1];   // row 0 and column 0 stay 0

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                dp[i][w] = dp[i - 1][w];                       // skip item i
                if (wt[i - 1] <= w) {                          // or take item i
                    dp[i][w] = Math.max(dp[i][w], val[i - 1] + dp[i - 1][w - wt[i - 1]]);
                }
            }
        }
        return dp;
    }

    static List<Integer> traceback(int[][] dp, int[] wt, int capacity) {
        List<Integer> chosen = new ArrayList<>();
        int w = capacity;
        for (int i = dp.length - 1; i >= 1; i--) {
            if (dp[i][w] != dp[i - 1][w]) {                    // value changed => item i was taken
                chosen.add(i);
                w -= wt[i - 1];
            }
        }
        Collections.reverse(chosen);
        return chosen;
    }

    static void printTable(int[][] dp, int capacity) {
        System.out.print("i\\w ");
        for (int w = 0; w <= capacity; w++) System.out.printf("%4d", w);
        System.out.println();
        for (int i = 0; i < dp.length; i++) {
            System.out.printf("%-4d", i);
            for (int w = 0; w <= capacity; w++) System.out.printf("%4d", dp[i][w]);
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of items: ");
        int n = sc.nextInt();
        int[] weights = new int[n];
        int[] values = new int[n];
        System.out.println("Enter " + n + " weights:");
        for (int i = 0; i < n; i++) weights[i] = sc.nextInt();
        System.out.println("Enter " + n + " values:");
        for (int i = 0; i < n; i++) values[i] = sc.nextInt();
        System.out.print("Knapsack capacity: ");
        int capacity = sc.nextInt();

        int[][] dp = buildTable(weights, values, capacity);
        System.out.println();
        printTable(dp, capacity);

        List<Integer> items = traceback(dp, weights, capacity);
        System.out.println("\nMaximum value : " + dp[n][capacity]);
        System.out.println("Items chosen  : " + items);
    }
}