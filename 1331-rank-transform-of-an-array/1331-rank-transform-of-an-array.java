class Solution {
    public int[] arrayRankTransform(int[] arr) {
        int n = arr.length;

        if (n == 0) return arr; // nothing to rank

        // sort a clone, keeps original arr untouched for the final output pass
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        // map each distinct value directly to its rank using a HashMap,
        // avoids binary serach per element later
        Map<Integer, Integer> rankOf = new HashMap<>();
        int rank = 1;

        for (int i = 0; i < n; i++) {
            // only assign a new rank the first time a distinct valeue is seen,
            // duplicates get skipped so rank stays "as small as possible"
            if (!rankOf.containsKey(sorted[i])) {
                rankOf.put(sorted[i], rank);
                rank++;
            } 
        }

        // single pass to build the output using O(1) map lookups
        int[] result = new int[n];
        for (int i = 0; i < n; i++) 
            result[i] = rankOf.get(arr[i]);
        
        return result;
    }
}