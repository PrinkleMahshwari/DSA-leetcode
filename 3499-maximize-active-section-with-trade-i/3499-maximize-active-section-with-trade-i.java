class Solution {
    public int maxActiveSectionsAfterTrade(String s) {

        int n = s.length();

        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }

        // single forward pass, no re-scanning: track the last THREE run
        // lengths (zero, one, zero) as we go. previous version's inner
        // "peek ahead" loop re-walked the next zero-run's characters even
        // though the outer loop would scan them again next iteration --
        // this version advances through every character exactly once total
        int maxDelta = 0;

        int prevZeroLen = -1; // most recent completed zero-run length, -1 if none yet
        int pendingOneLen = -1; // most recent completed one-run length, -1 if none pending

        int i = 0;
        while (i < n) {
            char runChar = s.charAt(i);
            int start = i;
            while (i < n && s.charAt(i) == runChar) i++;
            int runLen = i - start;

            if (runChar == '0') {
                // if a one-run is pending and had a zero before it, this
                // zero-run is its "next" neighbor -- resolve the candidate now
                if (pendingOneLen != -1 && prevZeroLen != -1) {
                    int delta = prevZeroLen + runLen;
                    if (delta > maxDelta) maxDelta = delta;
                }
                prevZeroLen = runLen;
                pendingOneLen = -1; // consumed, this one-run's check is done
            } else {
                pendingOneLen = runLen; // wait for the next run to resolve it
            }
        }

        return totalOnes + maxDelta;
    }
}