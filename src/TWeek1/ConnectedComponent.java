package TWeek1;

/**
 * Created by yafengwang on 2/15/17.
 */
public class ConnectedComponent {
    private int[] id;
    private boolean[] marked;
    private int count;

    public ConnectedComponent(Graph g) {
        id = new int[g.V()];
        marked = new boolean[g.V()];
        count = 0;
        for (int i = 0 ; i < marked.length ; i++) {
            if (! marked[i]) {
                dfs(g, i);
                count ++;
            }
        }
    }

    private void dfs(Graph g, int s) {
        marked[s] = true;
        id[s] = count;
        for (int w : g.adj(s)) {
            if (! marked[w]) {
                dfs(g, w);
            }
        }
    }

    public int[] getId() {
        return id;
    }

    public static void main(String[] args) {
        Graph g = new Graph(10);
        g.addEdge(0, 5);
        g.addEdge(1, 2);
        g.addEdge(1, 6);
        g.addEdge(2, 3);
        g.addEdge(2, 6);
        g.addEdge(2, 7);
        g.addEdge(3, 7);
        g.addEdge(4, 8);
        g.addEdge(4, 9);
        g.addEdge(6, 7);
        g.addEdge(8, 9);
        ConnectedComponent cc = new ConnectedComponent(g);
        int[] id = cc.getId();
        for (int x : id) {
            System.out.print(x + " ");
        }
    }
}
