class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = n + 1;

        // best[i] = shortest target-sum subarray
        // completely inside arr[0...i-1]
        int[] best = new int[n + 1];

        for (int i = 0; i <= n; i++) best[i] = INF;

        int left = 0;
        int sum = 0;
        int shortest = INF;
        int answer = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) sum -= arr[left++];

            if (sum == target) {
                int length = right - left + 1;

                // best[i] contains a subarray completely before the current one
                if (best[left] != INF) answer = Math.min(answer, best[left] + length);
                shortest= Math.min(shortest, length);
            }
            best[right + 1] = shortest;
        }
        return answer == INF ? -1 : answer;
    }
}