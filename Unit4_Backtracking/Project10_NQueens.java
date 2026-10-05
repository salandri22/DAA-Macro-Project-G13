public class Project10_NQueens {
    static final int N = 4;
    static int[] board = new int[N];
    static int solutions = 0;

    static boolean isSafe(int row, int col) {
        for (int r = 0; r < row; r++)
            if (board[r] == col || Math.abs(board[r] - col) == row - r)
                return false;
        return true;
    }

    static void solve(int row, String indent) {
        if (row == N) {
            solutions++;
            System.out.println(indent + "SOLUTION " + solutions + ": " + java.util.Arrays.toString(board));
            return;
        }
        for (int col = 1; col <= N; col++) {
            if (isSafe(row, col)) {
                board[row] = col;
                System.out.println(indent + "Q" + (row + 1) + " -> col " + col + " (valid)");
                solve(row + 1, indent + "    ");
            } else {
                System.out.println(indent + "Q" + (row + 1) + " -> col " + col + " (INVALID, pruned)");
            }
        }
    }

    public static void main(String[] args) {
        solve(0, "");
        System.out.println("Total solutions: " + solutions);
    }
}