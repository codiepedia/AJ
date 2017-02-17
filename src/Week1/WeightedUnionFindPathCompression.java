package Week1;

/**
 * Created by yafengwang on 9/11/16.
 *
 * The most efficient union find algorithm I can do. Both "Union" operation and "Connected" operation are log*.
 */
public class WeightedUnionFindPathCompression {
    private int[] id; // id[i] is the parent of i
    private int[] weight; // the number of objects in component with root i

    public WeightedUnionFindPathCompression(int N) {
        id = new int[N];
        weight = new int[N];
        for(int i=0 ; i < N ; i++) {
            id[i] = i;
            weight[i] = 1;
        }
    }

    private int root(int p) {
        int x = p;
        while(id[x] != x) {
            x = id[x];
        }
        // now we know x is the root of p, do path compression as well
        // loop invariant: every node before current is linked to root
        int parent = id[p];
        int current = p;
        while(current != parent) {
            id[p] = x;
            current = parent;
            parent = id[current];
        }
        return x;
    }

    public void union(int p, int q) {
        /**
         *  Weighted union, merge the small tree into the big ones.
         *  This ensures that, the depth of the tree, will be at least, O(lgN)
         */
        int p_root = root(p);
        int q_root = root(q);

        if (p_root == q_root) {
            return;
        }

        if (weight[p_root] > weight[q_root]) {
            id[q_root] = p_root;
            weight[p_root] += weight[q_root];
        } else {
            id[p_root] = q_root;
            weight[q_root] += weight[p_root];
        }
    }

    public boolean connected(int p, int q) {
        /**
         * Test if object p and object q are connected
         */
        return root(p) == root(q);
    }

    public static void main(String[] args) {
        WeightedUnionFindPathCompression uf = new WeightedUnionFindPathCompression(10);
        uf.union(1, 2);
        uf.union(3, 4);
        uf.union(0, 5);
        uf.union(5, 6);
        uf.union(7, 8);
        uf.union(7, 9);
        uf.union(1, 9);
        uf.union(2, 8);
        System.out.println(uf.connected(1, 2));
        System.out.println(uf.connected(0, 6));
        System.out.println(uf.connected(1, 8));
        System.out.println(uf.connected(4, 6));
    }
}

