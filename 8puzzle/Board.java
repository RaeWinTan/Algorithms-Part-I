import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

public class Board {

    private int[][] tt;
    private int h;
    private int m;
    private final int[] oPos;
    private String s;

    public Board(int[][] tiles) {
        h = 0;
        m = 0;
        oPos = new int[2];
        tt = new int[tiles.length][tiles.length];
        s = "";
        s += tiles.length + "\n";
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                tt[i][j] = tiles[i][j];
                if (j != tiles.length - 1) s += " " + tt[i][j];
                else s += " " + tt[i][j] + "\n";
                if (tt[i][j] == 0) {
                    oPos[0] = i;
                    oPos[1] = j;
                }
                if (i == tiles.length - 1 && j == tiles.length - 1) {
                    if (tt[i][j] == 0) {
                        break;
                    }
                }
                if (tt[i][j] != getCorrectValue(i, j) && tt[i][j] != 0) {
                    h++;
                    m += calculateManhattan(i, j, tt[i][j]);
                }
            }
        }
        tt = tiles;
    }

    public String toString() {
        return s;
    }

    // board dimension n
    public int dimension() {
        return tt[0].length;
    }

    private int getCorrectValue(int i, int j) {
        return (i + 1) * dimension() - dimension() + (j + 1);
    }

    // number of tiles out of place
    public int hamming() {
        return h;
    }
    //given the correct value must find hte x and y cordinate
    //to find y cordinate
    // num / dimension()
    //

    // sum of Manhattan distances between tiles and goal
    private int calculateManhattan(int i, int j, int num) {
        int corV = getCorrectValue(i, j);
        int tmp1 = dimension();
        int tmp2 = dimension();

        int lat = 0;
        int vert = 0;
        if (num % tmp1 != 0) {
            tmp1 = num % tmp1;
        }
        if (corV % tmp2 != 0) {
            tmp2 = corV % tmp2;
        }
        lat = Math.abs(tmp1 - tmp2);
        int actualrow = 0;
        if (num % dimension() == 0) {
            actualrow = num / dimension() - 1;
        } else {
            actualrow = num / dimension();
        }
        vert = Math.abs(actualrow - i);

        return vert + lat;
    }

    public int manhattan() {
        return m;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        Board temp = (Board) y;
        if (temp.dimension() != dimension()) {
            return false;
        }
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (temp.tt[i][j] != tt[i][j]) return false;
            }
        }
        return true;
    }

    private void swap(int[][] a, int oldRow, int oldCol, int row, int col) {
        int oldV = a[oldRow][oldCol];
        a[oldRow][oldCol] = a[row][col];
        a[row][col] = oldV;
        //switches value in a 2d array
    }

    private int[][] copyOf(int[][] m) {
        int[][] clone = new int[dimension()][];
        for (int i = 0; i < dimension(); i++) {
            clone[i] = m[i].clone();
        }
        return clone;

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        int rowPos = oPos[0] + 1;
        int colPos = oPos[1] + 1;
        //check left
        if (colPos - 1 >= 1) {
            int[][] copy = copyOf(tt);
            swap(copy, oPos[0], oPos[1], oPos[0], oPos[1] - 1);
            neighbors.add(new Board(copy));
        }
        //check right
        if (colPos + 1 <= dimension()) {
            int[][] copy = copyOf(tt);
            swap(copy, oPos[0], oPos[1], oPos[0], oPos[1] + 1);
            neighbors.add(new Board(copy));
        }
        //check up
        if (rowPos - 1 >= 1) {
            int[][] copy = copyOf(tt);
            swap(copy, oPos[0], oPos[1], oPos[0] - 1, oPos[1]);
            neighbors.add(new Board(copy));
        }
        //check down
        if (rowPos + 1 <= dimension()) {
            int[][] copy = copyOf(tt);
            swap(copy, oPos[0], oPos[1], oPos[0] + 1, oPos[1]);
            neighbors.add(new Board(copy));
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] cloned = copyOf(tt);
        if (cloned[1][0] != 0 && cloned[1][1] != 0)
            swap(cloned, 1, 0, 1, 1);
        else
            swap(cloned, 0, 0, 0, 1);
        return new Board(cloned);
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial.toString());
        StdOut.println(initial.dimension());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());

        StdOut.println(initial.isGoal());
        StdOut.println(initial.equals(initial));
        StdOut.println(initial.twin());
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
        }

    }

}
