class Solution {
    public int hIndex(int[] citations) {

        int n = citations.length;

        // counting sort bucket approach: since h-index can never exceed n
        // (can't have more than n papers with >= h citations if h > n),
        // cap all citation counts at n and bucket them -- avoids an O(n log n)
        // comparison sort entirely
        int[] bucket = new int[n + 1];

        for (int c : citations) {
            if (c >= n) {
                bucket[n]++; // anything >= n citations gets capped into the last bucket
            } else {
                bucket[c]++;
            }
        }

        // scan from the top down: papersAtLeast tracks how many papers have
        // at least the current citation count h. the largest h where
        // papersAtLeast >= h is the answer
        int papersAtLeast = 0;
        for (int h = n; h >= 0; h--) {
            papersAtLeast += bucket[h];
            if (papersAtLeast >= h) {
                return h;
            }
        }

        return 0;
    }
}