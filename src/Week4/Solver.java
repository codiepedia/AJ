package Week4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Stack;

/*
public class Solver {
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()            // is the initial board solvable?
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) // solve a slider puzzle (given below)
}

 */
public class Solver {

    private SearchNode initial;
    private SearchNode result;
    private boolean solvable = false;

    public Solver(Board initial) {
        this.initial = new SearchNode(initial, null, 0);
        this.result = solveSimultanously();
    }

    private SearchNode solveSimultanously() {
        MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>(new SearchNodeComparator());
        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<SearchNode>(new SearchNodeComparator());
        priorityQueue.insert(this.initial);
        priorityQueueTwin.insert(new SearchNode(this.initial.board.twin(), null, 0));

        while (true) {
            boolean first = solve(priorityQueue);
            boolean second = solve(priorityQueueTwin);
            if (first) {
                this.solvable = true;
                return priorityQueue.min();
            }
            if (second) {
                this.solvable = false;
                return null;
            }
        }
    }

    // check top is goal, if not proceed
    private boolean solve(MinPQ<SearchNode> pq) {
        if (pq.min().board.isGoal()) {
            return true;
        }
        SearchNode b = pq.delMin();
        for (Board i : b.board.neighbors()) {
            if (b.board.equals(i)) {
                continue;
            }
            SearchNode newNode = new SearchNode(i, b, b.stepsTillNow + 1);
            pq.insert(newNode);
        }
        return false;
    }

    public boolean isSolvable() {
        return this.solvable;
    }

    public int moves() {
        if (solvable) {
            return this.result.priority;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        Stack<Board> stack = new Stack<Board>();
        if (this.solvable) {
            SearchNode current = this.result;
            while (current.previousNode != null) {
                stack.add(current.board);
                current = current.previousNode;
            }
            stack.add(current.board);
        }
        return stack;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (! solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}

class SearchNode {
    public Board board;
    public SearchNode previousNode;
    public int stepsTillNow;
    public int priority;

    public SearchNode(Board board, SearchNode previousNode, int stepsTillNow) {
        this.board = board;
        this.previousNode = previousNode;
        this.stepsTillNow = stepsTillNow;
        this.priority = board.manhattan() + stepsTillNow;
    }
}

class SearchNodeComparator implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode board, SearchNode t1) {
        return board.priority - t1.priority;
    }
}