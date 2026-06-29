class Solution {
    public int numOfStrings(String[] patterns, String word) {
        
        // store final answer
        int count = 0;

        // traverse each pattern in array
        for (String pattern : patterns) {

            // check if pattern exists in word as substring
            if (word.contains(pattern))
                count++; // increase count

        }

        // return total count
        return count;
    }
}