class Solution {
    public int minimumPushes(String word) {

        // count frequency of each letter, only 26 possible so a fixed-size
        // array beats any map-based structure
        int[] freq = new int[26];
        for (char c : word.toCharArray()) {
            freq[c - 'a']++;
        }

        // counting sort by frequency instead of Arrays.sort/boxing: frequencies
        // are bounded by word.length(), so bucket them directly -- O(n) instead
        // of O(26 log 26) (negligible either way at 26 elements, but this
        // avoids Integer boxing entirely)
        int n = word.length();
        int[] freqBucket = new int[n + 1];
        for (int f : freq) {
            if (f > 0) freqBucket[f]++;
        }

        // greedily assign the highest frequencies first to the earliest
        // (cheapest) push-count tiers: 8 letters get 1 push, next 8 get 2
        // pushes, next 8 get 3 pushes, next 2 get 4 pushes (8+8+8+2=26)
        long totalPushes = 0;
        int lettersAssigned = 0;

        for (int f = n; f >= 1; f--) {
            int count = freqBucket[f];
            while (count > 0) {
                int pushCost = (lettersAssigned / 8) + 1;
                totalPushes += (long) pushCost * f;
                lettersAssigned++;
                count--;
            }
        }

        return (int) totalPushes;
    }
}