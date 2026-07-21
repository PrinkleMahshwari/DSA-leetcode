class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {

        int n = gas.length;

        int totalSurplus = 0; // sum of (gas[i] - cost[i]) across the whole circuit
        int runningTank = 0;  // tank if starting from the current candidate start
        int start = 0;

        for (int i = 0; i < n; i++) {
            int diff = gas[i] - cost[i];
            totalSurplus += diff;
            runningTank += diff;

            // if the tank goes negative, no station between the current
            // start and i can be a valid starting point either -- any of
            // them would hit this same shortfall or worse, so jump the
            // candidate start to i+1 and reset the running tank
            if (runningTank < 0) {
                start = i + 1;
                runningTank = 0;
            }
        }

        // a valid start exists only if total gas >= total cost overall;
        // if so, the last candidate start found is guaranteed to work
        // (problem guarantees uniqueness when a solution exists)
        return totalSurplus >= 0 ? start : -1;
    }
}