class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long smaller = Math.min (need1, need2);
        long larger = Math.max (need1, need2);

        // strategy 1: buy separate items
        long costA = (long) need1 * cost1 + (long) need2 * cost2;

        // strategy 2: use type 3 for smaller requirements
        long costB = smaller * costBoth + (long) (need1 - smaller) * cost1 + (long) (need2 - smaller) * cost2;

        // strategy 3: use type 3 for both requirements
        long costC = larger * costBoth;

        return Math.min (costA, Math.min (costB, costC));
    }
}