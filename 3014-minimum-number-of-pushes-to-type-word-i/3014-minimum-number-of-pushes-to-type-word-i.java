class Solution {
    public int minimumPushes(String word) {
        int n = word.length();
        // this is total distinct letters we need to assign to 8 keys

        // this is using math directly instead of sorting or simulating
        // 8 keys, first 8 letters = 1 push, next 8 = 2 pushes, etc.
        int cost = 0;
        for (int i = 0; i < n; i++) {
            // this is push count = (position / 8) + 1
            cost += (i / 8) + 1;
        }
        return cost;
    }
}
