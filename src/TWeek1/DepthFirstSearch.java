package TWeek1;

/**
 * Created by yafengwang on 2/8/17.
 */
public class DepthFirstSearch {

    private boolean[] marked;
    private int[] visitSequence;
    private int count;

    public DepthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        visitSequence = new int[g.V()];
        count = 0;
        dfs(g, s);
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        visitSequence[count] = s;
        count++;
        for (int w : g.adj(s)) {
            if (! marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int[] getVisitSequence() {
        return visitSequence;
    }

    public static void main(String[] args) {
        /*
         A:  E F B
         B:  A F
         C:  F H
         D:  H
         E:  A
         F:  C A G B
         G:  F H
         H:  G C D
         */
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(0, 5);
        g.addEdge(1, 5);
        g.addEdge(2, 5);
        g.addEdge(2, 7);
        g.addEdge(3, 7);
        g.addEdge(5, 6);
        g.addEdge(6, 7);

        DepthFirstSearch dfs = new DepthFirstSearch(g, 0);
        int[] result = dfs.getVisitSequence();
        for (int x : result) {
            System.out.print(x + " ");
        }
    }
}
