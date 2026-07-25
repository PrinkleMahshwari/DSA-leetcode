import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int n = words.length;
        int left = 0;
        
        while (left < n) {
            int right = left + 1;
            int lineWordsLength = words[left].length();
            
            // Step 1: Pack as many words as possible into the current line greedily
            while (right < n && lineWordsLength + 1 + words[right].length() <= maxWidth) {
                lineWordsLength += 1 + words[right].length();
                right++;
            }
            
            StringBuilder line = new StringBuilder();
            int numWords = right - left;
            
            // Step 2: Handle Left-Justification (Last line or line with only 1 word)
            if (right == n || numWords == 1) {
                for (int i = left; i < right; i++) {
                    line.append(words[i]);
                    if (i < right - 1) {
                        line.append(" ");
                    }
                }
                // Pad trailing spaces until maxWidth is reached
                while (line.length() < maxWidth) {
                    line.append(" ");
                }
            } 
            // Step 3: Handle Full-Justification (Standard middle lines)
            else {
                // Calculate pure total text length excluding the tracking space padding
                int wordsActualLength = 0;
                for (int i = left; i < right; i++) {
                    wordsActualLength += words[i].length();
                }
                
                int totalSpaces = maxWidth - wordsActualLength;
                int gaps = numWords - 1;
                
                int baseSpaces = totalSpaces / gaps;
                int remainderSpaces = totalSpaces % gaps;
                
                for (int i = left; i < right; i++) {
                    line.append(words[i]);
                    
                    // Do not append trailing spaces after the final word of the line
                    if (i < right - 1) {
                        int spacesToAppend = baseSpaces + (i - left < remainderSpaces ? 1 : 0);
                        for (int s = 0; s < spacesToAppend; s++) {
                            line.append(" ");
                        }
                    }
                }
            }
            
            result.add(line.toString());
            left = right; // Slide pointer to begin the next line chunk
        }
        
        return result;
    }
}
