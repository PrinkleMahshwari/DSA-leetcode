class Solution {
    public int minOperations(String s) {
        int n = s.length();
        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }
        
        if (sorted) return 0;
        if (n == 2) return -1;

        char mn = 'z';
        char mx = 'a';
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            mx = (char) Math.max(mx, c);
            mn = (char) Math.min(mn, c);
        }

        if (s.charAt(0) == mn || s.charAt(n - 1) == mx) return 1;

        if (s.charAt(0) == mx && s.charAt(n - 1) == mn) {
            boolean mnInPrefix = false;
            boolean mxInSuffix = false;
            
            // Check if mn exists anywhere before the last element
            for (int i = 0; i < n - 1; i++) {
                if (s.charAt(i) == mn) mnInPrefix = true;
            }
            // Check if mx exists anywhere after the first element
            for (int i = 1; i < n; i++) {
                if (s.charAt(i) == mx) mxInSuffix = true;
            }
            
            // Only requires 3 ops if mn is strictly locked at the end 
            // and mx is strictly locked at the start
            if (!mnInPrefix && !mxInSuffix) return 3;
        }

        return 2;
    }
}
