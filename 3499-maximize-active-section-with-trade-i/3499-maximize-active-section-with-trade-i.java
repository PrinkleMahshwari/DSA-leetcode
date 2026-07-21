class Solution {
    public int maxActiveSectionsAfterTrade(String s) {

        int n = s.length();
        char[] c = s.toCharArray();

        int totalOnes = 0;
        for (char ch : c) if (ch == '1') totalOnes++;

        // walk through s once, tracking consecutive run lengths. whenever a
        // '1'-run is found that has a '0'-run immediately before it (already
        // seen) AND a '0'-run immediately after it, it's a candidate for
        // sacrifice: delta = prevZeroLen + nextZeroLen (see derivation: losing
        // the 1-run but gaining back both zero-runs converted to 1 nets out
        // to exactly prevZero + nextZero, the sacrificed run's length cancels)
        int maxDelta = 0;

        int i = 0;
        int prevZeroLen = -1; // -1 means "no zero-run seen yet before current position"

        while (i < n) {
            char runChar = c[i];
            int start = i;
            while (i < n && c[i] == runChar) i++;
            int runLen = i - start;

            if (runChar == '0') {
                prevZeroLen = runLen;
            } else {
                // this is a '1'-run; check if it's followed by a '0'-run
                // (i.e., not the last run in the string) and was preceded
                // by one (prevZeroLen already set from a real zero-run)
                if (prevZeroLen != -1 && i < n) {
                    // peek ahead: the next run (if any) must be zeros --
                    // guaranteed true here since runs alternate by construction,
                    // so if i < n, the character at i differs from '1', i.e. it's '0'
                    int nextStart = i;
                    int j = i;
                    while (j < n && c[j] == '0') j++;
                    int nextZeroLen = j - nextStart;

                    int delta = prevZeroLen + nextZeroLen;
                    if (delta > maxDelta) maxDelta = delta;
                }
                // after processing a 1-run, reset prevZeroLen tracking isn't
                // needed since the next run (if zero) will overwrite it anyway,
                // and runs strictly alternate so no stale reuse can happen
            }
        }

        return totalOnes + maxDelta;
    }
}