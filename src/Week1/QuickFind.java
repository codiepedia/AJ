package Week1;


/**
 * Created by yafengwang on 9/11/16.
 *
 * Eager approach to solve the network connectivity problem.
 */
public class QuickFind {

    private int[] id;

    public QuickFind(int N) {
        /**
         * Constructor for Union-Find data type, this initializes the id array which holds the connections
         * @ interpretation: two objects are connected iff value in corresponding id array are the same
         * @ input: N, number of total objects
         */
        id = new int[N];
        for(int i=0 ; i < N ; i++) {
            id[i] = i;
        }
    }

    public void union(int p, int q) {
        /**
         * Merge component that contains p into component that contains q
         */
        int pid = id[p];
        int qid = id[q];

        for(int i=0 ; i < id.length ; i++) {
            if(id[i] == pid) {
                id[i] = qid;
            }
        }
    }

    public int find(int p) {
        /**
         * Find the identifier for the component which contains p
         */
        return id[p];
    }

    public boolean connected(int p, int q) {
        /**
         * Test if two objects are connected with each other
         */
        return id[p] == id[q];
    }

}
