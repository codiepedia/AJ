package Week1;

import java.io.*;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by yafengwang on 9/11/16.
 */

/*
public class Percolation {
   public Percolation(int n)                // create n-by-n grid, with all sites blocked
   public void open(int row, int col)       // open site (row, col) if it is not open already
   public boolean isOpen(int row, int col)  // is site (row, col) open?
   public boolean isFull(int row, int col)  // is site (row, col) full?
   public boolean percolates()              // does the system percolate?

   public static void main(String[] args)   // test client (optional)
}
 */
public class Percolation {

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufForFullTest;
    private boolean[][] isOpen;
    private int N;
    private int n;

    public Percolation(int n) {

        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.uf = new WeightedQuickUnionUF(n * n + 2); // two virtual sites, used for percolates
        this.ufForFullTest = new WeightedQuickUnionUF(n * n + 1); // only one virtual site
        this.isOpen = new boolean[n][n];
        this.N = n * n;
        this.n = n;
    }

    public void open(int i, int j) {
        i = i - 1;
        j = j - 1;
        if ((i < 0) || (i >= this.n) || (j < 0) || (j >= this.n)) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int current_id = Utilities.twoDimToOneDim(i, j, n) + 1;

        isOpen[i][j] = true;

        // for iterating the neighboring sites, and union with valid / open neighbors
        int[] x_delta = {-1, 1, 0, 0};
        int[] y_delta = {0, 0, -1, 1};

        for (int k=0 ; k < 4 ; k++) {
            int new_i = i + x_delta[k];
            int new_j = j + y_delta[k];
            if ((new_i >= 0) && (new_i < isOpen.length) && (new_j >= 0) && (new_j < isOpen[0].length)) {
                if (isOpen[new_i][new_j]) {
                    int new_id = Utilities.twoDimToOneDim(new_i, new_j, n) + 1;
                    uf.union(new_id, current_id);
                    ufForFullTest.union(new_id, current_id);
                }
            }
        }

        // if current site is at top or bottom, connect it with one of virtual site
        if (i == 0) {
            uf.union(0, current_id);
            ufForFullTest.union(0, current_id);
        }

        if (i == this.n - 1) {
            uf.union(N + 1, current_id);
        }

    }

    public boolean isOpen(int i, int j) {
        i = i - 1;
        j = j - 1;
        if ((i < 0) || (i >= this.n) || (j < 0) || (j >= this.n)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return this.isOpen[i][j];
    }

    public boolean isFull(int i, int j) {
        /**
         * Test if current site is connected to the top virtual site
         */
        i = i - 1;
        j = j - 1;
        if ((i < 0) || (i >= this.n) || (j < 0) || (j >= this.n)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        int current_id = Utilities.twoDimToOneDim(i, j, n) + 1;
        return ufForFullTest.connected(current_id, 0);
    }

    public boolean percolates() {
        return uf.connected(0, N + 1);
    }

    public static void main(String[] args) {

        BufferedReader br = null;

        try {
            String currentLine;

            br = new BufferedReader(new FileReader("test.txt"));
            boolean first = true;
            Percolation p = null;
            while ((currentLine = br.readLine()) != null) {

                if (first) {
                    int n = Integer.parseInt(currentLine);
                    p = new Percolation(n);
                    first = false;
                } else {
                    String[] list = currentLine.trim().split("\\s+");
                    int a = Integer.parseInt(list[0]);
                    int b = Integer.parseInt(list[1]);
                    p.open(a, b);
                }
            }
            System.out.println(p.percolates());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}

class Utilities {
    public static int[] oneDimToTwoDim(int x, int N) {
        int[] ret = {x / N, x % N};
        return ret;
    }

    public static int twoDimToOneDim(int x, int y, int N) {
        return x * N + y;
    }
}

