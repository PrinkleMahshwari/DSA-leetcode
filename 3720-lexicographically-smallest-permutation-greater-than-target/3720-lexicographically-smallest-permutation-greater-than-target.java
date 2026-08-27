class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] freq =  new int[26];

        for (char c : s.toCharArray())
            freq[c - 'a']++;
        
        char[] ans = new char[n];
        int i = 0;

        // match target as long as possible
        while (i < n) {
            int idx = target.charAt(i) - 'a';

            if (freq[idx] > 0) {
                ans[i] = target.charAt(i);
                freq[idx]--;
                i++;
            } else {
                break;
            }
        }

        // try to make the string greater from the current pos
        if (i < n) {
            int greater = findGreater(freq, target.charAt(i) - 'a');

            if (greater != -1) {
                ans[i] = (char) ('a' + greater);
                freq[greater]--;
                
                fillSmallest(ans, i + 1, freq);
                return new String(ans);
            }
        }

        // backtrack to find the rightmost pos to increase
        i--;

        while (i >= 0) {
            int current = ans[i] - 'a';

            // put the prev matched character back
            freq[current]++;

            int greater = findGreater(freq, current);

            if (greater != -1) {
                ans[i] = (char) ('a' + greater);
                freq[greater]--;

                fillSmallest(ans, i + 1, freq);
                return new String(ans);
            }
            i--;
        }
        return "";
    }

    // helper function for finding greater than target
    private int findGreater(int[] freq, int current) {
        for (int c = current + 1; c < 26; c++) 
            if (freq[c] > 0)
                return c;
        
        
        return -1;
    }

    // helper function for filling smallest permutation
    private void fillSmallest(char[] ans, int start, int[] freq) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans[pos++] = (char) ('a' + c);
                freq[c]--;
            }
        }
    }
}