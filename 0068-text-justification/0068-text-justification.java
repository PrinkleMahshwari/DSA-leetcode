import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int n = words.length;
        int left = 0;
        
        // Reusable character buffer to build each line instantly without allocations
        char[] buffer = new char[maxWidth];
        
        while (left < n) {
            int right = left + 1;
            int wordsLength = words[left].length();
            
            // Greedily find how many words fit on this line
            while (right < n && wordsLength + 1 + words[right].length() <= maxWidth) {
                wordsLength += 1 + words[right].length();
                right++;
            }
            
            int numWords = right - left;
            int bufferPtr = 0;
            
            // Case 1: Left-justified line (Last line or a line with a single word)
            if (right == n || numWords == 1) {
                for (int i = left; i < right; i++) {
                    String word = words[i];
                    int len = word.length();
                    word.getChars(0, len, buffer, bufferPtr);
                    bufferPtr += len;
                    
                    if (i < right - 1) {
                        buffer[bufferPtr++] = ' ';
                    }
                }
                // Pad remaining trailing slots with spaces
                while (bufferPtr < maxWidth) {
                    buffer[bufferPtr++] = ' ';
                }
            } 
            // Case 2: Fully-justified line
            else {
                int actualWordsLength = 0;
                for (int i = left; i < right; i++) {
                    actualWordsLength += words[i].length();
                }
                
                int totalSpaces = maxWidth - actualWordsLength;
                int gaps = numWords - 1;
                
                int baseSpaces = totalSpaces / gaps;
                int extraSpaces = totalSpaces % gaps;
                
                for (int i = left; i < right; i++) {
                    String word = words[i];
                    int len = word.length();
                    word.getChars(0, len, buffer, bufferPtr);
                    bufferPtr += len;
                    
                    // Add spaces if it's not the last word in the line
                    if (i < right - 1) {
                        int spacesToAppend = baseSpaces + (i - left < extraSpaces ? 1 : 0);
                        for (int s = 0; s < spacesToAppend; s++) {
                            buffer[bufferPtr++] = ' ';
                        }
                    }
                }
            }
            
            // Instantly create the String from the filled character array bounds
            result.add(new String(buffer, 0, maxWidth));
            left = right;
        }
        
        return result;
    }
}
