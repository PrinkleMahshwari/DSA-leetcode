class Solution {

    // Trie Node definition
    class Node {
        Node[] childern = new Node[26];
        boolean isEnd = false;
    }

    Node root = new Node();

    // insert word into Trie
    private void insert(String word) {
        Node node = root;

        for (char c : word.toCharArray()) {
            int idx = c - 'a';

            if (node.childern[idx] == null) {
                node.childern[idx] = new Node();
            }
            node = node.childern[idx];
        }
        node.isEnd = true;
    }

    public String longestCommonPrefix(String[] strs) {

        // build Trie from all string
        for (String word : strs) {
            insert(word);
        }

        StringBuilder prefix = new StringBuilder();
        Node node = root;

        // traverse while only one path exists
        while (true) {

            int count = 0;
            int index = -1;

            // check how many childern exist
            for (int i = 0; i < 26; i++) {
                if (node.childern[i] != null) {
                    count++;
                    index = i;
                }
            }

            // stop if:
            // 1. more than one branch OR
            // 2. end of a word
            if (count != 1 || node.isEnd) 
                break;

            // move to the next node
            node = node.childern[index];

            // append character to result
            prefix.append((char)(index + 'a'));
        }

        return prefix.toString();
    }
}