class Solution {

    // Index matches the digit directly (0 and 1 are empty placeholder)
    private static final String[] KEYPAD = {
        "", // 0
        "", // 1
        "abc", // 2
        "def", // 3
        "ghi", // 4
        "jkl", // 5
        "mno", // 6
        "pqrs", // 7
        "tuv", // 8
        "wxyz" // 9
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        // Gaurd check: if input is empty, return empty list immediately
        if (digits == null || digits.isEmpty())
            return result;
        
        // use a char array for the back tracking path to avoid massive string allocation
        char[] currentCombination = new char[digits.length()];

        // kick off the Depth-First Search (DFS) Backtracking
        backtrack(digits, 0, currentCombination, result);

        return result;
    }

    // Backtracking Engine (DFS)
    private void backtrack(String digits, int index, char[] currentCombination, List<String> result) {

        // Base Case: if our path length equals the target length, we found a complete combination
        if (index == digits.length()) {
            result.add(new String(currentCombination));
            return;
        }

        // get the letters corresponding to the current digit
        int digitValue = digits.charAt(index) - '0';
        String letters = KEYPAD[digitValue];

        // loop through all choices avaiable for this digit
        for (int i = 0; i < letters.length(); i++) {

            // place the current character in our path array
            currentCombination[index] = letters.charAt(i);

            // move deeper into the tree to process the next digit
            backtrack(digits, index + 1, currentCombination, result);

            // we don't explicitly need to clean up/remove elements becaues the next iteration will naturally overwrite currentCombination[index]
        }
    }
}