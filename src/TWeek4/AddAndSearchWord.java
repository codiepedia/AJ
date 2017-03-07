package TWeek4;

public class AddAndSearchWord {

    private Node root = new Node();

    class Node {
        private Node[] nodes = new Node[26];
        private int value;
    }

    public void addWord(String word) {
        root = put(root, word, 0);
    }

    /**
     *  Follow links corresponding to each character in the key.
     *     Encounter a null link: create new node
     *     Encounter the last character of the key, set value in that node
     */
    private Node put(Node root, String word, int idx) {
        if (root == null) root = new Node();

        if (idx == word.length()) {
            root.value = 1;
            return root;
        }

        int c = word.charAt(idx) - 'a';
        root.nodes[c] = put(root.nodes[c], word, idx + 1);

        return root;
    }

    public boolean search(String word) {
        Node n = getWithPattern(root, 0, word);
        if (n == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Follow links corresponding to each character in the key.
     *      search hit: node where search ends has a non-null value
     *      search miss: reach null link or node where search ends has null value.
     */
    private Node getWithPattern(Node root, int idx, String pattern) {
        // search miss
        if (root == null) return null;

        // search miss
        if (idx == pattern.length() && root.value != 1) return null;

        // search hit
        if (idx == pattern.length()) return root;

        // searching
        if (pattern.charAt(idx) == '.') {
            for (int i = 0 ; i < 26 ; i++) {
                Node n = getWithPattern(root.nodes[i], idx + 1, pattern);
                if (n != null) {
                    return n;
                }
            }
        } else {
            char c = pattern.charAt(idx);
            int c_idx = c - 'a';
            Node n = getWithPattern(root.nodes[c_idx], idx + 1, pattern);
            if (n != null) {
                return n;
            }
        }

        return null;
    }

    public static void main(String[] args) {
        AddAndSearchWord trie = new AddAndSearchWord();
        trie.addWord("abc");
        trie.addWord("abd");
        System.out.println(trie.search("..."));
    }
}
