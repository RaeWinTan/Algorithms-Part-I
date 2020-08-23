/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int t;
    private final double[] total;
    private final double CONFIDENCE_95;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException();
        CONFIDENCE_95 = 1.96;
        t = trials;
        total = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }
            double os = p.numberOfOpenSites();
            double size = n * n;
            total[i] = os / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(total);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(total);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(t));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(t));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.println("mean                    =" + ps.mean());
        System.out.println("stddev                    =" + ps.stddev());
        System.out.println(
                "95% confidence interval                    = [" + ps.confidenceLo() + ", " + ps
                        .confidenceHi()
                        + "]");

    }
}
