import java.util.*;

class Solution {

    public int removeCoveredIntervals(int[][] intervals) {
        int n = intervals.length;

        // sort by start ascending; when starts tie, sort by end descending.
        // the end-descending tiebreak is critical: if two intervals share the
        // same start, the one with the smaller end is automatically covered
        // by the one with the larger end, so it must appear AFTER it to get
        // correctly flagged as covered during the single left-to-right scan
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return b[1] - a[1];
        });

        int count = 0;
        int maxEndSoFar = -1;

        // after sorting, an interval is covered if and only if its end is
        // <= the maximum end seen among all intervals processed so far.
        // this holds because sorting guarantees every prior interval's start
        // is <= the current interval's start already, so only the end
        // comparison remains to decide coverage
        for (int[] interval : intervals) {
            int end = interval[1];

            if (end > maxEndSoFar) {
                // not covered by anything seen so far, counts as a real interval
                count++;
                maxEndSoFar = end;
            }
            // else: end <= maxEndSoFar means some earlier interval starts
            // no later and ends no earlier, i.e. it fully covers this one
        }

        return count;
    }
}