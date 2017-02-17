package Week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by yafengwang on 9/11/16.
 */

/*

public class PercolationStats {
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   public double mean()                          // sample mean of percolation threshold
   public double stddev()                        // sample standard deviation of percolation threshold
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   public double confidenceHi()                  // high endpoint of 95% confidence interval

   public static void main(String[] args)    // test client (described below)
}


 */
public class PercolationStats {

    private int n;
    private int trials;
    private double[] results;

    public PercolationStats(int n, int trials) {
        if ((n <= 0) || (trials <= 0)) {
            throw new java.lang.IllegalArgumentException();
        }
        this.n = n;
        this.trials = trials;
        this.results = new double[trials];

        for (int i = 0 ; i < trials ; i++) {
            Percolation p = new Percolation(n);
            int count = 0;
            while (! p.percolates()) {

                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;

                while (p.isOpen(x, y)) {
                    x = StdRandom.uniform(n) + 1;
                    y = StdRandom.uniform(n) + 1;
                }

                count ++;
                p.open(x, y);

            }
            results[i] = count * 1.0 / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(this.results);
    }

    public double stddev() {
        return StdStats.stddev(this.results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        for (int i = 0 ; i < trials ; i++) {
            Percolation p = new Percolation(n);
            int count = 0;
            while (! p.percolates()) {

                int x = StdRandom.uniform(n) + 1;
                int y = StdRandom.uniform(n) + 1;

                while (p.isOpen(x, y)) {
                    x = StdRandom.uniform(n) + 1;
                    y = StdRandom.uniform(n) + 1;
                }

                count ++;
                p.open(x, y);

            }
            ps.results[i] = count * 1.0 / (n * n);
        }
        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
    }

}
