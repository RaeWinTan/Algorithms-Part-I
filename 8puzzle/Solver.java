import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Solver {

    private MinPQ<SearchNode> pq;
    private int ms;
    private SearchNode solved;
    private boolean solvable;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        ms = 0;
        pq = new MinPQ<SearchNode>(new SearchNodeComparator());
        MinPQ<SearchNode> pq2 = new MinPQ<SearchNode>(new SearchNodeComparator());
        SearchNode i = new SearchNode(initial, null);
        SearchNode a = new SearchNode(initial.twin(), null);
        pq.insert(i);
        pq2.insert(a);
        while (!i.board.isGoal() && !a.board.isGoal()) {
            //GAME1
            for (Board b : i.board.neighbors()) {
                if (i.prevNode == null) {
                    pq.insert(new SearchNode(b, i));
                } else if (!i.prevNode.board.equals(b)) {
                    pq.insert(new SearchNode(b, i));
                }
            }
            //GAME2
            for (Board b : a.board.neighbors()) {
                if (a.prevNode == null) {
                    pq2.insert(new SearchNode(b, a));
                } else if (!a.prevNode.board.equals(b)) {
                    pq2.insert(new SearchNode(b, a));
                }
            }
            i = pq.delMin();
            a = pq2.delMin();
        }
        if (!i.board.isGoal()) {
            solvable = false;
            return;
        }
        solvable = true;
        ms = i.moves;
        solved = i;

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    //should compare number of


    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return ms;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        SearchNode solvedCopy = solved;
        ArrayList<Board> l = new ArrayList<Board>();
        if (moves() == -1) {
            return null;
        }
        while (solvedCopy != null) {
            l.add(solvedCopy.board);
            solvedCopy = solvedCopy.prevNode;
        }
        Collections.reverse(l);
        return l;
    }

    private class SearchNode {
        public Board board;
        public SearchNode prevNode;
        public int moves;
        public int manhattan;

        public SearchNode(Board board, SearchNode prevNode) {
            this.board = board;
            this.prevNode = prevNode;
            this.manhattan = board.manhattan();
            if (prevNode == null) moves = 0;
            else moves = prevNode.moves + 1;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        public int compare(SearchNode that, SearchNode other) {
            return (that.moves + that.manhattan) - (other.moves + other.manhattan);
        }
    }


    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
