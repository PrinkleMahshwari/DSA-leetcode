class Solution {
    public String smallestPalindrome(String s) {
        int n = s.length();
        int[] counts = new int[26];
        
        // Step 1: Count character frequencies
        for (int i = 0; i < n; i++) {
            counts[s.charAt(i) - 'a']++;
        }
        
        // Step 2: Extract the odd-count character if string length is odd
        char oddChar = 0;
        if (n % 2 != 0) {
            for (int i = 0; i < 26; i++) {
                if (counts[i] % 2 != 0) {
                    oddChar = (char) (i + 'a');
                    counts[i]--; // Reduce by 1 to make it even for mirror building
                    break;
                }
            }
        }
        
        // Reusable character array to construct the palindrome in 0 ms
        char[] result = new char[n];
        int left = 0;
        int right = n - 1;
        
        // Step 3: Mirror smallest characters greedily from the outside in
        for (int i = 0; i < 26; i++) {
            char ch = (char) (i + 'a');
            while (counts[i] > 0) {
                result[left] = ch;
                result[right] = ch;
                left++;
                right--;
                counts[i] -= 2; // Placed two instances
            }
        }
        
        // Step 4: Drop the odd middle anchor into place
        if (oddChar != 0) {
            result[left] = oddChar;
        }
        
        return new String(result);
    }
}
