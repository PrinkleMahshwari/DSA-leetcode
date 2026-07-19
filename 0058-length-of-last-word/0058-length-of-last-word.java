class Solution {
    public int lengthOfLastWord(String s) {
        
        int i = s.length() - 1;

        // skip trailing spcaes first, to find where the last word actually ends
        while (i >= 0 && s.charAt(i) == ' ')
            i--;
        
        int length = 0;

        // count characters backward until hitting a space or the start of string 
        while (i >= 0 && s.charAt(i) != ' ') {
            length++;
            i--;
        }

        return length;
    }
}