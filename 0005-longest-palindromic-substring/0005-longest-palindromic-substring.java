class Solution {
    public String longestPalindrome(String s) {
        // create StringBuilder t to transformed string e.g., abc --> ^#a#b#c#$
     StringBuilder t = new StringBuilder();

     t.append('^');

     for (int i = 0; i < s.length(); i++) {
        t.append('#');
        t.append(s.charAt(i));
     }

     t.append('#');
     t.append('$');

     // convert StringBuilder to String
     String str = t.toString();

     // create a radius array of palindrome
     int[] p = new int[str.length()];

     // initilize the center of current rightmost palindrome
     int center = 0;

     // initialize the right boundary of that palindrome
     int right = 0;

     // loop to process every position in the transformed string
     for (int i = 1; i < str.length() - 1; i++) {
        // add mirror
        int mirror = 2 * center - i;

        // check i is inside right boundary
        if (i < right) {
            p[i] = Math.min(right - i, p[mirror]);
        }
        // if it expand further and move beyond right boundary
        while (str.charAt(i + p[i] + 1) == str.charAt(i - p[i] - 1)) {
            p[i]++;
        }
        
        // update center and right
        if (i + p[i] > right) {
            center = i;
            right = i + p[i];
        }
     }

     // find maxRadius and centerIndex
     int maxRadius = 0;
     int centerIndex = 0;
     for (int i = 0; i < str.length(); i++) {
        // current radius is larger than max radius
        if (p[i] > maxRadius) {
            maxRadius = p[i];
            centerIndex = i;
        }
     }
    
    //convert back to the original string
    int start = (centerIndex - maxRadius) / 2;
    return s.substring(start, start + maxRadius);
    }
}