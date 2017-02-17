package Week4;

import java.util.Stack;

/*
public class Board {
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension n
    public int hamming()                   // number of blocks out of place
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal()                // is this board the goal board?
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of this board (in the output format specified below)

    public static void main(String[] args) // unit tests (not graded)
}
 */
public class Board {

    private int[][] blocks;
    private Iterable<Board> neighbors;
    private int manhatten = -1;
    private Board twin;

    public Board(int[][] blocks) {
        /**
         * Every board should have it priority set once it's constructed.
         */
        this.blocks = new int[blocks.length][blocks.length];
        for (int i = 0 ; i < blocks.length ; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {
        return this.blocks.length;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < this.dimension() ; i++) {
            for (int j=0 ; j < this.dimension() ; j ++) {
                if (blocks[i][j] != (i * this.dimension() + j + 1)) {
                    count ++;
                }
            }
        }
        return count;
    }

    private int calculateManhattan() {
        int dist = 0;
        for (int i = 0 ; i < this.dimension() ; i++) {
            for (int j = 0 ; j < this.dimension() ; j++) {
                if (blocks[i][j] == 0) {
                    continue;
                }
                int destX = (blocks[i][j] - 1) / this.dimension();
                int destY = (blocks[i][j] - 1) % this.dimension();
                dist += (Math.abs(i - destX) + Math.abs(j - destY));
            }
        }
        return dist;
    }

    public int manhattan() {
        if (manhatten == -1) {
            manhatten = calculateManhattan();
        }
        return manhatten;
    }


    private int getValue(int x, int y) {
        return this.blocks[x][y];
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public Board twin() {
        /**
         * NOTE: call getPriority once board is generated
         */
        if (this.twin == null) {
            int[] positions = new int[4];
            int start = 0;
            outerLoop:
            for (int i = 0 ; i < dimension() ; i++) {
                for (int j = 0 ; j < dimension() ; j++) {
                    if (getValue(i, j) != 0) {
                        positions[start++] = i;
                        positions[start++] = j;
                        if (start >= positions.length) {
                            break outerLoop;
                        }
                    }
                }
            }
            Board b = new Board(this.blocks);
            b.swap(positions[0], positions[1], positions[2], positions[3]);
            this.twin = b;
        }

        return this.twin;
    }

    public boolean equals(Object y) {

        // Sanity check
        if (y == null) {
            return false;
        }

        Board b = null;
        try {
            b = (Board) y;
        } catch (java.lang.ClassCastException e) {
            return false;
        }

        if (this.dimension() != b.dimension()) {
            return false;
        }

        // Compare elements
        for (int i = 0 ; i < b.dimension() ; i ++) {
            for (int j = 0 ; j < b.dimension() ; j++) {
                if (this.blocks[i][j] != b.getValue(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void swap(int a, int b, int x, int y) {
        int tmp = this.blocks[a][b];
        this.blocks[a][b] = this.blocks[x][y];
        this.blocks[x][y] = tmp;
    }

    public Iterable<Board> neighbors() {
        if (neighbors == null) {
            Stack<Board> s = this.getNeighbors();
            neighbors = s;
        }
        return neighbors;
    }

    private Stack<Board> getNeighbors() {
        /**
         * NOTE: call getPriority once board is generated
         */
        Stack<Board> stack = new Stack<Board>();

        int zeroX = -1;
        int zeroY = -1;

        outerLoop:
        for (int i = 0 ; i < this.dimension() ; i++) {
            for (int j = 0 ; j < this.dimension() ; j++) {
                if (this.blocks[i][j] == 0) {
                    zeroX = i;
                    zeroY = j;
                    break outerLoop;
                }
            }
        }

        int[] directionsX = {-1, 1, 0, 0};
        int[] directionsY = {0, 0, -1, 1};
        for (int d=0 ; d < directionsX.length ; d ++) {
            int newX = zeroX + directionsX[d];
            int newY = zeroY + directionsY[d];
            if (newX >=0 && newX < this.dimension() && newY >= 0 && newY < this.dimension()) {
                Board b = new Board(this.blocks);
                b.swap(zeroX, zeroY, newX, newY);
                stack.add(b);
            }
        }

        return stack;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.dimension() + "\n");
        for (int i = 0 ; i < this.dimension() ; i++) {
            for (int j = 0 ; j < this.dimension() ; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

    }

}
