package TWeek4;

public class TrieST {

    private Node root;

    class Node {
        private Object value;
        private Node[] nodes = new Node[26];
    }

    /** Initialize your data structure here. */
    public TrieST() {
        root = new Node();
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        root = put(root, word, 0);
    }

    private Node put(Node root, String word, int idx) {
        if (root == null) root = new Node();

        if (idx == word.length()) {
            root.value = 0;
            return root;
        }

        int c = word.charAt(idx) - 'a';
        root.nodes[c] = put(root.nodes[c], word, idx + 1);
        return root;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Node n = get(root, word, 0);
        if (n == null || n.value == null) {
            return false;
        }
        return true;
    }

    private Node get(Node root, String word, int idx) {
        if (root == null) return null;

        if (idx == word.length()) {
            return root;
        }

        int c = word.charAt(idx) - 'a';
        return get(root.nodes[c], word, idx + 1);
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Node n = get(root, prefix, 0);
        if (n == null) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        TrieST t = new TrieST();
        t.insert("abc");
        t.insert("abd");
        boolean a = t.startsWith("ad");
        System.out.println(a);
    }
}



/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
