class Solution {
    public int numberOfSubstrings(String s) {
        
        // store the frequency of characters a, b, and c
        HashMap<Character, Integer> map = new HashMap<>();

        // store final count of substrings
        int result = 0;

        // track the starting point of the sliding window
        int left = 0;

        // traverse every character in the string as the end of the window
        for (int right = 0; right < s.length(); right++) {

            // get current character at the right pointer
            char current = s.charAt(right);

            // update frequency map for the current character
            map.put(current, map.getOrDefault(current, 0) + 1);

            // check whether all three characters exist in the map
            while (map.getOrDefault('a', 0) > 0 && 
                   map.getOrDefault('b', 0) > 0 && 
                   map.getOrDefault('c', 0) > 0) {

                // valid substrings can end anywhere from index right to s.length() - 1
                result += s.length() - right;

                // get character at the left pointer to shrink the window
                char leftChar = s.charAt(left);

                // decrease its frequency in the map
                map.put(leftChar, map.get(leftChar) - 1);

                // move the left pointer forward
                left++;
            }
        }
        
        // return final count
        return result;
    }
}
