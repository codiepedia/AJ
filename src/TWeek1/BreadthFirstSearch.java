package TWeek1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yafengwang on 2/15/17.
 */
public class BreadthFirstSearch {
    private boolean[] marked;
    private List<Integer> visitSequence;
    private int[] edgeTo;

    public BreadthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        visitSequence = new ArrayList<Integer>();
        edgeTo = new int[g.V()];
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {
        List<Integer> queue = new LinkedList<Integer>();
        queue.add(s);
        marked[s] = true;
        edgeTo[s] = s;
        while (! queue.isEmpty()) {
            int v = queue.remove(0);
            visitSequence.add(v);
            for (int w : g.adj(v)) {
                if (! marked[w]) {
                    queue.add(w);
                    edgeTo[w] = v;
                    marked[w] = true;
                }
            }
        }
    }

    public Iterable<Integer> getVisitSequence() {
        return visitSequence;
    }

    public static void main(String[] args) {
        /*
            A:  B E
            B:  E F A
            C:  D F G H
            D:  C
            E:  B F A
            F:  C E B
            G:  H C
            H:  G C
         */
        Graph g = new Graph(8);
        g.addEdge(0, 1);
        g.addEdge(0, 4);
        g.addEdge(1, 4);
        g.addEdge(1, 5);
        g.addEdge(2, 6);
        g.addEdge(2, 7);
        g.addEdge(3, 7);
        g.addEdge(4, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        BreadthFirstSearch bfs = new BreadthFirstSearch(g, 0);
        for (int x : bfs.getVisitSequence()) {
            System.out.print(x + " ");
        }
    }

}
