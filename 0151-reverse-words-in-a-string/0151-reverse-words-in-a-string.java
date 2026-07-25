class Solution {
    public String reverseWords(String s) {
        int i = s.length() - 1;
        StringBuilder answer = new StringBuilder();

        while (i >= 0) {
            // 1. Skip spaces
            while (i >= 0 && s.charAt(i) == ' ') {
                i--;
            }
            
            // If we reached the start of the string, exit the loop
            if (i < 0) break;
            
            // 2. Mark the end of the word
            int end = i;
            
            // 3. Scan backwards to find the start of the word
            while (i >= 0 && s.charAt(i) != ' ') {
                i--;
            }
            
            // 4. Append a space delimiter if this isn't our first word
            if (answer.length() > 0) {
                answer.append(" ");
            }
            
            // 5. Slice and append the discovered word
            answer.append(s.substring(i + 1, end + 1));
        }
        
        return answer.toString();
    }
}
