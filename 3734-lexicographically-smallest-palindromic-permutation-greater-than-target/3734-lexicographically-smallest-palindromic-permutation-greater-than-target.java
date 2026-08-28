class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Check if a palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if ((freq[i] & 1) == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;

        /*
         * Special case: n = 1
         */
        if (halfLen == 0) {
            char only = (char) ('a' + middle);

            return only > target.charAt(0)
                    ? String.valueOf(only)
                    : "";
        }

        // Frequency of characters available for the first half
        int[] halfFreq = new int[26];

        for (int i = 0; i < 26; i++) {
            halfFreq[i] = freq[i] / 2;
        }

        /*
         * target's first half
         */
        String targetHalf = target.substring(0, halfLen);

        /*
         * First possibility:
         * Try to construct exactly targetHalf.
         *
         * If possible, we may be able to use it directly
         * depending on the middle / mirrored half.
         */
        int[] remaining = halfFreq.clone();
        boolean canMatch = true;

        for (int i = 0; i < halfLen; i++) {

            int c = targetHalf.charAt(i) - 'a';

            if (remaining[c] == 0) {
                canMatch = false;
                break;
            }

            remaining[c]--;
        }

        if (canMatch) {

            char[] half = targetHalf.toCharArray();

            String candidate = buildPalindrome(
                    half,
                    middle,
                    n
            );

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Second possibility:
         *
         * Find the smallest half strictly greater than targetHalf.
         */
        char[] nextHalf = findNextHalf(
                targetHalf,
                halfFreq
        );

        if (nextHalf == null) {
            return "";
        }

        return buildPalindrome(
                nextHalf,
                middle,
                n
        );
    }

    /*
     * Finds the lexicographically smallest permutation of
     * the multiset represented by freq that is strictly
     * greater than target.
     */
    private char[] findNextHalf(
            String target,
            int[] originalFreq) {

        int len = target.length();

        if (len == 0) {
            return null;
        }

        int[] freq = originalFreq.clone();

        /*
         * Match target from left to right as much as possible.
         */
        int matched = 0;

        while (matched < len) {

            int c = target.charAt(matched) - 'a';

            if (freq[c] == 0) {
                break;
            }

            freq[c]--;
            matched++;
        }

        /*
         * If we matched the complete target,
         * we need to backtrack from the last position.
         *
         * If we failed earlier, we can start from that
         * position.
         */
        int pivot = matched - 1;

        /*
         * If matching failed at position 'matched',
         * all remaining characters can be used at that
         * position.
         */
        if (matched < len) {
            pivot = matched;
        }

        /*
         * Try every possible pivot from right to left.
         */
        for (int p = pivot; p >= 0; p--) {

            /*
             * Restore the character that was matched at p.
             *
             * For p == matched, nothing was consumed there.
             */
            if (p < matched) {
                int restored = target.charAt(p) - 'a';
                freq[restored]++;
            }

            int wanted = target.charAt(p) - 'a';

            /*
             * Find the smallest available character
             * strictly greater than target[p].
             */
            for (int c = wanted + 1; c < 26; c++) {

                if (freq[c] == 0) {
                    continue;
                }

                char[] result = new char[len];

                /*
                 * Prefix before pivot stays equal to target.
                 */
                for (int i = 0; i < p; i++) {
                    result[i] = target.charAt(i);
                }

                /*
                 * Increase at pivot.
                 */
                result[p] = (char) ('a' + c);

                freq[c]--;

                /*
                 * Fill the suffix with the smallest
                 * possible characters.
                 */
                int pos = p + 1;

                for (int x = 0; x < 26; x++) {
                    while (freq[x] > 0) {
                        result[pos++] = (char) ('a' + x);
                        freq[x]--;
                    }
                }

                return result;
            }

            /*
             * If p was matched, its character has now
             * been restored and will be available when
             * moving further left.
             */
        }

        return null;
    }

    /*
     * Builds:
     *
     * half + middle + reverse(half)
     */
    private String buildPalindrome(
            char[] half,
            int middle,
            int n) {

        StringBuilder result = new StringBuilder(n);

        for (char c : half) {
            result.append(c);
        }

        if ((n & 1) == 1) {
            result.append((char) ('a' + middle));
        }

        for (int i = half.length - 1; i >= 0; i--) {
            result.append(half[i]);
        }

        return result.toString();
    }
}