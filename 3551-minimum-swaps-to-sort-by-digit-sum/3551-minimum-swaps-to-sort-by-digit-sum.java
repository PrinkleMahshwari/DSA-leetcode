class Solution {
    public int minSwaps(int[] nums) {
        int n = nums.length;

        // store eac number with its digit sum
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = digitSum (nums[i]);
        }

        // sort by digit sum, then by number
        Arrays.sort (arr, (a, b) -> {
            if (a[1] != b[1]) return Integer.compare(a[1], b[1]);
            return Integer.compare (a[0], b[0]);
        });

        // map each number to its target sorted index
        Map<Integer, Integer> target = new HashMap<>();

        for (int i = 0; i < n; i++) target.put(arr[i][0], i);

        // build the permutation of target indices
        int[] permutation = new int[n];
        for (int i = 0; i < n; i++) permutation[i] = target.get(nums[i]);

        // count permutation cycles
        boolean[] visited = new boolean[n];
        int cycles = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i]) continue;
            cycles++;

            int current = i;

            while (!visited[current]) {
                visited[current] = true;
                current = permutation[current];
            }
        }

        return n - cycles;
    }
    private int digitSum (int num) {
        int sum = 0;

        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}