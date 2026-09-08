class Solution {
    public int countCommas(int n) {
        // numbers from 1000 to 100000 --> 1 comma return n - 999
        // 1 to 999 contain no comma ---> return 0
        if (n < 1000) return 0;
        return n - 999;
    }
}