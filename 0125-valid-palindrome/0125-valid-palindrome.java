class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        
        while (left < right) {
            char head = s.charAt(left);
            char tail = s.charAt(right);
            
            // Skip non-alphanumeric characters from the left
            if (!Character.isLetterOrDigit(head)) {
                left++;
            } 
            // Skip non-alphanumeric characters from the right
            else if (!Character.isLetterOrDigit(tail)) {
                right--;
            } 
            // Both are alphanumeric, compare them case-insensitively
            else {
                if (Character.toLowerCase(head) != Character.toLowerCase(tail)) {
                    return false;
                }
                left++;
                right--;
            }
        }
        
        return true;
    }
}
