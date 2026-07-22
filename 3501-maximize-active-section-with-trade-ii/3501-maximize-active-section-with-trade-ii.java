import java.util.*;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        char[] c = s.toCharArray();
        
        // Count total '1's across the ENTIRE string baseline
        int globalOnes = 0;
        for (char ch : c) {
            if (ch == '1') globalOnes++;
        }
        
        List<Integer> segStartList = new ArrayList<>();
        List<Integer> segEndList = new ArrayList<>();
        List<Integer> segTypeList = new ArrayList<>();
        int[] posToSeg = new int[n];
        
        int i = 0;
        int segId = 0;
        while (i < n) {
            int start = i;
            char type = c[i];
            while (i < n && c[i] == type) {
                posToSeg[i] = segId;
                i++;
            }
            segStartList.add(start);
            segEndList.add(i - 1);
            segTypeList.add(type - '0');
            segId++;
        }
        
        int segCount = segId;
        int[] segStart = new int[segCount];
        int[] segEnd = new int[segCount];
        int[] segType = new int[segCount];
        int[] segLen = new int[segCount];
        
        for (int k = 0; k < segCount; k++) {
            segStart[k] = segStartList.get(k);
            segEnd[k] = segEndList.get(k);
            segType[k] = segTypeList.get(k);
            segLen[k] = segEnd[k] - segStart[k] + 1;
        }
        
        int[] val = new int[segCount];
        for (int k = 1; k < segCount - 1; k++) {
            if (segType[k] == 1) {
                val[k] = segLen[k - 1] + segLen[k + 1];
            }
        }
        
        int LOG = segCount > 0 ? 32 - Integer.numberOfLeadingZeros(segCount) : 1;
        int[][] sparse = new int[LOG][segCount];
        if (segCount > 0) {
            sparse[0] = val.clone();
        }
        
        for (int k = 1; k < LOG; k++) {
            int half = 1 << (k - 1);
            for (int idx = 0; idx + (1 << k) <= segCount; idx++) {
                sparse[k][idx] = Math.max(sparse[k - 1][idx], sparse[k - 1][idx + half]);
            }
        }
        
        int[] logTable = new int[Math.max(2, segCount + 1)];
        for (int k = 2; k <= segCount; k++) {
            logTable[k] = logTable[k / 2] + 1;
        }
        
        int q = queries.length;
        List<Integer> answer = new ArrayList<>(q);
        
        for (int qi = 0; qi < q; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];
            int segL = posToSeg[l];
            int segR = posToSeg[r];
            
            if (segL == segR) {
                // Return total global ones since no trade within a single segment can be made
                answer.add(globalOnes);
                continue;
            }
            
            int cnt = segR - segL + 1;
            int bestDelta = 0;
            
            if (cnt >= 3) {
                int iLeft = segL + 1;
                if (segType[iLeft] == 1) {
                    int leftLen = segEnd[segL] - l + 1;
                    int rightLen = (iLeft + 1 == segR) ? (r - segStart[segR] + 1) : segLen[iLeft + 1];
                    bestDelta = Math.max(bestDelta, leftLen + rightLen);
                }
                
                int iRight = segR - 1;
                if (segType[iRight] == 1) {
                    int rightLen = r - segStart[segR] + 1;
                    int leftLen = (iRight - 1 == segL) ? (segEnd[segL] - l + 1) : segLen[iRight - 1];
                    bestDelta = Math.max(bestDelta, leftLen + rightLen);
                }
                
                int lo = segL + 2, hi = segR - 2;
                if (lo <= hi) {
                    int len = hi - lo + 1;
                    int p = logTable[len];
                    int rangeMax = Math.max(sparse[p][lo], sparse[p][hi - (1 << p) + 1]);
                    bestDelta = Math.max(bestDelta, rangeMax);
                }
            }
            // Add the best localized swap gain directly to global baseline count
            answer.add(globalOnes + bestDelta);
        }
        return answer;
    }
}
